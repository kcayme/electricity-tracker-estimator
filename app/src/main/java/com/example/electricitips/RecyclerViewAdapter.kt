package com.example.electricitips

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.example.electricitips.R
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class RecyclerViewAdapter (var arrayList: ArrayList<Appliance>, val context: Fragment) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private lateinit var applianceDBHelper: ApplianceDBHelper

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private var img = itemView.findViewById<ImageView>(R.id.card_img)
        private var modelCode = itemView.findViewById<TextView>(R.id.card_modelcode)
        private var name = itemView.findViewById<TextView>(R.id.card_name)
        private var type = itemView.findViewById<TextView>(R.id.card_type)
        private var rating = itemView.findViewById<TextView>(R.id.card_rating)
        private var duration = itemView.findViewById<TextView>(R.id.card_duration)
        private var frequency = itemView.findViewById<TextView>(R.id.card_frequency)

        fun bindItems(appliance: Appliance){
            img.setImageResource(appliance.imgId)
            name.text = appliance.name
            modelCode.text = appliance.modelCode
            type.text = appliance.type
            rating.text = appliance.rating.toString()
            duration.text = appliance.duration.toString()
            frequency.text = appliance.frequency
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cardview_items, parent, false)
        // initialize db helper
        applianceDBHelper = ApplianceDBHelper(context.context!!)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(arrayList[position])

        holder.itemView.setOnLongClickListener{
            Toast.makeText(context.context,"${arrayList[position].modelCode} selected", Toast.LENGTH_SHORT).show()
            AlertDialog.Builder(context!!.context!!)
                .setTitle("Delete")
                .setMessage("Proceed to delete item?")
                .setPositiveButton("OK"){ dialog, which ->
                    // delete item from database
                    applianceDBHelper.deleteAppliance(arrayList.get(position).modelCode)
                    // delete item from adapter's arraylist
                    arrayList.removeAt(position)
                    // update recycler view
                    notifyDataSetChanged()
                }
                .setNegativeButton("Cancel"){_,_ ->
                }
                .show()
            true
        }

    }
}