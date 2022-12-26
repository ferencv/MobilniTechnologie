package com.example.stagviewer.Detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.stagviewer.R
import com.example.stagviewer.databinding.FragmentDetailBinding

class SubjectDetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var viewModelFactory: DetailViewModelFactory

    private val args by navArgs<SubjectDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate view and obtain an instance of the binding class.
        val binding: FragmentDetailBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_detail,
            container,
            false
        )

        viewModelFactory = DetailViewModelFactory(requireActivity().application, args.program)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(DetailViewModel::class.java)

        binding.program = viewModel.program

        return binding.root
    }

}