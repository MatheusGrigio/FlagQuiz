package com.example.flagquiz

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets}

            val textoNomeJogador: TextView = findViewById(R.id.textoNomeJogador)
            val textoPontuacaoFinal: TextView = findViewById(R.id.textoPontuacaoFinal)
            val textoResumoResultados: TextView = findViewById(R.id.textoResumoResultados)
            val botaoJogarNovamente: Button = findViewById(R.id.botaoJogarNovamente)

            val nomeJogador = intent.getStringExtra("NOME_JOGADOR")
            val pontuacaoFinal = intent.getIntExtra("PONTUACAO_FINAL", 0)
            val listaDeResultados = intent.getStringArrayListExtra("LISTA_RESULTADOS")

            textoNomeJogador.text = nomeJogador
            textoPontuacaoFinal.text = pontuacaoFinal.toString()
            textoResumoResultados.text = listaDeResultados?.joinToString("\n") ?: ""

            botaoJogarNovamente.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
    }
}