package com.example.electricitips

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.electricitips.databinding.ActivityMainBinding
import com.example.electricitips.databinding.FragmentInputFormBinding
import com.example.electricitips.fragments.Dashboard

/*
        Minimum Requiresments:
        a. Segues (Multi-scene)
        b. Embed in Tab Bar / Navigation View Controller
        c. Appropriate User Interfaces
        d. Alert View / Action View
        e. Images and Sounds
        f. Table Views / Picker View / Web View (better option, as per app requires)
        g. Dynamic  Data  Persistence:  Property  List  /  Core  Data  /  SQLite  (better  option,  as  per  app
        requires)
        h. App Icon
 */
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
            navigateFragments(it)
        }

        binding.floating.setOnClickListener {
            navController.navigate(R.id.dashboard)

            val inputBind = FragmentInputFormBinding.inflate(layoutInflater)

            val typeItems: Array<String> = resources.getStringArray(R.array.appliance_types)
            val typesAdapter = ArrayAdapter(this, R.layout.dropdown_appliance_types, typeItems)
            inputBind.inputType.setAdapter(typesAdapter)
            val freqItems: Array<String> = resources.getStringArray(R.array.frequency)
            val freqAdapter = ArrayAdapter(this, R.layout.dropdown_appliance_types, freqItems)
            inputBind.inputFreq.setAdapter(freqAdapter)

            val mBuilder = AlertDialog.Builder(this)
                .setView(inputBind.root)
                .setCancelable(true)
                .setIcon(R.drawable.ic_baseline_input_24)
            val mAlertDialog = mBuilder.show()

            inputBind.cancelBtn.setOnClickListener {
                mAlertDialog.dismiss()
            }

            inputBind.confirmBtn.setOnClickListener {
                val name = inputBind.inputName.text.toString()
                val type = inputBind.inputType.text.toString()
                val rating = inputBind.inputRating.text.toString()
                val duration = inputBind.inputHours.text.toString()
                val freq = inputBind.inputFreq.text.toString()
                mAlertDialog.dismiss()
                // create new dashboard object
                val dbFragment = Dashboard()
                // create transaction object
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                // create bundle containg user inputs
                val bundle = Bundle()
                bundle.putStringArrayList("data", arrayListOf("$name","$type","$rating","$duration","$freq"))
                if (dbFragment != null) {
                    // pass bundle as an argument of the fragment
                    dbFragment.arguments = bundle
                }
                if (dbFragment != null) {
                    // replace the current dashboard fragment in the R.id.nav_host_fragment and replace with newer instance of dashboard containing the input
                    fragmentTransaction.replace(R.id.nav_host_fragment,dbFragment).commit()
                }
            }

        }

    }

    private fun navigateFragments(it: MenuItem): Boolean {
        when (it.itemId) {
            R.id.home -> navController.navigate(R.id.home)
            R.id.dashboard -> navController.navigate(R.id.dashboard)
            R.id.links -> navController.navigate(R.id.links)
            else -> navController.navigate(R.id.tips)
        }
        return true
    }

}