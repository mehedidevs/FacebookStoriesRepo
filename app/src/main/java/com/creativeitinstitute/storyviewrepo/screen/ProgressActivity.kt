package com.creativeitinstitute.storyviewrepo.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.creativeitinstitute.storyviewrepo.R
import com.creativeitinstitute.storyviewrepo.databinding.ActivityProgressBinding

class ProgressActivity : AppCompatActivity() {

    lateinit var binding: ActivityProgressBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProgressBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.textProg.setMax(100)
        binding.textProg.setProgress(90)

    }
}