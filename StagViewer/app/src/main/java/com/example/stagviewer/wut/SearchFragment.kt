package com.example.stagviewer.wut

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stagviewer.R
import com.example.stagviewer.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {

    private var viewModelAdapter: ProgramAdapter? = null

    private val viewModel: SearchViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, SearchViewModel.Factory(activity.application))
            .get(SearchViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.programs.observe(viewLifecycleOwner, Observer<List<StagProgramModel>> { programs ->
            programs?.apply {
                viewModelAdapter?.programs = programs
                viewModel.updateResultString(programs.size)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentSearchBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search,
            container,
            false)
        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.setLifecycleOwner(viewLifecycleOwner)

        binding.viewModel = viewModel

        binding.submitButton.setOnClickListener(
            {
                viewModel.searchNameChanged(binding.nameInput.text.toString())
            });

        val nameObserver = Observer<String> { newName ->
            binding.resultStringLabel.text = newName
            binding.resultStringLabel2.text = newName
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        viewModel.resultString.observe(viewLifecycleOwner, nameObserver)

        viewModelAdapter = ProgramAdapter(ProgramClick {
            //Toast.makeText(activity, "clicked", Toast.LENGTH_LONG).show()

            val action = SearchFragmentDirections.actionSearchFragmentToSubjectDetailFragment(it)
            binding.root.findNavController().navigate(action)

//            binding.root.findNavController()
//               .navigate(R.id.subjectDetailFragment)
        })

        binding.root.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }


        // Observer for the network error.
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

        return binding.root
    }


    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

}

