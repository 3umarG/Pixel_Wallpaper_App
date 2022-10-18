package com.example.pixelwallpaperapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pixelwallpaperapp.R
import com.example.pixelwallpaperapp.pojo.Photo
import com.example.pixelwallpaperapp.pojo.WallpaperModel

class RVAdapter(
    private var listOfWallpapers: List<Photo>,
    private val listener: OnImageClickListener
) :
    RecyclerView.Adapter<RVAdapter.WallpaperViewHolder>() {

    inner class WallpaperViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
        private val imageView = v.findViewById<ImageView>(R.id.imageView)
        fun bind(photo: Photo) {
            Glide
                .with(v)
                .load(photo.src.portrait)
                .placeholder(R.drawable.progress_animation)
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_view, parent, false)
        return WallpaperViewHolder(view)
    }

    override fun onBindViewHolder(holder: WallpaperViewHolder, position: Int) {
        holder.bind(listOfWallpapers[position])
        holder.itemView.setOnClickListener {
            listener.onImageClick(listOfWallpapers[position])
        }

    }

    override fun getItemCount(): Int {
        return listOfWallpapers.size
    }

    fun searchLoad(list: List<Photo>) {
        listOfWallpapers = list
        notifyDataSetChanged()
    }


}