package com.example.okta.moviedb_iak.activity

import android.content.Intent
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.example.okta.moviedb_iak.R

class SplashscreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        Handler().postDelayed({
            val mainInten = Intent(applicationContext, MainActivity::class.java)
            startActivity(mainInten)
            finish()
        }, 2000)
    }
}
