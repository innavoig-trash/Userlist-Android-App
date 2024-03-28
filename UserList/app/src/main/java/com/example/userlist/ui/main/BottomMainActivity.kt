package com.example.userlist.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.userlist.R
import com.example.userlist.databinding.ActivityBottomMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBottomMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_bottom_main)
        navView.setupWithNavController(navController)
    }
}