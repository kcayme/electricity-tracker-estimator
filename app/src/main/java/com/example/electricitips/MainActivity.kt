package com.example.electricitips

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.electricitips.databinding.ActivityMainBinding
import com.example.electricitips.fragments.Articles
import com.example.electricitips.fragments.Dashboard
import com.example.electricitips.fragments.Home
import com.example.electricitips.fragments.Others

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    // create instances of the fragment objects
    private val homeFragment = Home()
    private val dashboardFragment = Dashboard()
    private val articlesFragment = Articles()
    private val othersFragment = Others()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(homeFragment)

        // listener on the navigation bar
        binding.bottomNavView.setOnItemSelectedListener {
            // switch-case the id of the item selected in the navigation bar
            when(it.itemId){
                R.id.home -> replaceFragment(homeFragment)
                R.id.dashboard -> replaceFragment(dashboardFragment)
                R.id.articles -> replaceFragment(articlesFragment)
                R.id.others -> replaceFragment(othersFragment)
            }
            true
        }
    }
    // function for replacing the view when navigation button is selected
    private fun replaceFragment(fragment: Fragment){
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
}