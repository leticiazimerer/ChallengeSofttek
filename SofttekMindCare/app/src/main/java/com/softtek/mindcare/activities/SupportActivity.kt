package com.softtek.mindcare.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.softtek.mindcare.adapters.SupportResourceAdapter
import com.softtek.mindcare.databinding.ActivitySupportBinding
import com.softtek.mindcare.viewmodels.SupportViewModel

class SupportActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySupportBinding
    private lateinit var viewModel: SupportViewModel
    private lateinit var supportAdapter: SupportResourceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySupportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(SupportViewModel::class.java)

        setupRecyclerView()
        setupListeners()
        observeViewModel()
        viewModel.loadSupportResources()
    }

    private fun setupRecyclerView() {
        supportAdapter = SupportResourceAdapter { resource ->
            when (resource.type) {
                "URL" -> openUrl(resource.url)
                "PHONE" -> callNumber(resource.url)
                "EMAIL" -> sendEmail(resource.url)
            }
        }

        binding.supportRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@SupportActivity)
            adapter = supportAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupListeners() {
        binding.emergencyButton.setOnClickListener {
            viewModel.loadEmergencyResources()
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun observeViewModel() {
        viewModel.supportResources.observe(this) { resources ->
            supportAdapter.submitList(resources)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun callNumber(number: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
        startActivity(intent)
    }

    private fun sendEmail(email: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        }
        startActivity(intent)
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, SupportActivity::class.java)
    }
}