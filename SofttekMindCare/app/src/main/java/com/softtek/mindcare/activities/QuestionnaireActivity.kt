package com.softtek.mindcare.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.softtek.mindcare.adapters.QuestionAdapter
import com.softtek.mindcare.databinding.ActivityQuestionnaireBinding
import com.softtek.mindcare.viewmodels.QuestionnaireViewModel

class QuestionnaireActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuestionnaireBinding
    private lateinit var viewModel: QuestionnaireViewModel
    private lateinit var questionAdapter: QuestionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionnaireBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(QuestionnaireViewModel::class.java)

        setupRecyclerView()
        setupListeners()
        observeViewModel()
        viewModel.loadQuestionnaire()
    }

    private fun setupRecyclerView() {
        questionAdapter = QuestionAdapter { question, answer ->
            viewModel.saveAnswer(question.id, answer)
        }

        binding.questionsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@QuestionnaireActivity)
            adapter = questionAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupListeners() {
        binding.submitButton.setOnClickListener {
            viewModel.submitQuestionnaire()
            finish()
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun observeViewModel() {
        viewModel.questionnaire.observe(this) { questionnaire ->
            questionnaire?.let {
                binding.questionnaireTitle.text = it.title
                binding.questionnaireDescription.text = it.description
                questionAdapter.submitList(it.questions)
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, QuestionnaireActivity::class.java)
    }
}