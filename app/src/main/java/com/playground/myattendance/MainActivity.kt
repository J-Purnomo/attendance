package com.playground.myattendance

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.playground.myattendance.databinding.ActivityMainBinding
import com.playground.myattendance.model.ResponseCheckOut
import com.playground.myattendance.model.ResponseCheckin
import com.playground.myattendance.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private lateinit var profil: SharedPreferences
    private var nip: String = ""
    private val api = RetrofitClient().getInstance()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        profil = getSharedPreferences("login_session", MODE_PRIVATE)

        nip = profil.getString("nip", null).toString()

        binding?.tvGreeting?.text = "Hi.. ${profil.getString("nama", null)}!"

        binding?.btnCheckin?.setOnClickListener {
            checkIn()
        }

        binding?.btnCheckout?.setOnClickListener {
            checkOut()
        }

        binding?.btnLogout?.setOnClickListener {
            profil.edit().clear().apply()

            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }
    }

    private fun checkIn() {
        api.checkin(nip).enqueue(object : Callback<ResponseCheckin> {
            override fun onResponse(
                call: Call<ResponseCheckin>,
                response: Response<ResponseCheckin>
            ) {
                if (response.body()?.response == true) {
                    val message = response.body()?.message.toString()
                    Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "Silahkan coba kembali!", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<ResponseCheckin>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun checkOut() {
        api.checkout(nip).enqueue(object : Callback<ResponseCheckOut> {
            override fun onResponse(
                call: Call<ResponseCheckOut>,
                response: Response<ResponseCheckOut>
            ) {
                if (response.body()?.response == true) {
                    val message = response.body()?.message.toString()
                    Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "Silahkan coba kembali!", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<ResponseCheckOut>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }
}