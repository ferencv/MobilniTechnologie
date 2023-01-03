package com.example.stagviewer.wut

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.stagviewer.R
import com.example.stagviewer.databinding.FacultyTableItemBinding

/**
 * RecyclerView Adapter for setting up data binding on the items in the list.
 */
class FacultyBindingAdapter(val callback: FacultyClick) : RecyclerView.Adapter<FacultyViewHolder>() {

    var faculties: List<StagFacultyModel> = emptyList()
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacultyViewHolder {
        val withDataBinding: FacultyTableItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            FacultyViewHolder.LAYOUT,
            parent,
            false)
        return FacultyViewHolder(withDataBinding)
    }

    override fun getItemCount() = faculties.size

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     */
    override fun onBindViewHolder(holder: FacultyViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.faculty = faculties[position]
            it.clickCallback = callback
        }
    }

}

/**
 * ViewHolder for DevByte items. All work is done by data binding.
 */
class FacultyViewHolder(val viewDataBinding: FacultyTableItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.faculty_table_item
    }
    fun bind(
        item : StagFacultyModel,
        clickListener: FacultyClick
    )
    {
        viewDataBinding.faculty = item
        viewDataBinding.clickCallback = clickListener
        viewDataBinding.executePendingBindings()
    }
}

class FacultyClick(val callback: (StagFacultyModel) -> Unit) {
    fun onClick(faculty: StagFacultyModel) = callback(faculty)
}