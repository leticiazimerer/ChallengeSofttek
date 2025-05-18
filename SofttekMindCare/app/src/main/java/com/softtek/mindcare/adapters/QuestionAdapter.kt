package com.softtek.mindcare.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softtek.mindcare.databinding.ItemQuestionBinding
import com.softtek.mindcare.models.Question
import com.softtek.mindcare.models.QuestionType

class QuestionAdapter(
    private val onAnswerSelected: (Int, Any) -> Unit
) : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    private var questions: List<Question> = emptyList()

    fun submitList(newQuestions: List<Question>) {
        questions = newQuestions
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val binding = ItemQuestionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return QuestionViewHolder(binding, onAnswerSelected)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.bind(questions[position])
    }

    override fun getItemCount() = questions.size

    class QuestionViewHolder(
        private val binding: ItemQuestionBinding,
        private val onAnswerSelected: (Int, Any) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(question: Question) {
            binding.questionText.text = question.text

            when (question.type) {
                QuestionType.RADIO -> setupRadioOptions(question)
                QuestionType.CHECKBOX -> setupCheckboxOptions(question)
                QuestionType.SCALE -> setupScaleOptions(question)
            }
        }

        private fun setupRadioOptions(question: Question) {
            binding.radioGroup.removeAllViews()
            question.options.forEachIndexed { index, option ->
                val radioButton = RadioButton(binding.root.context).apply {
                    text = option
                    setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked) onAnswerSelected(question.id, option)
                    }
                }
                binding.radioGroup.addView(radioButton)
            }
        }

        private fun setupCheckboxOptions(question: Question) {
            binding.checkboxGroup.removeAllViews()
            question.options.forEach { option ->
                val checkBox = CheckBox(binding.root.context).apply {
                    text = option
                    setOnCheckedChangeListener { _, _ ->
                        val selected = binding.checkboxGroup.getCheckedOptions()
                        onAnswerSelected(question.id, selected)
                    }
                }
                binding.checkboxGroup.addView(checkBox)
            }
        }

        private fun setupScaleOptions(question: Question) {
            binding.scaleSeekBar.max = question.options.size - 1
            binding.scaleSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    if (fromUser) onAnswerSelected(question.id, progress)
                }
                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })
        }
    }
}