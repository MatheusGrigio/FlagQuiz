package com.example.flagquiz

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class QuizActivity : AppCompatActivity() {

    private lateinit var todasAsBandeiras: List<Bandeira>
    private lateinit var bandeirasDoQuiz: List<Bandeira>
    private var indicePerguntaAtual = 0
    private var pontuacao = 0
    private lateinit var nomeJogador: String
    private val listaDeResultados = ArrayList<String>()

    // Variáveis de componentes visuais em português
    private lateinit var textoContadorPerguntas: TextView
    private lateinit var imagemBandeira: ImageView
    private lateinit var campoResposta: EditText
    private lateinit var textoFeedback: TextView
    private lateinit var botaoEnviar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
            nomeJogador = intent.getStringExtra("NOME_JOGADOR") ?: "Jogador"

            textoContadorPerguntas = findViewById(R.id.textoContadorPerguntas)
            imagemBandeira = findViewById(R.id.imagemBandeira)
            campoResposta = findViewById(R.id.campoResposta)
            textoFeedback = findViewById(R.id.textoFeedback)
            botaoEnviar = findViewById(R.id.botaoEnviar)

            carregarBandeiras()
            iniciarNovoQuiz()

        botaoEnviar.setOnClickListener {
            gerenciarEnvioResposta()
        }
    }


        fun carregarBandeiras() {
            todasAsBandeiras = listOf(
                Bandeira(R.drawable.flag_albania, "Albania"),
                Bandeira(R.drawable.flag_brasil, "Brasil"),
                Bandeira(R.drawable.flag_australia, "Australia"),
                Bandeira(R.drawable.flag_bolivia, "Bolivia"),
                Bandeira(R.drawable.flag_canada, "Canada"),
                Bandeira(R.drawable.flag_china, "China"),
                Bandeira(R.drawable.flag_dominica, "Dominica"),
                Bandeira(R.drawable.flag_portugal, "Portugal"),
                Bandeira(R.drawable.flag_ira, "Ira"),
                Bandeira(R.drawable.flag_suica, "Suiça")
            )
        }

        fun iniciarNovoQuiz() {
            pontuacao = 0
            indicePerguntaAtual = 0
            listaDeResultados.clear()
            bandeirasDoQuiz = todasAsBandeiras.shuffled().take(5)
            exibirProximaPergunta()
        }

        fun exibirProximaPergunta() {
            if (indicePerguntaAtual < bandeirasDoQuiz.size) {
                val bandeiraAtual = bandeirasDoQuiz[indicePerguntaAtual]
                textoContadorPerguntas.text = "${indicePerguntaAtual + 1} de 5"
                imagemBandeira.setImageResource(bandeiraAtual.idImg)
                campoResposta.text.clear()
                textoFeedback.visibility = View.INVISIBLE
                campoResposta.isEnabled = true
                botaoEnviar.text = "Enviar"
            } else {
                val intent = Intent(this, ResultActivity::class.java)
                // Enviando os dados com chaves em português
                intent.putExtra("NOME_JOGADOR", nomeJogador)
                intent.putExtra("PONTUACAO_FINAL", pontuacao)
                intent.putStringArrayListExtra("LISTA_RESULTADOS", listaDeResultados)
                startActivity(intent)
                finish()
            }
        }

        fun gerenciarEnvioResposta() {
            if (botaoEnviar.text == "Enviar") {
                verificarResposta()
            } else {
                indicePerguntaAtual++
                exibirProximaPergunta()
            }
        }

        fun verificarResposta() {
            val respostaUsuario = campoResposta.text.toString().trim()
            val respostaCorreta = bandeirasDoQuiz[indicePerguntaAtual].nomeDoPais

            textoFeedback.visibility = View.VISIBLE
            campoResposta.isEnabled = false

            if (respostaUsuario.equals(respostaCorreta, ignoreCase = true)) {
                pontuacao += 20
                textoFeedback.text = "Correto!"
                textoFeedback.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_dark))
                listaDeResultados.add("${indicePerguntaAtual + 1}. ${respostaCorreta} - Correto")
            } else {
                textoFeedback.text = "Incorreto! A resposta era: ${respostaCorreta}"
                textoFeedback.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark))
                listaDeResultados.add("${indicePerguntaAtual + 1}. ${respostaCorreta} - Incorreto")
            }

            if (indicePerguntaAtual < bandeirasDoQuiz.size - 1) {
                botaoEnviar.text = "Próxima Pergunta"
            } else {
                botaoEnviar.text = "Ver Resultados"
            }
        }
    }



