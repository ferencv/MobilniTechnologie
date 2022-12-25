package com.example.stagviewer.wut

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stagviewer.R
import com.example.stagviewer.databinding.FragmentSearchBinding
import com.example.stagviewer.databinding.TableItemBinding

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
        viewModel.programs.observe(viewLifecycleOwner, Observer<List<ProgramModel>> { programs ->
            programs?.apply {
                viewModelAdapter?.programs = programs
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

        viewModelAdapter = ProgramAdapter(ProgramClick {
            // When a video is clicked this block or lambda will be called by DevByteAdapter

            // context is not around, we can safely discard this click since the Fragment is no
            // longer on the screen
            val packageManager = context?.packageManager ?: return@ProgramClick

//            // Try to generate a direct intent to the YouTube app
//            var intent = Intent(Intent.ACTION_VIEW, it.launchUri)
//            if(intent.resolveActivity(packageManager) == null) {
//                // YouTube app isn't found, use the web url
//                intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
//            }

            //startActivity(intent)
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

class ProgramClick(val block: (ProgramModel) -> Unit) {
    fun onClick(program: ProgramModel) = block(program)
}

/**
 * RecyclerView Adapter for setting up data binding on the items in the list.
 */
class ProgramAdapter(val callback: ProgramClick) : RecyclerView.Adapter<ProgramViewHolder>() {

    var programs: List<ProgramModel> = emptyList()
        set(value) {
            field = value
            // For an extra challenge, update this to use the paging library.

            // Notify any registered observers that the data set has changed. This will cause every
            // element in our RecyclerView to be invalidated.
            notifyDataSetChanged()
        }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramViewHolder {
        val withDataBinding: TableItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ProgramViewHolder.LAYOUT,
            parent,
            false)
        return ProgramViewHolder(withDataBinding)
    }

    override fun getItemCount() = programs.size

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     */
    override fun onBindViewHolder(holder: ProgramViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.program = programs[position]
            it.clickCallback = callback
        }
    }

}

/**
 * ViewHolder for DevByte items. All work is done by data binding.
 */
class ProgramViewHolder(val viewDataBinding: TableItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.table_item
    }
}