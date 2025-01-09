package com.adambulette.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.adambulette.R
import com.adambulette.utils.hideKeyboard
import com.adambulette.utils.isTablet

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.hide()
        this.hideKeyboard(view = View(this))
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        Handler().postDelayed({
            if (!isTablet(this)) {
                finish()
            }else{
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }, 2000)
        setContentView(R.layout.activity_splash)
    }
}