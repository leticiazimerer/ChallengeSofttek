package com.softtek.mindcare.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.ViewModelProvider
import com.softtek.mindcare.databinding.ActivitySupportBinding
import com.softtek.mindcare.viewmodels.SupportViewModel

class SupportActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySupportBinding
    private lateinit var viewModel: SupportViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySupportBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[SupportViewModel::class.java]
        setupRecyclerView()
        loadResources()
    }

    private fun setupRecyclerView() {
        binding.supportRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.supportRecyclerView.adapter = viewModel.supportAdapter
    }

    private fun loadResources() {
        viewModel.loadSupportResources()
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, SupportActivity::class.java)
    }
}