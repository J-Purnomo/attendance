package com.playground.myattendance

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.playground.myattendance.databinding.ActivityLoginBinding
import com.playground.myattendance.model.ResponseLogin
import com.playground.myattendance.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private var binding: ActivityLoginBinding? = null
    private var nip : String = ""
    private var password : String = ""
    private lateinit var profil : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)

//        Checking session...
        profil = getSharedPreferences("login_session", MODE_PRIVATE)
        if (profil.getString("nip", null) != null) {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }

        binding!!.btnLogin.setOnClickListener {
            nip = binding!!.edNip.text.toString()
            password = binding!!.edPassword.text.toString()

            when {
                nip == "" -> {
                    binding!!.edNip.error = "NIP tidak boleh kosong!"
                }
                password == "" -> {
                    binding!!.edPassword.error = "Password tidak boleh kosong!"
                }
                else -> {
                    binding!!.progressBar.visibility   = View.VISIBLE
                    getData()
                }
            }
        }

    }

    private fun getData() {
        val api = RetrofitClient().getInstance()
        api.login(nip, password).enqueue(object  : Callback<ResponseLogin> {
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                if(response.isSuccessful) {
                    if (response.body()?.response == true) {

                        getSharedPreferences("login_session", MODE_PRIVATE)
                            .edit()
                            .putString("nip", response.body()?.payload?.nip)
                            .putString("nama", response.body()?.payload?.nama)
                            .apply()

                        binding!!.progressBar.visibility = View.GONE
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    } else {
                        binding!!.progressBar.visibility = View.GONE
                        Toast.makeText(
                            this@LoginActivity,
                            "Silahkan periksa kembali NIP & Password Anda!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Gagal terhubung ke server!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                Log.e("error message", "${t.message}" )
            }

        })
    }
}