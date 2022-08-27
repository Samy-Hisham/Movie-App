package com.android.newapp.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.android.newapp.R
import com.android.newapp.data.models.MovieDetailsModel
import com.android.newapp.databinding.FragmentDetailsBinding
import com.android.newapp.helpers.Const
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val detailsViewModel: DetailsViewModel by viewModels()
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailsBinding.bind(view)

        val movieId = DetailsFragmentArgs
            .fromBundle(requireArguments()).movieId

        detailsViewModel.getMovieDetail(movieId, Const.API_KEY)

        observe()

    }

    private fun observe() {

        detailsViewModel.detailsLiveData.observe(viewLifecycleOwner) {
            intiView(it)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun intiView(model: MovieDetailsModel) {

        binding.apply {
            textDesc.text = model.overview
            textTitle.text = model.title
            textReleaseDate.text = model.release_date
            textVoteCount.text = model.vote_count
            textLanguage.text = model.original_language
            textTime.text = model.runtime.toString() + " " + "min"
            Glide.with(this@DetailsFragment)
                .load(Const.BASE_IMAGE + model.poster_path)
                .into(imageMovie)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}