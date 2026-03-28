package edu.cit.salleh.ecoquest.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import edu.cit.salleh.ecoquest.R

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val prefs = getSharedPreferences("ecoquest", MODE_PRIVATE)
        val name = prefs.getString("name", "User")
        val points = prefs.getInt("points", 0)

        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        val tvPoints = findViewById<TextView>(R.id.tvPoints)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        tvWelcome.text = "Welcome, $name! 🌿"
        tvPoints.text = "Eco Points: $points"

        btnLogout.setOnClickListener {
            prefs.edit().clear().apply()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}