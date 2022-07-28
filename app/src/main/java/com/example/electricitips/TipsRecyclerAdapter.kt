package com.example.electricitips

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.electricitips.fragments.TipsDirections

class TipsRecyclerAdapter (private var arrayList: ArrayList<Articles>, val context: Fragment) : RecyclerView.Adapter<TipsRecyclerAdapter.ArticleViewHolder>(){

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private var img = itemView.findViewById<ImageView>(R.id.tips_img)
        private var header = itemView.findViewById<TextView>(R.id.tips_header)
        private var writer = itemView.findViewById<TextView>(R.id.tips_writer)
        private var date = itemView.findViewById<TextView>(R.id.tips_date)
        private var content = itemView.findViewById<TextView>(R.id.tips_content)

        fun bindItems(articles : Articles){
                img.setImageResource(articles.img)
                header.text = articles.headline
                writer.text = articles.writerName
                date.text = articles.date
                content.text = articles.content
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cardview_tips, parent, false)
        return ArticleViewHolder(v)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bindItems(arrayList[position])

        val readmoreBtn = holder.itemView.findViewById<Button>(R.id.readmoreBtn)
        readmoreBtn.setOnClickListener {
            val url = when(position){
                0 -> "https://www.pna.gov.ph/articles/1095032"
                1 -> "https://www.energyliteracyph.com/learning-materials/save-energy"
                2 -> "https://company.meralco.com.ph/corporate-profile"
                3 -> "https://www.bchydro.com/powersmart/residential/tips-technologies/everyday-electricity-saving-tips.html"
                else -> "https://101appliance.com/understanding-the-energy-label/"
            }
            try {
                val action = TipsDirections.actionTipsToWebView2(url)
                val navController = Navigation.findNavController(this.context.requireActivity(),R.id.nav_host_fragment)
                navController.navigate(action)
            }
            catch (e:Exception){
                Toast.makeText(context.context,"$e",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}