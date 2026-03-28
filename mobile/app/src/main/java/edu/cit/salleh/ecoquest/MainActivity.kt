package edu.cit.salleh.ecoquest

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import edu.cit.salleh.ecoquest.ui.DashboardActivity
import edu.cit.salleh.ecoquest.ui.LoginActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("ecoquest", MODE_PRIVATE)
        val token = prefs.getString("token", null)

        if (token != null) {
            startActivity(Intent(this, DashboardActivity::class.java))
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        finish()
    }
}