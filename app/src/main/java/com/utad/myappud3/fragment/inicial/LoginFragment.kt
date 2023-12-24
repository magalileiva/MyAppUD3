package com.utad.myappud3.fragment.inicial

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.utad.myappud3.data_store.DataStoreManager
import com.utad.myappud3.ProjectsListActivity
import com.utad.myappud3.R
import com.utad.myappud3.databinding.FragmentLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private lateinit var _binding: FragmentLoginBinding
    private val binding: FragmentLoginBinding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch(Dispatchers.IO) {
            autoLogin()
        }

        binding.bttLogin.setOnClickListener { DoLogin() }
        binding.bttNew.setOnClickListener { navigateToNewAccount() }
    }

    private fun DoLogin() {
        val name = binding.tvName.text.toString().trim()
        val password = binding.tvPass.text.toString().trim()

        if (!name.isNullOrEmpty() && !password.isNullOrEmpty()) {
            obtainUserAndPassword(name, password)
        } else {
            Toast.makeText(
                requireContext(),
                R.string.loginError,
                Toast.LENGTH_SHORT
            ).show();
        }
    }

    private fun obtainUserAndPassword(name: String, passwordParam: String) {
        var isPasswordValid: Boolean? = null
        var isNameValid: Boolean? = null

        lifecycleScope.launch(Dispatchers.IO) {
            DataStoreManager.getUser(requireContext()).collect { user ->
                isNameValid = user == name
                lifecycleScope.launch(Dispatchers.Main) {
                    checkCredentials(isNameValid, isPasswordValid)
                }
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            DataStoreManager.getPassword(requireContext()).collect { password ->
                isPasswordValid = passwordParam == password
                lifecycleScope.launch(Dispatchers.Main) {
                    checkCredentials(isNameValid, isPasswordValid)
                }
            }
        }
    }

    private suspend fun autoLogin() {
        DataStoreManager.getIsUserLogged(requireContext()).collect { isUserLogged ->
            if (isUserLogged) {
                lifecycleScope.launch(Dispatchers.Main) {
                    navigateToData()
                }
            }
        }
    }

    private fun checkCredentials(isNameValid: Boolean?, isPasswordValid: Boolean?) {
        if (isNameValid == true && isPasswordValid == true) {
            setUserLogged()
            navigateToData()
        } else if (isNameValid != null && !isNameValid!!) {
            Toast.makeText(requireContext(), "Login incorrecto", Toast.LENGTH_SHORT).show()
        } else if (isPasswordValid != null && !isPasswordValid!!) {
            Toast.makeText(requireContext(), "Login incorrecto", Toast.LENGTH_SHORT).show()

        }
    }

    private fun setUserLogged() {
        //Estamos guardando que el usuario ha logeado
        lifecycleScope.launch(Dispatchers.IO) {
            DataStoreManager.setUserLogged(requireContext())
        }
    }

    private fun navigateToNewAccount() {
        val accountFragmnet = RegisterFragment()
        val transaction = parentFragmentManager.beginTransaction()
        transaction.setReorderingAllowed(true)
        transaction.replace(R.id.fcv_main, accountFragmnet)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.addToBackStack("Login")
        transaction.commit()
    }


    private fun navigateToData() {
        val intent = Intent(this.context, ProjectsListActivity::class.java)
        startActivity(intent)
    }


}