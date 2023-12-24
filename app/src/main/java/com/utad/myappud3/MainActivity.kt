package com.utad.myappud3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.utad.myappud3.databinding.ActivityMainBinding
import com.utad.myappud3.fragment.inicial.LoginFragment

class MainActivity : AppCompatActivity() {

    private lateinit var _binding:  ActivityMainBinding
    private val binding: ActivityMainBinding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myInitialFragment = LoginFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setReorderingAllowed(true)
        transaction.add(binding.fcvMain.id, myInitialFragment)
        transaction.commit()
    }
}