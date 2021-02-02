package com.ainul.academy.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ainul.academy.data.ModuleEntity
import com.ainul.academy.databinding.ItemsModuleListBinding

class DetailCourseAdapter:RecyclerView.Adapter<DetailCourseAdapter.ModuleViewHolder>() {
    private val listModules = ArrayList<ModuleEntity>()

    fun setModules(modules: List<ModuleEntity>?){
        if(modules == null) return
        this.listModules.clear()
        this.listModules.addAll(modules)
    }

    inner class ModuleViewHolder(private val binding: ItemsModuleListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(module: ModuleEntity) {
            binding.textModuleTitle.text = module.title
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailCourseAdapter.ModuleViewHolder {
        val itemModuleBinding = ItemsModuleListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ModuleViewHolder(itemModuleBinding)
    }

    override fun getItemCount(): Int = listModules.size

    override fun onBindViewHolder(holder: DetailCourseAdapter.ModuleViewHolder, position: Int) {
        val module = listModules[position]
        holder.bind(module)
    }
}