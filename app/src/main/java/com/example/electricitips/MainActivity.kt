package com.example.electricitips

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputBinding
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.electricitips.databinding.ActivityMainBinding
import com.example.electricitips.fragments.*
import com.example.electricitips.databinding.FragmentInputFormBinding
import com.example.electricitips.fragments.Articles
import com.example.electricitips.fragments.Dashboard
import com.example.electricitips.fragments.Home
import com.example.electricitips.fragments.Others

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        //navController controls the navigation between fragments
        navController = navHostFragment.findNavController()
        binding.bottomNavView.setupWithNavController(navController)

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