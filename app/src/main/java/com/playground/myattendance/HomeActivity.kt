package com.playground.myattendance

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.playground.myattendance.databinding.ActivityHomeBinding
import com.playground.myattendance.network.RetrofitClient

class HomeActivity : AppCompatActivity() {

    private var binding: ActivityHomeBinding? = null
    private lateinit var profil: SharedPreferences
    private var nip: String = ""
    private val api = RetrofitClient().getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        profil = getSharedPreferences("login_session", MODE_PRIVATE)

        nip = profil.getString("nip", null).toString()

    }
}