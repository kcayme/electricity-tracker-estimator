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
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class RecyclerViewAdapter (var arrayList: ArrayList<Appliance>, val context: Fragment) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private var img = itemView.findViewById<ImageView>(R.id.card_img)
        private var name = itemView.findViewById<TextView>(R.id.card_name)
        private var type = itemView.findViewById<TextView>(R.id.card_type)
        private var rating = itemView.findViewById<TextView>(R.id.card_rating)
        private var duration = itemView.findViewById<TextView>(R.id.card_duration)
        private var frequency = itemView.findViewById<TextView>(R.id.card_frequency)

        fun bindItems(appliance: Appliance){
            img.setImageResource(appliance.imgId)
            name.text = appliance.name
            type.text = appliance.type
            rating.text = appliance.rating
            duration.text = appliance.duration
            frequency.text = appliance.frequency
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cardview_items, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(arrayList[position])

        holder.itemView.setOnLongClickListener{
            Toast.makeText(context.context,"$position selected", Toast.LENGTH_SHORT).show()
            true
        }

    }
}