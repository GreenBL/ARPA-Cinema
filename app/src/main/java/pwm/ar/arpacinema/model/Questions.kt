package pwm.ar.arpacinema.model

enum class Questions(val questionId : Int, val question : String) {
    PARENT_NAME(0, "Nome di un genitore"),
    PET_NAME(1, "Nome dell' animale domestico"),
    HERO_NAME(2, "Nome di un erore dell\'infanzia"),
    MOVIE_FAVORITE(3, "Film preferito"),
    SCHOOL_NAME(4, "Nome della scuola elementare"),
    FAVORITE_FOOD(5, "Cibo preferito");

    companion object {
        fun getQuestionById(id: Int): String? {
            return entries.find { it.questionId == id }?.question
        }
    }

}