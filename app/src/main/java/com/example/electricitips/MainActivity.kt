package com.example.electricitips

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.electricitips.databinding.ActivityMainBinding
// Home
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //replaceFragment(homeFragment)

        // listener on the navigation bar
        binding.bottomNavView.setOnItemSelectedListener {
            // switch-case the id of the item selected in the navigation bar
            when(it.itemId){
                R.id.home -> TODO()//replaceFragment(homeFragment)
                R.id.dashboard -> {
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                }//replaceFragment(dashboardFragment)
                R.id.links -> TODO()//replaceFragment(linksFragment)
                R.id.tips -> TODO()//replaceFragment(tipsFragment)
            }
            true
        }
    }
 /*   // function for replacing the view when navigation button is selected
    private fun replaceFragment(fragment: Fragment){
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, fragment)
            transaction.commit()
        }
    }*/
}