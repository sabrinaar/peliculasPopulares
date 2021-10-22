package com.sabrina.peliculaspopulares.ui.Adapter

import android.content.Context
import android.provider.Settings.System.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sabrina.peliculaspopulares.R
import com.sabrina.peliculaspopulares.data.model.Pelicula
import kotlinx.android.synthetic.main.item_list_movie.view.*

class AdapterPeliculas(
    private val context: Context,
    private val peliculasList: List<Pelicula>,
    private val itemClickListener: onPeliculaClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface onPeliculaClickListener {
        fun onPeliculaClick(pelicula: Pelicula)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return MainViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_list_movie, parent, false)
        )
    }

    override fun getItemCount(): Int = peliculasList.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MainViewHolder -> holder.bind(peliculasList[position], position)
        }
    }

    inner class MainViewHolder(itemview: View) : BaseViewHolder<Pelicula>(itemview) {


        override fun bind(item: Pelicula, position: Int) {
           
            Glide.with(context).load(context.getString(R.string.portada_url_base)+item.portada).centerCrop().into(itemView.portada)
            itemView.titulo_movie.text = item.titulo
            itemView.setOnClickListener{itemClickListener.onPeliculaClick(item)}
        }
    }
}