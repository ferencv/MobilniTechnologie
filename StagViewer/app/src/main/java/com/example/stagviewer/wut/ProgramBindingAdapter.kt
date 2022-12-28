package com.example.stagviewer.wut

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.stagviewer.R
import com.example.stagviewer.databinding.TableItemBinding

/**
 * RecyclerView Adapter for setting up data binding on the items in the list.
 */
class ProgramAdapter(val callback: ProgramClick) : RecyclerView.Adapter<ProgramViewHolder>() {

    var programs: List<StagProgramModel> = emptyList()
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
    fun bind(
        item : StagProgramModel,
        clickListener: ProgramClick
    )
    {
        viewDataBinding.program = item
        viewDataBinding.clickCallback = clickListener
        viewDataBinding.executePendingBindings()
    }
}

class ProgramClick(val callback: (StagProgramModel) -> Unit) {
    fun onClick(program: StagProgramModel) = callback(program)
}