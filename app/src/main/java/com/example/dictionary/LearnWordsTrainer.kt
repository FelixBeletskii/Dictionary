package com.example.dictionary
const val NUMBERS_OF_ANSWERS: Int = 4

data class Word(val original: String,
                val translate: String, var learned: Boolean = false)
data class Question(val variants: List<Word>, val correctAnswer: Word)
class LearnWordsTrainer {
    private val dictionary = listOf(
        Word("bird", "птица"),
        Word("Photo", "Фотография"),
        Word("Meal", "Еда"),
        Word("Flower", "Цветок"),
        Word("Wallpapper", "Обои"),
        Word("TV", "Телевизор"),
        Word("Sofa", "Диван"),
        Word("Fork", "Вилка"),
        Word("Sea", "Море"),
        Word("Table", "Стол"),
        Word("Sofa", "Диван"),
        Word("Pensil", "Ручка"),
        Word("Pen", "Карандаш"),
        Word("Chair", "Кресло"),
        Word("Knife", "Нож"),
        Word("Notebook", "Блокнот"),
        Word("Copybook", "Тетрадь"),
        Word("Pillow", "Подушка")
    )
    private var currentQuestion: Question? = null
    fun getNextQuestion(): Question? {
        val notLearnedList = dictionary.filter { !it.learned }
        if (notLearnedList.isEmpty()) return null

        val questionWords =
            if (notLearnedList.size < NUMBERS_OF_ANSWERS) {
                val learnedList = dictionary.filter { it.learned }.shuffled()
                notLearnedList.shuffled()
                    .take(NUMBERS_OF_ANSWERS) + learnedList
                    .take(NUMBERS_OF_ANSWERS - notLearnedList.size)
            } else {
                notLearnedList.shuffled().take(NUMBERS_OF_ANSWERS)
            }.shuffled()
        val correctAnswer = questionWords.random()
        currentQuestion = Question(variants = questionWords, correctAnswer = correctAnswer)
        return currentQuestion
    }

        fun checkAnswer(userAnswerIndex: Int?): Boolean {
            return currentQuestion?.let {
                val correctAnswerId = it.variants.indexOf(it.correctAnswer)
               if(correctAnswerId==userAnswerIndex){
                    it.correctAnswer.learned=true
                   true
                } else{
                        false}
                }?: false
            }
        }





