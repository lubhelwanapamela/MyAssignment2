package com.example.lifehackapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class QuizActivity : AppCompatActivity() {
    private var index = 0
    private var score = 0
    private lateinit var questionText: TextView
    private lateinit var feedbackText: TextView

    private val questions = arrayOf(
        "you should wait an hour after eating before swimming",
        "Cracking your knuckles causes arthris",
        "using night mode on your phone helps you sleepp better",
                "sugar makes children hyperactive",
        "you only use 10% of your brain"
    )

    private val answers = arrayOf(false, true, false, true, false)
    private val explanations = arrayOf(
        "Myth: No scientific evidance support this; cramping risk is minimal.",
        "Myth: knuckle cracking doesnt couse arthritis, only possible ligament streching.",
        "Hack: Reduces blue light ,improving melatonin produvtion and sleep quality.",
        "Myth: studies show no direct link between sugar and hyperactive behavior.",
        "Myth: you use virtually all parts of your brain over a day."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        questionText = findViewById(R.id.questionText)
        feedbackText = findViewById(R.id.feedbackText)

        val hackButton = findViewById<Button>(R.id.hackButton)
        val mythButton = findViewById<Button>(R.id.mythButton)
        val nextButton = findViewById<Button>(R.id.nextButton)

        loadQuestion()

        hackButton.setOnClickListener { checkAnswer(true) }
        mythButton.setOnClickListener { checkAnswer(false) }

        nextButton.setOnClickListener {
            index++

            if (index < questions.size) {
                loadQuestion()
                feedbackText.text = ""
            } else {
                // Navigate to ScoreActivity when quiz is finished
                val intent = Intent(this, ScoreActivity::class.java)
                intent.putExtra("score", score)
                intent.putExtra("total", questions.size)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun loadQuestion() {
        questionText.text = questions[index]
    }

    private fun checkAnswer(userAnswer: Boolean) {
        if (userAnswer == answers[index]) {
            feedbackText.text = "Correct! 🎉\n${explanations[index]}"
            score++
        } else {
            feedbackText.text = "Wrong! ❌\n${explanations[index]}"
        }
    }
}
