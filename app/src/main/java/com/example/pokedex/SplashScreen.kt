package com.example.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.os.HandlerCompat.postDelayed
import android.content.Intent





class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val handler = Handler()
        handler.postDelayed(Runnable { mostrarMainActivity() }, 2000)
    }
    private fun mostrarMainActivity() {
        val intent = Intent(
            this, MainActivity::class.java
        )
        startActivity(intent)
        finish()
    }
}
