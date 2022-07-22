package com.example.electricitips

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.electricitips.databinding.ActivityLinksBinding

class LinksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLinksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLinksBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //replaceFragment(homeFragment)

        // listener on the navigation bar
        /*binding.bottomNavView.setOnItemSelectedListener {
            // switch-case the id of the item selected in the navigation bar
            when (it.itemId) {
                R.id.home -> TODO()//replaceFragment(homeFragment)
                R.id.dashboard -> TODO()//replaceFragment(dashboardFragment)
                R.id.links -> TODO()//replaceFragment(linksFragment)
                R.id.tips -> TODO()//replaceFragment(tipsFragment)
            }
            true
        }*/
    }
}