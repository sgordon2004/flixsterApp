package com.example.flixster
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MoviesAdapter (
    private val onClick: (Movie) -> Unit
) : ListAdapter<Movie, MoviesAdapter.VH>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(a: Movie, b: Movie) = a.id == b.id
            override fun areContentsTheSame(a: Movie, b: Movie) = a == b
        }
    }

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        private val poster: ImageView = view.findViewById(R.id.poster)
        private val title: TextView = view.findViewById(R.id.title)
        private val overview: TextView = view.findViewById(R.id.overview)

        fun bind(m: Movie) {
            title.text = m.title
            overview.text = m.overview

            val posterPath = m.posterPath
            if (posterPath.isNullOrBlank()) {
                // Show a placeholder if there's no poster path at all
                poster.setImageResource(R.drawable.outline_broken_image_24)
            } else {
                // Make sure the path has a leading slash
                val path = if (posterPath.startsWith("/")) posterPath else "/$posterPath"
                val url = ImageConfig.POSTER_BASE + path

                // Debug log to verify URL
                Log.d("MoviesAdapter", "Loading poster: $url")

                Glide.with(itemView)
                    .load(url)
                    .placeholder(R.drawable.outline_broken_image_24)
                    .error(R.drawable.outline_broken_image_24)
                    .centerCrop()
                    .into(poster)
            }

            itemView.setOnClickListener { onClick(m) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))
}