package com.example.marvelapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.R
import com.example.marvelapp.model.CharacterData
import com.squareup.picasso.Picasso

class RecyclerViewAdapter(private val mListener:(CharacterData) -> Unit):
        PagingDataAdapter<CharacterData, RecyclerViewAdapter.MyViewHolder>(DiffUtilCallBack()) {
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position)!!,mListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_character,parent,false)
        return MyViewHolder(inflater)
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){

        private val cardView:CardView = view.findViewById(R.id.card_view)
        private val imageView: ImageView = view.findViewById(R.id.imageView)
        private val title: TextView = view.findViewById(R.id.character_title)
        private val subtitle: TextView = view.findViewById(R.id.character_subtitle)
        fun bind(data: CharacterData, mListener: (CharacterData) -> Unit){

            val url = "${data.thumbnail.path}.${data.thumbnail.extension}".replace("http", "https")
            try {
                Picasso.get().load(url).into(imageView)
            } catch (e:Exception){
                println("IMAGE" + e.message)
            }
           // println("URL IMAGE: $url")
            title.text = data.name
            cardView.setOnClickListener { mListener(data)}
        }
    }

    class DiffUtilCallBack: DiffUtil.ItemCallback<CharacterData>(){
        override fun areItemsTheSame(oldItem: CharacterData, newItem: CharacterData): Boolean {
            return oldItem.thumbnail.path == newItem.thumbnail.path
        }

        override fun areContentsTheSame(oldItem: CharacterData, newItem: CharacterData): Boolean {
            return oldItem.thumbnail.path == newItem.thumbnail.path
        }

    }
}