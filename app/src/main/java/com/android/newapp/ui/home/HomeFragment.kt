package com.android.newapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.newapp.R
import com.android.newapp.adapters.AdapterRecyclerHome
import com.android.newapp.data.models.Result
import com.android.newapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var adapter: AdapterRecyclerHome? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        adapter = AdapterRecyclerHome {
            findNavController()
                .navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(it!!))
        }

        homeViewModel.getMovie()

        observe()
    }

    private fun observe() {

        homeViewModel.movieLiveData?.observe(viewLifecycleOwner) {
            adapter?.list = it as? ArrayList<Result>
            binding.recyclerMovies.adapter = adapter
        }
    }
}