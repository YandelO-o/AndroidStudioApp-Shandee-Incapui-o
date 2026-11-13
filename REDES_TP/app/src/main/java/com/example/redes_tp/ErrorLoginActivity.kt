package com.example.redes_tp

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ErrorLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_error_login)

        val ivVolver = findViewById<ImageView>(R.id.ivVolver)

        // Al hacer clic en la imagen, cerramos esta Activity para volver al Login
        ivVolver.setOnClickListener {
            finish()
        }
    }
}