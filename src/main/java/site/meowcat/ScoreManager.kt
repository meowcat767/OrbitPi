package site.meowcat

class ScoreManager {
    private var score: Int = 0

    fun reset() {
        score = 0
    }

    fun add(points: Int) {
        score += points
    }

    fun penalty(points: Int) {
        score -= points
        if (score < 0) {
            score = 0
        }
    }

    fun getScore(): Int {
       return score
    }
}