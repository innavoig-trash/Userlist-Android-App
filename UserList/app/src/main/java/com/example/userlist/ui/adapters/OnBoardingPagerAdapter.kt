package com.example.userlist.ui.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.userlist.ui.onboarding.fragments.FirstFragment
import com.example.userlist.ui.onboarding.fragments.SecondFragment
import com.example.userlist.ui.onboarding.fragments.ThirdFragment

class OnBoardingPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FirstFragment()
            1 -> fragment = SecondFragment()
            2 -> fragment = ThirdFragment()
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 3
    }
}