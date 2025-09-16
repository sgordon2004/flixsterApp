package com.example.flixster

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flixster.databinding.FragmentMoviesBinding
import kotlinx.coroutines.launch
import com.example.flixster.BuildConfig


class MoviesFragment : Fragment(R.layout.fragment_movies) {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MoviesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentMoviesBinding.bind(view)

        adapter = MoviesAdapter { movie ->
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, MovieDetailFragment.newInstance(movie))
                .addToBackStack(null)
                .commit()
        }
        binding.rvMovies.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMovies.adapter = adapter

        // Fetch
        val apiKey = BuildConfig.TMDB_API_KEY
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val res = ApiClient.tmdb.nowPlaying(apiKey)
                adapter.submitList(res.results)
            } catch (t: Throwable) {
                Toast.makeText(requireContext(), "Failed: ${t.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView(); _binding = null
    }
    companion object { fun newInstance() = MoviesFragment() }
}