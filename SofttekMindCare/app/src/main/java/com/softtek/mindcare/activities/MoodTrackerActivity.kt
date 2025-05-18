package com.softtek.mindcare.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.softtek.mindcare.databinding.ActivityMoodTrackerBinding
import com.softtek.mindcare.viewmodels.MoodTrackerViewModel

class MoodTrackerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMoodTrackerBinding
    private lateinit var viewModel: MoodTrackerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoodTrackerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MoodTrackerViewModel::class.java]
        setupMoodButtons()
        setupSaveButton()
    }

    private fun setupMoodButtons() {
        binding.happyButton.setOnClickListener { viewModel.selectMood("Feliz") }
        binding.sadButton.setOnClickListener { viewModel.selectMood("Triste") }
        binding.angryButton.setOnClickListener { viewModel.selectMood("Nervoso") }
        viewModel.selectedMood.observe(this) { mood ->
            binding.selectedMoodText.text = mood ?: "Selecione um humor"
        }
    }

    private fun setupSaveButton() {
        binding.saveButton.setOnClickListener {
            viewModel.saveMoodEntry(
                binding.moodNote.text.toString(),
                binding.intensitySlider.value.toInt()
            )
            finish()
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, MoodTrackerActivity::class.java)
    }
}