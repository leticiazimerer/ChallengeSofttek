package com.softtek.mindcare.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softtek.mindcare.databinding.ItemQuestionBinding
import com.softtek.mindcare.models.Question
import com.softtek.mindcare.models.QuestionType

class QuestionAdapter(
    private val onAnswerSelected: (Question, Any) -> Unit
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

    override fun getItemCount(): Int = questions.size

    class QuestionViewHolder(
        private val binding: ItemQuestionBinding,
        private val onAnswerSelected: (Question, Any) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(question: Question) {
            with(binding) {
                questionText.text = question.text

                when (question.type) {
                    QuestionType.RADIO -> setupRadioOptions(question)
                    QuestionType.CHECKBOX -> setupCheckboxOptions(question)
                    QuestionType.SCALE -> setupScaleOptions(question)
                }
            }
        }

        private fun setupRadioOptions(question: Question) {
            with(binding) {
                radioGroup.visibility = View.VISIBLE
                checkboxGroup.visibility = View.GONE
                scaleLayout.visibility = View.GONE

                radioGroup.removeAllViews()
                question.options.forEachIndexed { index, option ->
                    val radioButton = RadioButton(context).apply {
                        text = option
                        id = View.generateViewId()
                        setOnCheckedChangeListener { _, isChecked ->
                            if (isChecked) onAnswerSelected(question, option)
                        }
                    }
                    radioGroup.addView(radioButton)
                }
            }
        }

        private fun setupCheckboxOptions(question: Question) {
            with(binding) {
                radioGroup.visibility = View.GONE
                checkboxGroup.visibility = View.VISIBLE
                scaleLayout.visibility = View.GONE

                checkboxGroup.removeAllViews()
                question.options.forEachIndexed { index, option ->
                    val checkBox = CheckBox(context).apply {
                        text = option
                        id = View.generateViewId()
                        setOnCheckedChangeListener { _, isChecked ->
                            val answers = checkboxGroup.getCheckedOptions()
                            onAnswerSelected(question, answers)
                        }
                    }
                    checkboxGroup.addView(checkBox)
                }
            }
        }

        private fun setupScaleOptions(question: Question) {
            with(binding) {
                radioGroup.visibility = View.GONE
                checkboxGroup.visibility = View.GONE
                scaleLayout.visibility = View.VISIBLE

                scaleText.text = question.options.firstOrNull() ?: "0"
                scaleSeekBar.max = (question.options.size - 1).coerceAtLeast(0)

                scaleSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                        scaleText.text = question.options[progress]
                        if (fromUser) {
                            onAnswerSelected(question, progress)
                        }
                    }
                    override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
                })
            }
        }
    }
}

fun ViewGroup.getCheckedOptions(): List<String> {
    return (0 until childCount)
        .map { getChildAt(it) }
        .filterIsInstance<CheckBox>()
        .filter { it.isChecked }
        .map { it.text.toString() }
}