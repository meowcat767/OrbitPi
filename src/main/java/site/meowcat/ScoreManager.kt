package site.meowcat

object ScoreManager {

    @JvmStatic
    fun getScore(): Int = score

    @JvmStatic
    fun add(points: Int) { score += points }

    @JvmStatic
    fun penalty(points: Int) {
        score -= points
        if (score < 0) score = 0
    }

    @JvmStatic
    fun reset() { score = 0 }

    private var score: Int = 0
}
