package com.olaffson.jeuxenfants.games.memory

import kotlin.random.Random

data class MemoryCard(
    val id: Int,
    val symbol: String,
    val faceUp: Boolean = false,
    val matched: Boolean = false,
)

/**
 * État immuable d'une partie de Memory.
 *
 * Quand deux cartes différentes sont retournées, elles restent visibles
 * ([hasPendingMismatch]) jusqu'à l'appel de [hideMismatch], que l'écran
 * déclenche après un court délai pour laisser l'enfant les regarder.
 */
data class MemoryState(
    val cards: List<MemoryCard>,
    val firstIndex: Int? = null,
    val secondIndex: Int? = null,
    val moves: Int = 0,
) {
    val hasPendingMismatch: Boolean get() = secondIndex != null
    val isFinished: Boolean get() = cards.all { it.matched }
    val pairsFound: Int get() = cards.count { it.matched } / 2
    val totalPairs: Int get() = cards.size / 2

    fun flip(index: Int): MemoryState {
        if (hasPendingMismatch || index !in cards.indices) return this
        val card = cards[index]
        if (card.faceUp || card.matched) return this

        val revealed = cards.toMutableList().also { it[index] = card.copy(faceUp = true) }
        val first = firstIndex ?: return copy(cards = revealed, firstIndex = index)

        return if (cards[first].symbol == card.symbol) {
            revealed[first] = revealed[first].copy(matched = true)
            revealed[index] = revealed[index].copy(matched = true)
            copy(cards = revealed, firstIndex = null, moves = moves + 1)
        } else {
            copy(cards = revealed, secondIndex = index, moves = moves + 1)
        }
    }

    fun hideMismatch(): MemoryState {
        val first = firstIndex ?: return this
        val second = secondIndex ?: return this
        val hidden = cards.toMutableList()
        hidden[first] = hidden[first].copy(faceUp = false)
        hidden[second] = hidden[second].copy(faceUp = false)
        return copy(cards = hidden, firstIndex = null, secondIndex = null)
    }

    companion object {
        val SYMBOLS = listOf("🐶", "🐱", "🦊", "🐸", "🐵", "🐼", "🦁", "🐷", "🐰", "🐨")

        fun newGame(pairs: Int, random: Random = Random.Default): MemoryState {
            require(pairs in 2..SYMBOLS.size) { "Nombre de paires invalide : $pairs" }
            val symbols = SYMBOLS.shuffled(random).take(pairs)
            val cards = (symbols + symbols)
                .shuffled(random)
                .mapIndexed { i, s -> MemoryCard(id = i, symbol = s) }
            return MemoryState(cards)
        }
    }
}
