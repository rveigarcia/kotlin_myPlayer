package com.mon.myplayer2

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.service.controls.templates.ThumbnailTemplate
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mon.myplayer2.databinding.ViewMediaItemBinding
import kotlin.properties.Delegates

typealias Listener = (MediaItem) -> Unit

class MediaAdapter (
    items: List<MediaItem> = emptyList(),
    private val listener: Listener
):
    RecyclerView.Adapter<MediaAdapter.ViewHolder>() {

    var items: List<MediaItem> by Delegates.observable(items){_, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.view_media_item)
   //     val binding = ViewMediaItemBinding.inflate((LayoutInflater.from(parent.context),parent,false))
        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { listener(item) }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        private val binding = ViewMediaItemBinding.bind(view)

        fun bind(mediaItem: MediaItem){
            with(binding){
                mediaTitle.text = mediaItem.title
                mediaThumb.loadUrl(mediaItem.url)
                root.setOnClickListener {toast(mediaItem.title)}

                mediaVideoIndicador.visibility = when (mediaItem.type){
                    MediaItem.Type.PHOTO -> View.GONE
                    MediaItem.Type.VIDEO -> View.VISIBLE
                }
            }
        }
    }
}