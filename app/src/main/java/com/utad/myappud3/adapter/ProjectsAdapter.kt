package com.utad.myappud3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.color.MaterialColors
import com.utad.myappud3.R
import com.utad.myappud3.databinding.ItemProjectsBinding
import com.utad.myappud3.model.Project

class ProjectsAdapter(
    val onClick: (project: Project) -> Unit,
    val deleteProject: (project: Project) -> Unit
):ListAdapter<Project,ProjectsAdapter.ProjectViewHolder>(ProjectCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val inflater =  LayoutInflater.from(parent.context)
        val binding = ItemProjectsBinding.inflate(inflater, parent, false)
        return ProjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val item: Project = getItem(position)
        holder.binding.tvTituloList.text = item.name
        holder.binding.tvDesc.text = item.description
        holder.binding.chipStatus.text = if(item.status==1) "Pendiente" else if(item.status==2) "En proceso" else "Finalizado"
        holder.binding.chipPrior.text = if(item.priority==1) "Alta" else if(item.priority==2) "Media" else "Baja"
        var color = if(item.priority==1)
            AppCompatResources.getColorStateList(holder.binding.chipPrior.context,R.color.rojo)
        else if(item.priority==2)
            AppCompatResources.getColorStateList(holder.binding.chipPrior.context,R.color.amarillo)
        else
            AppCompatResources.getColorStateList(holder.binding.chipPrior.context,R.color.verde)
        holder.binding.chipPrior.chipBackgroundColor = color
        holder.binding.bttDelete.setOnClickListener { deleteProject(item) }
        holder.binding.root.setOnClickListener { onClick(item) }
    }

    inner class ProjectViewHolder(val binding: ItemProjectsBinding):
        ViewHolder(binding.root)

    object ProjectCallBack: DiffUtil.ItemCallback<Project>() {
        override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean {
            return oldItem.name == newItem.name &&
                    oldItem.description == newItem.description
        }

    }
}