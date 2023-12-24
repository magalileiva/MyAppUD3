package com.utad.myappud3.fragment.inicial

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.utad.myappud3.data_store.DataStoreManager
import com.utad.myappud3.R
import com.utad.myappud3.databinding.FragmentRegisterBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



class RegisterFragment : Fragment() {

    private lateinit var _binding: FragmentRegisterBinding
    private val binding: FragmentRegisterBinding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bttNewAccount.setOnClickListener { saveDataAndNavigateToLogin() }
    }

    private fun saveDataAndNavigateToLogin() {
        val user = binding.tvNameAccount.text.toString().trim()
        val password = binding.tvPassAccount.text.toString().trim()
        val passwordRep = binding.tvPassAccountRep.text.toString().trim()
        if (!user.isNullOrEmpty() && !password.isNullOrEmpty() && password==passwordRep){
            lifecycleScope.launch(Dispatchers.IO) {
                DataStoreManager.saveUser(requireContext(), user, password)
            }
            val loginFragmnet = LoginFragment()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.setReorderingAllowed(true)
            transaction.replace(R.id.fcv_main, loginFragmnet)
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            transaction.commit()
        }else{
            Toast.makeText(requireContext(),R.string.passwordError, Toast.LENGTH_SHORT).show();
        }
    }

}