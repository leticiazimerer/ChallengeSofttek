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

        viewModel = ViewModelProvider(this).get(MoodTrackerViewModel::class.java)

        setupUI()
        setupMoodSelection()
    }

    private fun setupUI() {
        binding.saveButton.setOnClickListener {
            val selectedMood = viewModel.selectedMood.value
            val intensity = binding.intensitySlider.value.toInt()
            val note = binding.noteEditText.text.toString()

            if (selectedMood != null) {
                viewModel.saveMoodEntry(selectedMood, intensity, note)
                finish()
            }
        }
    }

    private fun setupMoodSelection() {
        binding.happyButton.setOnClickListener { viewModel.selectMood("Happy") }
        binding.sadButton.setOnClickListener { viewModel.selectMood("Sad") }
        binding.angryButton.setOnClickListener { viewModel.selectMood("Angry") }
        binding.anxiousButton.setOnClickListener { viewModel.selectMood("Anxious") }
        binding.calmButton.setOnClickListener { viewModel.selectMood("Calm") }
        binding.tiredButton.setOnClickListener { viewModel.selectMood("Tired") }
        binding.excitedButton.setOnClickListener { viewModel.selectMood("Excited") }

        viewModel.selectedMood.observe(this) { mood ->
            binding.selectedMoodText.text = mood ?: "Select a mood"
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, MoodTrackerActivity::class.java)
    }
}