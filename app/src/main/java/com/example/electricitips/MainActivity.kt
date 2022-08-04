package com.example.electricitips

import android.content.Context
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.view.KeyEvent
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import com.example.electricitips.databinding.ActivityMainBinding
import com.example.electricitips.databinding.FragmentInputFormBinding
import com.example.electricitips.databinding.FragmentWebViewBinding

/*
        Minimum Requirements:
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
    // database helper
    private lateinit var applianceDBHelper: ApplianceDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // setup db helper
        applianceDBHelper = ApplianceDBHelper(this)
        // setup navController
        navController = Navigation.findNavController(this,R.id.nav_host_fragment)

        // setup nav options builder to set a transition animations
        val options = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setExitAnim(androidx.transition.R.anim.abc_fade_out)
            .setPopEnterAnim(com.google.android.material.R.anim.abc_popup_enter)
            .setPopExitAnim(com.google.android.material.R.anim.abc_popup_exit)
            .setPopUpTo(navController.graph.startDestinationId, true)
            .build()

        // listener when nav bar item is selected
        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> navController.navigate(R.id.home,null, options)
                R.id.dashboard -> navController.navigate(R.id.dashboard,null, options)
                R.id.links -> navController.navigate(R.id.links,null, options)
                else -> navController.navigate(R.id.tips,null, options)
            }
            true
        }
        binding.bottomNavView.setOnItemReselectedListener { _ ->
            return@setOnItemReselectedListener
        }

        binding.floating.setOnClickListener {
            val mPrompt = MediaPlayer.create(this,R.raw.input)
            mPrompt.start()
            val inputBind = FragmentInputFormBinding.inflate(layoutInflater)

            val typeItems: Array<String> = resources.getStringArray(R.array.appliance_types)
            val typesAdapter = ArrayAdapter(this, R.layout.dropdown_appliance_types, typeItems)
            inputBind.inputType.setAdapter(typesAdapter)
            val freqItems: Array<String> = resources.getStringArray(R.array.frequency)
            val freqAdapter = ArrayAdapter(this, R.layout.dropdown_appliance_types, freqItems)
            inputBind.inputFreq.setAdapter(freqAdapter)

            val mBuilder = AlertDialog.Builder(this)
                .setView(inputBind.root)
                .setCancelable(false)
            val mAlertDialog = mBuilder.show()

            mAlertDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)

            // force hide keyboard when Type and Frequency inputs text are pressed
            inputBind.inputFreq.setOnClickListener {
                val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(inputBind.inputFreqLayout.windowToken,0)
            }
            inputBind.inputType.setOnClickListener {
               val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(inputBind.inputTypeLayout.windowToken,0)
            }

            inputBind.cancelBtn.setOnClickListener {
                mAlertDialog.dismiss()
            }

            inputFieldsFocusListeners(inputBind)

            inputBind.confirmBtn.setOnClickListener {

                val name = inputBind.inputName.text.toString()
                val code = inputBind.inputCode.text.toString().uppercase()
                val type = inputBind.inputType.text.toString()
                val rating = inputBind.inputRating.text.toString()
                val duration = inputBind.inputHours.text.toString()
                val freq = inputBind.inputFreq.text.toString()

                val errorMsg = "This field is required!"
                // prompt error message if fields are empty
                if(isEmpty(name) || isEmpty(code) || isEmpty(type) || isEmpty(rating) || isEmpty(duration) || isEmpty(freq)){
                    if(isEmpty(name)){
                        inputBind.inputNameLayout.isErrorEnabled = true
                        inputBind.inputNameLayout.error = errorMsg
                    }
                    if(isEmpty(code)){
                        inputBind.inputCodeLayout.isErrorEnabled = true
                        inputBind.inputCodeLayout.error = errorMsg
                    }
                    if(isEmpty(type)){
                        inputBind.inputTypeLayout.isErrorEnabled = true
                        inputBind.inputTypeLayout.error = errorMsg
                    }
                    if(isEmpty(rating)){
                        inputBind.inputRatingLayout.isErrorEnabled = true
                        inputBind.inputRatingLayout.error = errorMsg
                    }
                    if(isEmpty(duration)){
                        inputBind.inputDurationLayout.isErrorEnabled = true
                        inputBind.inputDurationLayout.error = errorMsg
                    }
                    if(isEmpty(freq)){
                        inputBind.inputFreqLayout.isErrorEnabled = true
                        inputBind.inputFreqLayout.error = errorMsg
                    }
                }
                else{
                    val mSuccess = MediaPlayer.create(this,R.raw.success)

                    val imgID: Int = getTypeIcon(type)
                    // blue text is just to ensure variables are passed to correct object parameters
                    val newAppliance = Appliance(imgId = imgID, name = name,
                        modelCode = code, type = type,
                        rating = rating.toFloat(), duration = duration.toFloat(), frequency = freq
                    )
                    applianceDBHelper.insertAppliance(newAppliance)
                    mSuccess.start()
                    Toast.makeText(this,"New item added!", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                    navController.navigate(R.id.dashboard)
                    binding.bottomNavView.menu.getItem(1).isChecked = true
                    mAlertDialog.dismiss()
                }

            }

        }

    }

    private fun inputFieldsFocusListeners(inputBind: FragmentInputFormBinding) {
        inputBind.inputName.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                inputBind.inputNameLayout.isErrorEnabled = false
            }
        }
        inputBind.inputCode.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                inputBind.inputCodeLayout.isErrorEnabled = false
            }
        }
        inputBind.inputType.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                inputBind.inputTypeLayout.isErrorEnabled = false
            }
        }
        inputBind.inputRating.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                inputBind.inputRatingLayout.isErrorEnabled = false
            }
        }
        inputBind.inputHours.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                inputBind.inputDurationLayout.isErrorEnabled = false
            }
        }
        inputBind.inputFreq.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                inputBind.inputFreqLayout.isErrorEnabled = false
            }
        }
    }

    override fun onBackPressed() {
        binding.bottomBar.performShow()
        super.onBackPressed()

        when(navController.currentDestination!!.id){
            R.id.home -> binding.bottomNavView.menu.getItem(0).isChecked = true
            R.id.dashboard -> binding.bottomNavView.menu.getItem(1).isChecked = true
            R.id.links -> binding.bottomNavView.menu.getItem(3).isChecked = true
            else -> binding.bottomNavView.menu.getItem(4).isChecked = true
        }
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