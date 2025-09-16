package com.example.flixster
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.flixster.databinding.FragmentMovieDetailBinding

class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentMovieDetailBinding.bind(view)
        val movie = requireArguments().getParcelable<Movie>("movie")!!

        binding.title.text = movie.title
        binding.rating.text = "Rating: " + String.format("%.1f", movie.voteAverage)
        binding.overview.text = movie.overview
        movie.backdropPath?.let {
            Glide.with(binding.backdrop).load(ImageConfig.BACKDROP_BASE + it).into(binding.backdrop)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView(); _binding = null
    }
    companion object {
        fun newInstance(movie: Movie) = MovieDetailFragment().apply {
            arguments = bundleOf("movie" to movie)
        }
    }
}