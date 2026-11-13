package com.example.redes_tp

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnIrSensores = findViewById<Button>(R.id.btnIrSensores)
        btnIrSensores.setOnClickListener {
            val intent = Intent(this, SensoresActivity::class.java)
            startActivity(intent)
        }

        // CAMBIAR TEXTO
        val tvMensaje = findViewById<TextView>(R.id.tvMensaje)
        val btnCambiarTexto = findViewById<Button>(R.id.btnCambiarTexto)

        btnCambiarTexto.setOnClickListener {
            tvMensaje.text = "¡Texto cambiado!"
        }

        // SUMAR DOS NÚMEROS
        val etNumero1 = findViewById<EditText>(R.id.etNumero1)
        val etNumero2 = findViewById<EditText>(R.id.etNumero2)
        val btnSumar = findViewById<Button>(R.id.btnSumar)
        val tvResultado = findViewById<TextView>(R.id.tvResultado)

        btnSumar.setOnClickListener {
            val texto1 = etNumero1.text.toString()
            val texto2 = etNumero2.text.toString()

            if (texto1.isNotEmpty() && texto2.isNotEmpty()) {
                val suma = texto1.toInt() + texto2.toInt()
                tvResultado.text = "Resultado: $suma"
            } else {
                Toast.makeText(this, "Completá ambos números", Toast.LENGTH_SHORT).show()
            }
        }

        // LOGIN CON INTENTOS Y BLOQUEO
        val etUsuario = findViewById<EditText>(R.id.etUsuario)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvEstadoLogin = findViewById<TextView>(R.id.tvEstadoLogin)

        val usuarioCorrecto = "admin"
        val passwordCorrecta = "1234"
        var intentosRestantes = 3

        btnLogin.setOnClickListener {
            val userIngresado = etUsuario.text.toString()
            val passIngresada = etPassword.text.toString()

            if (userIngresado == usuarioCorrecto && passIngresada == passwordCorrecta) {
                tvEstadoLogin.text = "¡Bienvenido, $userIngresado!"
                intentosRestantes = 3
            } else {
                intentosRestantes--

                if (intentosRestantes > 0) {
                    tvEstadoLogin.text = "Incorrecto. Intentos restantes: $intentosRestantes"
                    // Abrimos la pantalla con la imagen usando Intent
                    val intent = Intent(this, ErrorLoginActivity::class.java)
                    startActivity(intent)
                } else {
                    // Deshabilitamos inputs y botón al fallar 3 veces
                    etUsuario.isEnabled = false
                    etPassword.isEnabled = false
                    btnLogin.isEnabled = false

                    object : CountDownTimer(10000, 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            val segundos = millisUntilFinished / 1000
                            tvEstadoLogin.text = "Bloqueado. Esperá $segundos segundos..."
                        }

                        override fun onFinish() {
                            intentosRestantes = 3
                            etUsuario.isEnabled = true
                            etPassword.isEnabled = true
                            btnLogin.isEnabled = true
                            tvEstadoLogin.text = "Podés volver a intentar. Intentos: 3"
                        }
                    }.start()
                }
            }
        }
    }
}