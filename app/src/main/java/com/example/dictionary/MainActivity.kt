package com.example.dictionary

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.*
import androidx.core.view.isVisible
import com.example.dictionary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding of Activity must not to be null")

    override fun onCreate(savedInstanceState: Bundle?) {
        _binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val trainer = LearnWordsTrainer()
        showNextQuestion(trainer)
        with(binding){
            buttonContinue.setOnClickListener {
                markNeutralActivity(layoutAnswer1, tvVariantNumber1, tvVariantValue1)
                markNeutralActivity(layoutAnswer2, tvVariantNumber2, tvVariantValue2)
                markNeutralActivity(layoutAnswer3, tvVariantNumber3, tvVariantValue3)
                markNeutralActivity(layoutAnswer4, tvVariantNumber4, tvVariantValue4)
                layoutResult.isVisible=false
                showNextQuestion(trainer)

            }
            skipButton.setOnClickListener {
                showNextQuestion(trainer)
            }
        }

    }

    private fun showNextQuestion(trainer: LearnWordsTrainer) {
        val firsQuestion: Question? = trainer.getNextQuestion()
        with(binding) {
            if (firsQuestion == null || firsQuestion.variants.size < NUMBERS_OF_ANSWERS) {
                tvQuestionWord.isVisible = false
                layoutVariants.isVisible = false
                skipButton.text = "Complete!"
            } else {
                skipButton.isVisible = true
                tvQuestionWord.isVisible = true
                tvQuestionWord.text = firsQuestion.correctAnswer.original
                tvVariantValue1.text = firsQuestion.variants[0].translate
                tvVariantValue2.text = firsQuestion.variants[1].translate
                tvVariantValue3.text = firsQuestion.variants[2].translate
                tvVariantValue4.text = firsQuestion.variants[3].translate

                layoutAnswer1.setOnClickListener {
                    if (trainer.checkAnswer(0)) {
                        markAnswerCorrect(layoutAnswer1, tvVariantNumber1, tvVariantValue1)
                        showResultMessage(true)
                    } else {
                        markAnswerWrong(layoutAnswer1, tvVariantNumber1, tvVariantValue1)
                        showResultMessage(false)
                    }
                }
                layoutAnswer2.setOnClickListener {
                    if (trainer.checkAnswer(1)) {
                        markAnswerCorrect(layoutAnswer2, tvVariantNumber2, tvVariantValue2)
                        showResultMessage(true)
                    } else {
                        markAnswerWrong(layoutAnswer2, tvVariantNumber2, tvVariantValue2)
                        showResultMessage(false)

                    }
                    layoutAnswer3.setOnClickListener {
                        if (trainer.checkAnswer(2)) {
                            markAnswerCorrect(layoutAnswer3, tvVariantNumber3, tvVariantValue3)
                            showResultMessage(true)
                        } else {
                            markAnswerWrong(layoutAnswer3, tvVariantNumber3, tvVariantValue3)
                            showResultMessage(false)
                        }
                    }
                    layoutAnswer4.setOnClickListener {
                        if (trainer.checkAnswer(3)) {
                            markAnswerCorrect(layoutAnswer4, tvVariantNumber4, tvVariantValue4)
                            showResultMessage(true)
                        } else {
                            markAnswerWrong(layoutAnswer4, tvVariantNumber4, tvVariantValue4)
                            showResultMessage(false)
                        }
                    }
                }
            }
        }
    }


    private fun markNeutralActivity(
        layoutAnswer: LinearLayout,
        tvVariantNumber: TextView,
        tvVariantValue: TextView
    ) {
        with(binding) {

            layoutAnswer.background =
                ContextCompat.getDrawable(this@MainActivity, R.drawable.shape_rounded_container)
        }
        tvVariantNumber.apply {
            background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.shape_rounded_variants
            )
            setTextColor(getColor(this@MainActivity, R.color.TextVariantsColor))
        }
        tvVariantValue.setTextColor(getColor(R.color.TextVariantsColor))
    }


    private fun markAnswerWrong(
        layoutAnswer: LinearLayout,
        tvVariantNumber: TextView,
        tvVariantValue: TextView
    ) {

        layoutAnswer.background = getDrawable(
            this,
            R.drawable.shape_drawable_container_incorrect
        )
        tvVariantNumber.background =
            getDrawable(this, R.drawable.shape_drawable_variants_incorrect)
        tvVariantNumber.setTextColor(getColor(this, R.color.white))
        tvVariantValue.setTextColor(getColor(this, R.color.IncorrectAnswer))
        showResultMessage(false)
    }


    private fun markAnswerCorrect(
        layoutAnswer: LinearLayout,
        tvVariantNumber: TextView,
        tvVariantValue: TextView
    ) {
        layoutAnswer.background = getDrawable(
            this,
            R.drawable.shape_drawable_container_correct
        )
        tvVariantNumber.background =
            getDrawable(this, R.drawable.shape_drawable_variants_correct)
        tvVariantValue.setTextColor(getColor(this, R.color.CorrectAnswer))
        tvVariantNumber.setTextColor(getColor(this, R.color.white))
        showResultMessage(true)

    }

    private fun showResultMessage(isCorrect: Boolean) {
        var color: Int
        var resultIconResource: Int
        if (isCorrect) {
            color = ContextCompat.getColor(this, R.color.CorrectAnswer)
            resultIconResource = R.drawable.group_410
        } else {
            color = ContextCompat.getColor(this, R.color.IncorrectAnswer)
            resultIconResource = R.drawable.incorrect
        }
        with(binding) {
            skipButton.isVisible = false
            layoutResult.isVisible = true
            buttonContinue.setTextColor(color)
            layoutResult.setBackgroundColor(color)
            ivResultIcon.setImageResource(resultIconResource)
        }
    }
}



