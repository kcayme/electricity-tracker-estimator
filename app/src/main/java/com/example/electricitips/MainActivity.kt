package com.example.electricitips

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.electricitips.databinding.ActivityMainBinding
import com.example.electricitips.databinding.FragmentInputFormBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // setup navigation bar
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        //navController controls the navigation between fragments
        navController = navHostFragment.findNavController()
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.home,R.id.dashboard,R.id.links,R.id.tips)
        )
        binding.bottomNavView.setupWithNavController(navController)

        // listener still needed to ensure correct navigation
        binding.bottomNavView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> navController.navigate(R.id.home)
                R.id.dashboard -> navController.navigate(R.id.dashboard)
                R.id.links -> navController.navigate(R.id.links)
                else -> navController.navigate(R.id.tips)
            }
            true
        }

        binding.floating.setOnClickListener {
            navController.navigate(R.id.dashboard)

            val inputBind = FragmentInputFormBinding.inflate(layoutInflater)
            val items: Array<String> = resources.getStringArray(R.array.appliance_types)
            val typesAdapter = ArrayAdapter(this, R.layout.dropdown_appliance_types, items)
            inputBind.autoCompleteTypes.setAdapter(typesAdapter)

            val mBuilder = AlertDialog.Builder(this)
                .setView(inputBind.root)
                .setTitle("Login Form")
            val mAlertDialog = mBuilder.show()
        }


    }

}