package com.example.electricitips


import android.media.MediaPlayer
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment

class RecyclerViewAdapter (private var arrayList: ArrayList<Appliance>, val context: Fragment) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private lateinit var applianceDBHelper: ApplianceDBHelper
    private var mDelete = MediaPlayer.create(context.requireContext(), R.raw.delete)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private var img = itemView.findViewById<ImageView>(R.id.card_img)
        private var modelCode = itemView.findViewById<TextView>(R.id.card_code)
        private var name = itemView.findViewById<TextView>(R.id.card_name)
        private var type = itemView.findViewById<TextView>(R.id.card_type)
        private var rating = itemView.findViewById<TextView>(R.id.card_rating)
        private var duration = itemView.findViewById<TextView>(R.id.card_duration)
        private var frequency = itemView.findViewById<TextView>(R.id.card_frequency)
        private var linearlayout = itemView.findViewById<LinearLayout>(R.id.card_linearlayout)
        private var cardBackground = itemView.findViewById<CardView>(R.id.dashboard_cardview)

        fun bindItems(appliance: Appliance){
            if(appliance.imgId == R.drawable.empty){
                img.setImageResource(appliance.imgId)
                name.text = appliance.name
                modelCode.visibility = View.GONE
                type.visibility = View.GONE
                rating.text = "N/A"
                duration.text = "N/A"
                frequency.visibility = View.GONE
            }
            else{
                img.setImageResource(appliance.imgId)
                name.text = appliance.name
                modelCode.text = appliance.modelCode
                type.text = appliance.type
                rating.text = appliance.rating.toString()
                duration.text = appliance.duration.toString()
                frequency.text = appliance.frequency
                linearlayout.visibility = View.GONE
            }

            when(appliance.type){
                "Entertainment" -> cardBackground.setCardBackgroundColor(ContextCompat.getColor(cardBackground.context, R.color.theme_red))
                "Lighting" -> cardBackground.setCardBackgroundColor(ContextCompat.getColor(cardBackground.context, R.color.theme_green))
                "Cooling" -> cardBackground.setCardBackgroundColor(ContextCompat.getColor(cardBackground.context, R.color.theme_blue))
                "Kitchen Appliance" -> cardBackground.setCardBackgroundColor(ContextCompat.getColor(cardBackground.context, R.color.theme_pink))
                "Household Appliance" -> cardBackground.setCardBackgroundColor(ContextCompat.getColor(cardBackground.context, R.color.theme_yellow))
                "Others" -> cardBackground.setCardBackgroundColor(ContextCompat.getColor(cardBackground.context, R.color.theme_orange))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cardview_items, parent, false)
        // initialize db helper
        applianceDBHelper = ApplianceDBHelper(context.requireContext())




        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(arrayList[position])

        val collapseBtn = holder.itemView.findViewById<Button>(R.id.card_collapseBtn)
        collapseBtn.setOnClickListener {
            val holderLL = holder.itemView.findViewById<LinearLayout>(R.id.card_linearlayout)
            if(holderLL.visibility != View.VISIBLE){
                holderLL.visibility = View.VISIBLE
                collapseBtn.setBackgroundResource(R.drawable.collapse)
            }
            else{
                holderLL.visibility = View.GONE
                collapseBtn.setBackgroundResource(R.drawable.expand)
            }
        }

        holder.itemView.setOnLongClickListener {
            if(holder.itemView.findViewById<TextView>(R.id.card_rating).text != "N/A"){
                AlertDialog.Builder(context.requireContext())
                    .setMessage("Proceed to delete item?")
                    .setPositiveButton("OK") { _, _ ->
                        mDelete.start()
                        try {
                            // delete item from database
                            val del = applianceDBHelper.deleteAppliance(arrayList[position].modelCode)
                            arrayList.removeAt(position)

                            if (arrayList.size == 0) {
                                arrayList.add(
                                    Appliance(
                                        R.drawable.empty,
                                        "No items to show",
                                        "",
                                        "",
                                        0.0f,
                                        0.0f,
                                        ""
                                    )
                                )
                            }
                            notifyDataSetChanged()
                        }
                        catch (e: Exception){
                            Toast.makeText(context.requireContext(),"$e",Toast.LENGTH_SHORT).show()
                        }
                    }
                    .setNegativeButton("Cancel") { _, _ ->
                    }
                    .show()
            }
            true
        }




    }
}