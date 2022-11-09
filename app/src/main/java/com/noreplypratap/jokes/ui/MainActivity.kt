package com.noreplypratap.jokes.ui

import android.content.res.Resources.NotFoundException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayoutMediator
import com.noreplypratap.jokes.R
import com.noreplypratap.jokes.databinding.ActivityMainBinding
import com.noreplypratap.jokes.utils.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        binding.viewPager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout,binding.viewPager){ tab , pos ->
            tab.text = when(pos){
                0 -> {"Home"}
                1 -> {"Saved"}
                else -> {
                    throw NotFoundException("Error in Tab And Pos ...")
                }
            }
        }.attach()
    }
}