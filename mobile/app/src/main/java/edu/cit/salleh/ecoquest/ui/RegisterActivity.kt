package edu.cit.salleh.ecoquest.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import edu.cit.salleh.ecoquest.R
import edu.cit.salleh.ecoquest.api.RetrofitClient
import edu.cit.salleh.ecoquest.model.RegisterRequest
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val tvLogin = findViewById<TextView>(R.id.tvLogin)
        val tvError = findViewById<TextView>(R.id.tvError)

        btnRegister.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                tvError.setTextColor(
                    resources.getColor(android.R.color.holo_red_dark, null))
                tvError.text = "All fields are required"
                return@setOnClickListener
            }

            if (password.length < 6) {
                tvError.setTextColor(
                    resources.getColor(android.R.color.holo_red_dark, null))
                tvError.text = "Password must be at least 6 characters"
                return@setOnClickListener
            }

            tvError.text = ""
            btnRegister.isEnabled = false
            btnRegister.text = "Creating account..."

            lifecycleScope.launch {
                try {
                    val response = RetrofitClient.instance.register(
                        RegisterRequest(name, email, password)
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
                        tvError.text = "✅ Registration successful! Redirecting..."
                        Handler(Looper.getMainLooper()).postDelayed({
                            startActivity(Intent(this@RegisterActivity,
                                LoginActivity::class.java))
                            finish()
                        }, 1500)
                    } else if (response.code() == 409) {
                        tvError.setTextColor(
                            resources.getColor(android.R.color.holo_red_dark, null))
                        tvError.text = "Email already in use"
                        btnRegister.isEnabled = true
                        btnRegister.text = "Create Account"
                    } else {
                        tvError.setTextColor(
                            resources.getColor(android.R.color.holo_red_dark, null))
                        tvError.text = "Registration failed. Try again."
                        btnRegister.isEnabled = true
                        btnRegister.text = "Create Account"
                    }
                } catch (e: Exception) {
                    tvError.setTextColor(
                        resources.getColor(android.R.color.holo_red_dark, null))
                    tvError.text = "Cannot connect to server"
                    btnRegister.isEnabled = true
                    btnRegister.text = "Create Account"
                }
            }
        }

        tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}