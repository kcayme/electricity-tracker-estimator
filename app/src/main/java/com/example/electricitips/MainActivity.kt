package com.example.electricitips

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.text.TextUtils.isEmpty
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.getSystemService
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
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
import com.example.electricitips.fragments.Home
import com.example.electricitips.fragments.Links
import com.example.electricitips.fragments.Tips

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
    // navigation components
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    // arraylist that holds item inputs
    private var arrayList = ArrayList<Appliance>()
    // this is a ViewModel class that holds data (the added items and electricity rate input) that can be used by all fragments and main activity

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
            val fragTransaction = supportFragmentManager.beginTransaction()
            when (it.itemId) {
                R.id.home -> {
                    val fragHome = Home()
                    fragTransaction.replace(R.id.nav_host_fragment, fragHome, "HOME")
                    fragTransaction.commit()
                    true
                }
                R.id.dashboard -> {
                    val fragDash = Dashboard()
                    if (arrayList != null){
                        val bundle = Bundle()
                        bundle.putParcelableArrayList("data", arrayList)
                        fragDash.arguments = bundle
                    }
                    fragTransaction.replace(R.id.nav_host_fragment,fragDash, "DASHBOARD")
                    fragTransaction.commit()
                    true
                }
                R.id.links -> {
                    val fragLinks = Links()
                    fragTransaction.replace(R.id.nav_host_fragment, fragLinks, "LINKS")
                    fragTransaction.commit()
                    true
                }
                else -> {
                    val fragTips = Tips()
                    fragTransaction.replace(R.id.nav_host_fragment, fragTips, "TIPS")
                    fragTransaction.commit()
                    true
                }
            }

            true
        }

        binding.floating.setOnClickListener {
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
            mAlertDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            mAlertDialog.window!!.setBackgroundBlurRadius(3)
            navController.navigate(R.id.dashboard)

            // force hide keyboard when Type and Frequency inputs text are pressed
            inputBind.inputFreq.setOnClickListener {
                val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(inputBind.freqlayout.windowToken,0)
            }
            inputBind.inputType.setOnClickListener {
                val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(inputBind.typelayout.windowToken,0)
            }

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
                        fragmentTransaction.replace(R.id.nav_host_fragment,dbFragment, "DASHBOARD")
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
        return  item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    private fun getTypeIcon(type: String) = when (type) {
        "Entertainment" -> R.drawable.entertainment
        "Lighting" -> R.drawable.lighting
        "Cooling" -> R.drawable.cooling
        "Kitchen Appliance" -> R.drawable.kitchen
        "Household Appliance" -> R.drawable.household
        else -> R.drawable.others
    }
}