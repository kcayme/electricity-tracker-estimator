package com.example.electricitips

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.electricitips.R
import com.example.electricitips.databinding.FragmentLinksBinding

class Links : Fragment() {

    private lateinit var binding: FragmentLinksBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_links, container, false)
    }

    override fun onViewCreated(view:View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLinksBinding.inflate(layoutInflater)
        binding.meralcoIB.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            //pass the url to intent data
            intent.data = (Uri.parse("https://www.facebook.com/meralco"))
            startActivity(intent)
            Toast.makeText(activity, "clicked meralco", Toast.LENGTH_SHORT).show()
        }

        binding.vecoIB.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            //pass the url to intent data
            intent.data = Uri.parse("https://www.facebook.com/visayanelectriccompany")
            startActivity(intent)
            Toast.makeText(activity, "clicked veco", Toast.LENGTH_SHORT).show()
        }
    }

}

