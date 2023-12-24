package com.utad.myappud3.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.utad.myappud3.adapter.ProjectsAdapter
import com.utad.myappud3.databinding.FragmentEditProjectBinding
import com.utad.myappud3.model.MyApplication
import com.utad.myappud3.model.Project
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditProjectFragment : Fragment() {

    private lateinit var _binding: FragmentEditProjectBinding
    private val binding: FragmentEditProjectBinding get() = _binding
    private val args: EditProjectFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditProjectBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData(args.projectId)

        binding.bttSave.setOnClickListener { updateProject() }
    }

    private fun updateProject() {
        val app = requireContext().applicationContext as MyApplication
        lifecycleScope.launch(Dispatchers.IO) {
            var status = if(binding.rb4.isChecked) 1 else if(binding.rb5.isChecked) 2 else 3
            var priority = if(binding.rb1.isChecked) 1 else if(binding.rb2.isChecked) 2 else 3
            app.room.projectDao().updateProject(
                Project(
                    id = args.projectId,
                    name = binding.tvProj.text.toString().trim(),
                    description = binding.tvDes.text.toString().trim(),
                    status = status,
                    priority = priority
                )
            )
            lifecycleScope.launch(Dispatchers.Main) {
                val action = EditProjectFragmentDirections.actionEditProjectFragmentToProjectListFragment()
                findNavController().navigate(action)
            }
        }
    }

    private fun loadData(projectId: Int) {
        val app = requireContext().applicationContext as MyApplication
        lifecycleScope.launch(Dispatchers.IO) {
            val project = app.room.projectDao().getProjectById(projectId)
            lifecycleScope.launch(Dispatchers.Main) {
                binding.tvProj.text = project.name
                binding.tvDes.text = project.description
                if (project.priority == 1) binding.radioGroup.check(binding.rb1.id) else if ((project.priority == 2)) binding.radioGroup.check(binding.rb2.id) else binding.radioGroup.check(binding.rb3.id)
                if (project.status == 1) binding.radioGroup2.check(binding.rb4.id) else if ((project.status == 2)) binding.radioGroup2.check(binding.rb5.id) else binding.radioGroup2.check(binding.rb6.id)
            }
        }
    }

}