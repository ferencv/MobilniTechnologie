package com.example.stagviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.stagviewer.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }

        val submitButton: Button = findViewById(R.id.submitButton)
        submitButton.setOnClickListener{submitButtonClick()}

    }

    private fun submitButtonClick()
    {
        Toast.makeText(this, "button clicked",
            Toast.LENGTH_SHORT).show()
    }
}