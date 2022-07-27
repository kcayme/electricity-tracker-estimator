package com.example.electricitips.fragments

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.electricitips.R
import com.example.electricitips.databinding.FragmentLinksBinding

class Links : Fragment(R.layout.fragment_links), View.OnClickListener {

    private lateinit var binding: FragmentLinksBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLinksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view:View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        binding.meralcoIB.setOnClickListener(this)
        binding.vecoIB.setOnClickListener(this)
        binding.dlpcIB.setOnClickListener(this)
        binding.doeIB.setOnClickListener(this)
        binding.napocorIB.setOnClickListener(this)
        binding.ngcpIB.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            binding.meralcoIB.id -> {
                playSelectClick()
                Toast.makeText(activity, "You clicked MERALCO.", Toast.LENGTH_SHORT).show()
                linksDialog(0)
            }
            binding.vecoIB.id -> {
                playSelectClick()
                Toast.makeText(activity, "You clicked VECO.", Toast.LENGTH_SHORT).show()
                linksDialog(1)
            }
            binding.dlpcIB.id -> {
                playSelectClick()
                Toast.makeText(activity, "You clicked DLPC.", Toast.LENGTH_SHORT).show()
                linksDialog(2)
            }
            binding.doeIB.id -> {
                playSelectClick()
                Toast.makeText(activity, "You clicked DOE.", Toast.LENGTH_SHORT).show()
                linksDialog(3)
            }
            binding.napocorIB.id -> {
                playSelectClick()
                Toast.makeText(activity, "You clicked NAPOCOR.", Toast.LENGTH_SHORT).show()
                linksDialog(4)
            }
            binding.ngcpIB.id -> {
                playSelectClick()
                Toast.makeText(activity, "You clicked NGCP.", Toast.LENGTH_SHORT).show()
                linksDialog(5)
            }
        }
    }

    private fun playSelectClick() {
        val mMusic = MediaPlayer.create(activity, R.raw.selectclick)
        mMusic?.start()
    }



    private fun linksDialog(selectedItem:Int) {
        val items: Array<String> = arrayOf("Facebook", "Official Website")
        var checkItem = 0
        AlertDialog.Builder(context!!)
            .setIcon(R.drawable.baseline_language_24)
            .setTitle("Select which site to open:")
            .setSingleChoiceItems(items, checkItem) { _, which ->
                checkItem = which
            }
            .setPositiveButton("OK"){ _, _ ->
                if (checkItem==0) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    when(selectedItem) {
                        0 -> intent.data = Uri.parse(resources.getString(R.string.meralcoFB))
                        1 -> intent.data = Uri.parse(resources.getString(R.string.vecoFB))
                        2 -> intent.data = Uri.parse(resources.getString(R.string.dlpcFB))
                        3 -> intent.data = Uri.parse(resources.getString(R.string.doeFB))
                        4 -> intent.data = Uri.parse(resources.getString(R.string.napocorFB))
                        5 -> intent.data = Uri.parse(resources.getString(R.string.ngcpFB))
                    }
                    startActivity(intent)
                } else if(checkItem==1){
                    val intent = Intent(Intent.ACTION_VIEW)
                    when(selectedItem) {
                        0 -> intent.data = Uri.parse(resources.getString(R.string.meralcoOS))
                        1 -> intent.data = Uri.parse(resources.getString(R.string.vecoOS))
                        2 -> intent.data = Uri.parse(resources.getString(R.string.dlpcOS))
                        3 -> intent.data = Uri.parse(resources.getString(R.string.doeOS))
                        4 -> intent.data = Uri.parse(resources.getString(R.string.napocorOS))
                        5 -> intent.data = Uri.parse(resources.getString(R.string.ngcpOS))
                    }
                    startActivity(intent)
                }
                playClickTone()
            }
            .setNegativeButton("CANCEL"){ _, _ ->
                playClickTone()
            }
            .show()
    }

    private fun playClickTone() {
        val mMusic = MediaPlayer.create(activity, R.raw.coolinterfaceclicktone)
        mMusic?.start()
    }
}