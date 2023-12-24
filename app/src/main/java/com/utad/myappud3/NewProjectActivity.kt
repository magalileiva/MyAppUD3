package com.utad.myappud3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.utad.myappud3.databinding.ActivityNewProjectBinding
import com.utad.myappud3.model.MyApplication
import com.utad.myappud3.model.Project
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewProjectActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityNewProjectBinding
    private val binding: ActivityNewProjectBinding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNewProjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bttGuardar.setOnClickListener { guardarProyecto() }
    }

    private fun guardarProyecto() {
        val app = applicationContext as MyApplication

        lifecycleScope.launch(Dispatchers.IO) {
            var status = if(binding.rbStatusPendiente.isChecked) 1 else if(binding.rbStatusProceso.isChecked) 2 else 3
            var priority = if(binding.rbPriorityAlta.isChecked) 1 else if(binding.rbPriorityMedia.isChecked) 2 else 3
            app.room.projectDao().addNewProject(
                Project(
                    id = 0,
                    name = binding.tvNameProject.text.toString().trim(),
                    description = binding.tvDescProject.text.toString().trim(),
                    status = status,
                    priority = priority
                )
            )
        }

        val intent = Intent(applicationContext, ProjectsListActivity::class.java)
        startActivity(intent)
    }
}