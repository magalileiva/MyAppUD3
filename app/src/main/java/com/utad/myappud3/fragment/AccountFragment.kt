package com.utad.myappud3.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.utad.myappud3.MainActivity
import com.utad.myappud3.adapter.ProjectsAdapter
import com.utad.myappud3.data_store.DataStoreManager
import com.utad.myappud3.databinding.FragmentAccountBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountFragment : Fragment() {

    private lateinit var _binding: FragmentAccountBinding
    private val binding: FragmentAccountBinding get() = _binding

    private lateinit var adapter: ProjectsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadUserData()

        binding.bttSalir.setOnClickListener { onClickSalir() }
        binding.bttEliminar.setOnClickListener { onClickEliminar() }

    }

    private fun onClickEliminar() {
        lifecycleScope.launch(Dispatchers.IO) {
            DataStoreManager.deleteUser(requireContext())
            lifecycleScope.launch(Dispatchers.Main) {
                navigateToLogin()
            }
        }
    }

    private fun onClickSalir() {
        lifecycleScope.launch(Dispatchers.IO) {
            DataStoreManager.deleteIsLogin(requireContext())
            lifecycleScope.launch(Dispatchers.Main) {
                navigateToLogin()
            }
        }

    }

    private fun loadUserData() {
        lifecycleScope.launch(Dispatchers.IO) {
            DataStoreManager.getUser(requireContext()).collect { user ->
                lifecycleScope.launch(Dispatchers.Main) {
                    binding.tvUser.text = user
                }
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            DataStoreManager.getPassword(requireContext()).collect { password ->
                lifecycleScope.launch(Dispatchers.Main) {
                    binding.tvPassvalue.text = password
                }
            }
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this.context, MainActivity::class.java)
        startActivity(intent)
    }

}