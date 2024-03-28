package com.example.userlist.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import com.example.userlist.databinding.ActivityOnBoardingBinding
import com.example.userlist.ui.adapters.OnBoardingPagerAdapter
import com.example.userlist.ui.auth.AuthActivity

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()
        setListeners()
    }

    private fun setupViewPager() {
        binding.apply {
            viewPager.apply {
                val sectionPagerAdapter = OnBoardingPagerAdapter(this@OnBoardingActivity)
                adapter = sectionPagerAdapter
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        btnBack.isVisible = position != 0

                        if (position == 2) {
                            btnNext.text = StringBuilder("Finish")
                        } else {
                            btnNext.text = StringBuilder("Next")
                        }
                    }
                })
            }

            dotsIndicator.attachTo(binding.viewPager)
        }
    }

    private fun setListeners() {
        binding.apply {
            btnNext.setOnClickListener {
                if (viewPager.currentItem < 2) {
                    viewPager.currentItem = viewPager.currentItem + 1
                } else {
                    finish()
                    startActivity(Intent(this@OnBoardingActivity, AuthActivity::class.java))
                }
            }

            btnBack.setOnClickListener {
                if (viewPager.currentItem > 0) {
                    viewPager.currentItem = viewPager.currentItem - 1
                }
            }
        }
    }
}