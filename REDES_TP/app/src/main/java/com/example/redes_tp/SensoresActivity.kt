package com.example.redes_tp

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

// Implementamos SensorEventListener para que esta pantalla pueda escuchar los cambios de los sensores
class SensoresActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var acelerometro: Sensor? = null
    private var sensorLuz: Sensor? = null
    private var sensorProximidad: Sensor? = null

    private lateinit var tvAcelerometro: TextView
    private lateinit var tvLuz: TextView
    private lateinit var tvProximidad: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensores)

        tvAcelerometro = findViewById(R.id.tvAcelerometro)
        tvLuz = findViewById(R.id.tvLuz)
        tvProximidad = findViewById(R.id.tvProximidad)
        val btnVolver = findViewById<Button>(R.id.btnVolverSensores)

        btnVolver.setOnClickListener {
            finish()
        }

        // 1. Obtenemos el servicio de sensores del sistema Android
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // 2. Pedimos la referencia a cada sensor físico (pueden ser null si el celular/emulador no lo tiene)
        acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorLuz = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        sensorProximidad = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        // Avisamos en pantalla si algún sensor no está disponible en el dispositivo
        if (acelerometro == null) tvAcelerometro.text = "Acelerómetro: No disponible"
        if (sensorLuz == null) tvLuz.text = "Sensor de Luz: No disponible"
        if (sensorProximidad == null) tvProximidad.text = "Proximidad: No disponible"
    }

    // Cuando la pantalla pasa a primer plano, activamos la escucha de los sensores
    override fun onResume() {
        super.onResume()
        acelerometro?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
        sensorLuz?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
        sensorProximidad?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    // Cuando salimos de la pantalla o minimizamos, apagamos la escucha para ahorrar batería
    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    // Este método se ejecuta automáticamente cada vez que cambia el valor de algún sensor registrado
    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return

        when (event.sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> {
                // El acelerómetro devuelve 3 ejes: X, Y, Z en m/s²
                val x = String.format("%.2f", event.values[0])
                val y = String.format("%.2f", event.values[1])
                val z = String.format("%.2f", event.values[2])
                tvAcelerometro.text = "Acelerómetro:\nX: $x | Y: $y | Z: $z"
            }
            Sensor.TYPE_LIGHT -> {
                // El sensor de luz devuelve 1 valor en lux
                val lux = event.values[0]
                tvLuz.text = "Sensor de Luz: $lux lx"
            }
            Sensor.TYPE_PROXIMITY -> {
                // El sensor de proximidad devuelve la distancia en centímetros
                val distancia = event.values[0]
                tvProximidad.text = "Proximidad: $distancia cm"
            }
        }
    }

    // Método obligatorio de la interfaz SensorEventListener (se ejecuta si cambia la precisión del sensor)
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // No necesitamos hacer nada acá para este ejercicio
    }
}