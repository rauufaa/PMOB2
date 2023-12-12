package com.example.pmob2.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.pmob2.R
import com.example.pmob2.databinding.ActivityParkingLocationBinding
import com.example.pmob2.view.adapter.ParkingPageAdaptor
import com.google.android.material.tabs.TabLayout

class ParkingLocationActivity : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var pageAdaptor: ParkingPageAdaptor
    private lateinit var binding: ActivityParkingLocationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParkingLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewPager = binding.viewPagerParking

        pageAdaptor = ParkingPageAdaptor(supportFragmentManager, lifecycle)

        tabLayout = binding.tabLayout
        viewPager.adapter = pageAdaptor

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab!=null)
                    viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })



    }
}