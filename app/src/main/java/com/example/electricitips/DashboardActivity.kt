package com.example.electricitips

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.electricitips.databinding.ActivityDashboardBinding
import com.example.electricitips.databinding.ActivityMainBinding

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //replaceFragment(homeFragment)

        // listener on the navigation bar
        binding.bottomNavView.setOnItemSelectedListener {
            // switch-case the id of the item selected in the navigation bar
            when (it.itemId) {
                R.id.home -> {val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)}//replaceFragment(homeFragment)
                R.id.dashboard -> TODO()//replaceFragment(dashboardFragment)
                R.id.links -> TODO()//replaceFragment(linksFragment)
                R.id.tips -> TODO()//replaceFragment(tipsFragment)
            }
            true
        }
    }
}