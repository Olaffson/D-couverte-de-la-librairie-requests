package com.olaffson.jeuxenfants.games.morpion

import kotlin.random.Random

enum class Player(val symbol: String) {
    X("❌"), O("⭕");

    val other: Player get() = if (this == X) O else X
}

/** Plateau de morpion 3x3, cases numérotées de 0 à 8 ligne par ligne. */
data class MorpionState(
    val board: List<Player?> = List(9) { null },
    val current: Player = Player.X,
) {
    val winningLine: List<Int>? get() = LINES.firstOrNull { line ->
        val p = board[line[0]]
        p != null && line.all { board[it] == p }
    }
    val winner: Player? get() = winningLine?.let { board[it[0]] }
    val isDraw: Boolean get() = winner == null && board.all { it != null }
    val isOver: Boolean get() = winner != null || isDraw

    fun play(index: Int): MorpionState {
        if (isOver || index !in board.indices || board[index] != null) return this
        val next = board.toMutableList().also { it[index] = current }
        return MorpionState(next, current.other)
    }

    /**
     * Coup du robot. Avec [smartness] = 1.0 il gagne ou bloque dès que possible ;
     * plus la valeur est basse, plus il joue au hasard (pour laisser gagner les petits).
     */
    fun robotMove(random: Random = Random.Default, smartness: Double = 0.75): Int? {
        val free = board.indices.filter { board[it] == null }
        if (free.isEmpty() || isOver) return null
        if (random.nextDouble() < smartness) {
            findWinningMove(current)?.let { return it }
            findWinningMove(current.other)?.let { return it }
            if (board[4] == null) return 4
            val corners = listOf(0, 2, 6, 8).filter { board[it] == null }
            if (corners.isNotEmpty()) return corners.random(random)
        }
        return free.random(random)
    }

    private fun findWinningMove(player: Player): Int? = board.indices.firstOrNull { i ->
        board[i] == null &&
            copy(board = board.toMutableList().also { it[i] = player }).winner == player
    }

    companion object {
        val LINES = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8),
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8),
            listOf(0, 4, 8), listOf(2, 4, 6),
        )
    }
}
