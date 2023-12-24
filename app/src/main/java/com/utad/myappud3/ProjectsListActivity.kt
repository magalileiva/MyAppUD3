package com.utad.myappud3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.utad.myappud3.databinding.ActivityProjectsListBinding


class ProjectsListActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityProjectsListBinding
    private val binding: ActivityProjectsListBinding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityProjectsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFrangment = supportFragmentManager.findFragmentById(binding.fcvNavGraph.id)
        val controller = navHostFrangment?.findNavController()
        if(controller != null){
            binding.bnvNews.setupWithNavController(controller)
        }

    }
}
