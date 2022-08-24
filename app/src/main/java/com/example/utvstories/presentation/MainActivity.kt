package com.example.utvstories.presentation

import android.content.ActivityNotFoundException
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.utvstories.R
import com.example.utvstories.data.remote.models.StoryDto
import com.example.utvstories.databinding.ActivityMainBinding
import com.example.utvstories.util.State
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var storiesAdapter: StoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpUI()

        initCollectors()
    }

    private fun setUpUI() {
        with(binding.storyRecycler) {
            storiesAdapter = StoriesAdapter { openWebsite(it) }
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 3)
            adapter = storiesAdapter
        }
        binding.swipeRefresher.setOnRefreshListener {
            viewModel.fetchStories()
        }
    }

    private fun initCollectors() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                this.launch {
                    viewModel.storiesList.collect {
                        storiesAdapter.submitList(it as List<StoryDto>)
                    }
                }
                this.launch {
                    viewModel.state.collect {
                        binding.swipeRefresher.isRefreshing = it == State.Loading
                        if (it is State.Error) {
                            Snackbar.make(binding.root, R.string.loading_error, Snackbar.LENGTH_LONG)
                                .setAction(R.string.update_button) {
                                    viewModel.fetchStories()
                                }
                                .show()
                            Log.d(TAG, "onCreate: Error: ${it.e}, Message: ${it.message}")
                        }
                    }
                }
            }
        }
    }

    private fun openWebsite(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        try {
            startActivity(browserIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(this, R.string.browser_failure_message, Toast.LENGTH_SHORT).show()
        }
    }
}