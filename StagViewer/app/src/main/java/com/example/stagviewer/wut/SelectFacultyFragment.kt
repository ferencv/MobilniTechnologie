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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stagviewer.R
import com.example.stagviewer.databinding.FragmentSelectFacultyBinding


class SelectFacultyFragment : Fragment() {

    private val args by navArgs<SelectFacultyFragmentArgs>()
    private var viewModelAdapter: FacultyBindingAdapter? = null

    private val viewModel: FacultySelectViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, FacultySelectViewModelFactory(activity.application, args.filter?: ProgramsFilter("")))
            .get(FacultySelectViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.faculties.observe(viewLifecycleOwner, Observer<List<StagFacultyModel>> { items ->
            items?.apply {
                viewModelAdapter?.faculties = items
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentSelectFacultyBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_select_faculty,
            container,
            false)
        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.setLifecycleOwner(viewLifecycleOwner)

        binding.viewModel = viewModel

        viewModelAdapter = FacultyBindingAdapter(FacultyClick {

            val action = SelectFacultyFragmentDirections.actionSelectFacultyFragmentToSearchFragment(ProgramsFilter(viewModel.searchString?.value?:"", it.abbrev, it.name))
            binding.root.findNavController().navigate(action)

        })

        binding.root.findViewById<RecyclerView>(R.id.faculty_recycler_view).apply {
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
            Toast.makeText(activity,"Fakulty: Nepodařilo se načíst data ze služby, budou se zobrazovat pouze dříve zakešované údaje.", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

}

