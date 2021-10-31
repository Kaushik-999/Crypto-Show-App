package com.kaushik.cryptoshow

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kaushik.cryptoshow.retrofit.Crypto

class CryptoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val nameView: TextView = itemView.findViewById(R.id.cryptoName)
    val priceView: TextView = itemView.findViewById(R.id.cryptoPrice)
    val imageView: ImageView = itemView.findViewById(R.id.cryptoImage)
    val updateTime: TextView = itemView.findViewById(R.id.updateTime)
}


class CryptoAdapter(private val context: Context): RecyclerView.Adapter<CryptoViewHolder>() {
    private val data: ArrayList<Crypto> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.crypto_item_view,parent,false)

        return CryptoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        val currentItem = data[position]

        val symbol = currentItem.symbol
        val name = currentItem.name
        holder.nameView.text = "$name ($symbol)"

        val currPrice = currentItem.current_price.toString()
        holder.priceView.text = "₹ $currPrice"

        holder.updateTime.text = currentItem.last_updated.subSequence(0,10)

        Glide.with(context).load(currentItem.image).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updatedCrypto(updateData: ArrayList<Crypto>){
        data.clear()
        data.addAll(updateData)
        notifyDataSetChanged()
    }
}