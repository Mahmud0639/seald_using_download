package com.manuni.sealdexample
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.manuni.sealdexample.databinding.ActivityMainBinding
import com.manuni.sealdexample.seald.ScreenState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var downloadJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.loadButton.setOnClickListener {

            downloadJob?.cancel()

            showState(ScreenState.Loading)

            downloadJob = lifecycleScope.launch {
                var count = 1

                while (isActive) {
                    val success = Random.nextBoolean()

                    if (success) {
                        showState(ScreenState.Success("Downloading... ($count)"))
                    } else {
                        showState(ScreenState.Error("Download stopped."))
                    }

                    count++
                    delay(2000)
                }
            }
        }

        // Stop Button
        binding.stopButton.setOnClickListener {
            downloadJob?.cancel()
            showState(ScreenState.Error("Download cancelled by user"))
        }
    }

    private fun showState(state: ScreenState) {
        when (state) {
            is ScreenState.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
                binding.quoteText.visibility = View.GONE
            }
            is ScreenState.Success -> {
                binding.progressBar.visibility = View.GONE
                binding.quoteText.visibility = View.VISIBLE
                binding.quoteText.text = state.quote
            }
            is ScreenState.Error -> {
                binding.progressBar.visibility = View.GONE
                binding.quoteText.visibility = View.VISIBLE
                binding.quoteText.text = state.message
            }
        }
    }
}
