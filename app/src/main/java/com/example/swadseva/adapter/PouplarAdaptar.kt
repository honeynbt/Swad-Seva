package com.example.swadseva.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.swadseva.DetailsActivity
import com.example.swadseva.databinding.PopularItemBinding

class PouplarAdaptar (private val items: List<String>, private val prices:List<String>, private val images:List<Int>, private val requireContext: Context) : RecyclerView.Adapter<PouplarAdaptar.PouplerViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PouplarAdaptar.PouplerViewHolder {
        return PouplerViewHolder(PopularItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PouplarAdaptar.PouplerViewHolder, position: Int) {
        val item = items[position]
        val price= prices[position]
        val image = images[position]
        holder.bind(item, price, image)

        holder.itemView.setOnClickListener {
            val intent = Intent(requireContext, DetailsActivity::class.java)
            intent.putExtra("MenuItemName", item)
            intent.putExtra("MenuItemImage", image)
            requireContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class PouplerViewHolder (private val binding: PopularItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val imagesView = binding.imageView5
        fun bind(item: String, price: String, image: Int) {
            binding.foodPopular.text = item
            binding.pricePopular.text = price
            imagesView.setImageResource(image)
        }

    }

}