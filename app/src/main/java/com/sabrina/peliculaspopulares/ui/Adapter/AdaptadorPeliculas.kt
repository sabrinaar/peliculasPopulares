package com.sabrina.peliculaspopulares.ui.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sabrina.peliculaspopulares.R
import com.sabrina.peliculaspopulares.data.model.Pelicula
import kotlinx.android.synthetic.main.item_list_pelicula.view.*


class AdaptadorPeliculas : RecyclerView.Adapter<AdaptadorPeliculas.ArticleViewHolder>() {

        inner class ArticleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

        private val differCallback = object : DiffUtil.ItemCallback<Pelicula>() {
            override fun areItemsTheSame(oldItem: Pelicula, newItem: Pelicula): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Pelicula, newItem: Pelicula): Boolean {
                return oldItem == newItem
            }
        }

        val differ = AsyncListDiffer(this, differCallback)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
            return ArticleViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_list_pelicula,
                    parent,
                    false
                )
            )
        }

        override fun getItemCount(): Int {
            return differ.currentList.size
        }

        private var onItemClickListener: ((Pelicula) -> Unit)? = null

        override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
            val article = differ.currentList[position]
            holder.itemView.apply {
                Glide.with(context).load(context.getString(R.string.portada_url_base185)+article.portada).centerCrop().placeholder(R.drawable.ic_launcher_foreground).into(portada)
                titulo_pelicula_item.text = article.titulo

                setOnClickListener {
                    onItemClickListener?.let { it(article) }
                }
            }
        }

        fun setOnItemClickListener(listener: (Pelicula) -> Unit) {
            onItemClickListener = listener
        }
    }

