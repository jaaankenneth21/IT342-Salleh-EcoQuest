package edu.cit.salleh.ecoquest.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import edu.cit.salleh.ecoquest.R
import edu.cit.salleh.ecoquest.api.RetrofitClient
import edu.cit.salleh.ecoquest.model.LoginRequest
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvRegister = findViewById<TextView>(R.id.tvRegister)
        val tvError = findViewById<TextView>(R.id.tvError)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                tvError.setTextColor(resources.getColor(android.R.color.holo_red_dark, null))
                tvError.text = "All fields are required"
                return@setOnClickListener
            }

            tvError.text = ""
            btnLogin.isEnabled = false
            btnLogin.text = "Logging in..."

            lifecycleScope.launch {
                try {
                    val response = RetrofitClient.instance.login(
                        LoginRequest(email, password)
                    )
                    if (response.isSuccessful) {
                        val auth = response.body()
                        val prefs = getSharedPreferences("ecoquest", MODE_PRIVATE)
                        prefs.edit()
                            .putString("token", auth?.token)
                            .putString("name", auth?.name)
                            .putInt("points", auth?.points ?: 0)
                            .apply()
                        tvError.setTextColor(
                            resources.getColor(android.R.color.holo_green_dark, null))
                        tvError.text = "✅ Login successful! Redirecting..."
                        Handler(Looper.getMainLooper()).postDelayed({
                            startActivity(Intent(this@LoginActivity,
                                DashboardActivity::class.java))
                            finish()
                        }, 1500)
                    } else {
                        tvError.setTextColor(
                            resources.getColor(android.R.color.holo_red_dark, null))
                        tvError.text = "Invalid email or password"
                        btnLogin.isEnabled = true
                        btnLogin.text = "Sign In"
                    }
                } catch (e: Exception) {
                    tvError.setTextColor(
                        resources.getColor(android.R.color.holo_red_dark, null))
                    tvError.text = "Cannot connect to server"
                    btnLogin.isEnabled = true
                    btnLogin.text = "Sign In"
                }
            }
        }

        tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }
}