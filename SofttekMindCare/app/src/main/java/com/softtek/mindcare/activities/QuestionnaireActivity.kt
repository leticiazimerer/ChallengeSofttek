package com.softtek.mindcare.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.ViewModelProvider
import com.softtek.mindcare.databinding.ActivityQuestionnaireBinding
import com.softtek.mindcare.viewmodels.QuestionnaireViewModel

class QuestionnaireActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuestionnaireBinding
    private lateinit var viewModel: QuestionnaireViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionnaireBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[QuestionnaireViewModel::class.java]
        setupRecyclerView()
        setupSubmitButton()
        loadQuestionnaire()
    }

    private fun setupRecyclerView() {
        binding.questionsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.questionsRecyclerView.adapter = viewModel.questionAdapter
    }

    private fun setupSubmitButton() {
        binding.submitButton.setOnClickListener {
            viewModel.submitQuestionnaire()
            finish()
        }
    }

    private fun loadQuestionnaire() {
        viewModel.loadQuestionnaire()
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, QuestionnaireActivity::class.java)
    }
}