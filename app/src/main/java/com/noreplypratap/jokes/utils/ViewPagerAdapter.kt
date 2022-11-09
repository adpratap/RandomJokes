package com.noreplypratap.jokes.utils

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.noreplypratap.jokes.ui.fragments.HomeFragment
import com.noreplypratap.jokes.ui.fragments.SavedFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {HomeFragment()}
            1 -> {SavedFragment()}
            else -> {
                throw Resources.NotFoundException("Fragment Position Error...")
            }
        }
    }

}