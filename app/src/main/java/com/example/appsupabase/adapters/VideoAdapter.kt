package com.example.appsupabase.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.appsupabase.R
import com.example.appsupabase.models.Video

class VideoAdapter(context: Context, var videos: ArrayList<Video>) : ArrayAdapter<Video>(
    context,
    R.layout.item_video,
    videos
) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater
            .from(context)
            .inflate(R.layout.item_video, parent, false)

        val video = videos[position]

        val txtTitulo = view.findViewById<TextView>(R.id.txtTitulo)
        val txtFecha = view.findViewById<TextView>(R.id.txtFecha)
        val txtUrl = view.findViewById<TextView>(R.id.txtUrl)
        val imgPortada = view.findViewById<ImageView>(R.id.imgPortada)

        txtTitulo.text = video.titulo
        txtFecha.text = video.fechapub
        txtUrl.text = video.urlvideo1

        val videoId = video.urlvideo1
            .substringAfterLast("/")
            .substringBefore("?")

        Glide.with(context)
            .load("https://img.youtube.com/vi/$videoId/hqdefault.jpg")
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .into(imgPortada)

        return view
    }
}