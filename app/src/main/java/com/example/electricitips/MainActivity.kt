package com.example.electricitips

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.text.TextUtils.isEmpty
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
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
    private var arrayList = ArrayList<Appliance>()

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
        /*
        binding.bottomNavView.setOnItemSelectedListener {
            navigateFragments(it)
        }
         */


        binding.floating.setOnClickListener {
            //val manager = supportFragmentManager.fragments.size.toString()
            //Toast.makeText(this,"$manager",Toast.LENGTH_SHORT).show()
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

                if(isEmpty(name) || isEmpty(type) || isEmpty(rating) || isEmpty(duration) || isEmpty(freq)){
                    Toast.makeText(this, "Some fields are empty!",Toast.LENGTH_SHORT).show()
                }
                else{
                    var imgID: Int = getTypeIcon(type)
                    var newAppliance = Appliance(imgID,name,type,rating,duration,freq)
                    arrayList.add(newAppliance)
                    // create new dashboard object
                    val dbFragment = Dashboard()
                    // create transaction object
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    // create bundle containing user inputs
                    val bundle = Bundle()
                    bundle.putParcelableArrayList("data", arrayList)
                    if (dbFragment != null) {
                        // pass bundle as an argument of the fragment
                        dbFragment.arguments = bundle
                        // replace the current dashboard fragment in the R.id.nav_host_fragment and replace with newer instance of dashboard containing the input
                        fragmentTransaction.replace(R.id.nav_host_fragment,dbFragment, "DASHBOARD")
                        //fragmentTransaction.addToBackStack(supportFragmentManager.fragments[])
                        fragmentTransaction.commit()
                    }
                }


            }

        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.bottom_nav, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,navController) || super.onOptionsItemSelected(item)
    }

    private fun getTypeIcon(type: String) = when (type) {
        "Entertainment" -> R.drawable.entertainment
        "Lighting" -> R.drawable.lighting
        "Cooling" -> R.drawable.cooling
        "Kitchen Appliance" -> R.drawable.kitchen
        "Household Appliance" -> R.drawable.household
        else -> R.drawable.others
    }

    private fun navigateFragments(it: MenuItem): Boolean {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        when (it.itemId) {
            R.id.home -> NavigationUI.onNavDestinationSelected(it,navController)
            R.id.dashboard -> NavigationUI.onNavDestinationSelected(it,navController)
            R.id.links -> NavigationUI.onNavDestinationSelected(it,navController)
            else -> NavigationUI.onNavDestinationSelected(it,navController)
        }
        return true
    }

}