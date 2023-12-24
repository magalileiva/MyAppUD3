package com.utad.myappud3.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.utad.myappud3.NewProjectActivity
import com.utad.myappud3.ProjectsListActivity
import com.utad.myappud3.adapter.ProjectsAdapter
import com.utad.myappud3.databinding.FragmentProjectListBinding
import com.utad.myappud3.model.MyApplication
import com.utad.myappud3.model.Project
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProjectListFragment : Fragment() {

    private lateinit var _binding: FragmentProjectListBinding
    private val binding: FragmentProjectListBinding get() = _binding

    private val adapter = ProjectsAdapter(
    {navigateToProject(it)},
    {deleteProject(it)}
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProjectListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvProject.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL, false)
        binding.rvProject.adapter = adapter

        obtenerListaProyectos()

        binding.fabProject.setOnClickListener {
            navigateToNewProject()
        }
    }

    private fun obtenerListaProyectos() {
        val app = requireContext().applicationContext as MyApplication
        lifecycleScope.launch(Dispatchers.IO) {
            val projects = app.room.projectDao().getAllProject()
            lifecycleScope.launch (Dispatchers.Main){
                adapter.submitList(projects)
            }
        }
    }

    private fun deleteProject(project: Project) {
        val app = requireContext().applicationContext as MyApplication
        lifecycleScope.launch(Dispatchers.IO) {
            val projects = app.room.projectDao().deleteProject(project)
            lifecycleScope.launch (Dispatchers.Main){
                obtenerListaProyectos()
            }
        }
    }

    private fun navigateToProject(project: Project) {
        val action = ProjectListFragmentDirections.actionProjectListFragmentToEditProjectFragment(project.id)
        findNavController().navigate(action)
    }

    private fun navigateToNewProject() {
        val intent = Intent(this.context, NewProjectActivity::class.java)
        startActivity(intent)
    }


}