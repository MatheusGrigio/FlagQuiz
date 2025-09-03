package com.example.flagquiz

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val etNome: EditText = findViewById(R.id.etNome)
        val btnComeca: Button = findViewById(R.id.btnComeca)
        btnComeca.setOnClickListener {
            val nome = etNome.text.toString()
            if (nome.isNotBlank()) {
                val intent = Intent(this, QuizActivity::class.java)
                intent.putExtra("PLAYER_NAME", nome)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Por favor, insira seu nome.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}