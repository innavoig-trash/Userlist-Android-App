package com.example.userlist.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.example.userlist.databinding.ActivitySplashBinding
import com.example.userlist.ui.main.BottomMainActivity
import com.example.userlist.ui.onboarding.OnBoardingActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val viewModel by viewModels<SplashViewModel> {
        SplashViewModelFactory(this.applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSplash()
    }

    private fun setSplash() {
        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.getIsLoggedIn().observe(this@SplashActivity) {
                if (it) {
                    finish()
                    startActivity(Intent(this@SplashActivity, BottomMainActivity::class.java))
                } else {
                    finish()
                    startActivity(Intent(this@SplashActivity, OnBoardingActivity::class.java))
                }
            }
        }, 3000L)
    }
}