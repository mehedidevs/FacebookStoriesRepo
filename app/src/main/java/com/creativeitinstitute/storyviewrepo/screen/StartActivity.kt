package com.creativeitinstitute.storyviewrepo.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.creativeitinstitute.storyviewrepo.R

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        findViewById<Button>(R.id.showStoryBtn).setOnClickListener {
            startActivity(Intent(this@StartActivity, MainActivity::class.java))

        }

    }
}