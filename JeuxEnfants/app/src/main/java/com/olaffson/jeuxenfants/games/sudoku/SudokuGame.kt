package com.olaffson.jeuxenfants.games.sudoku

import kotlin.random.Random

/** Taille de grille adaptée aux enfants : 4x4 (blocs 2x2) ou 6x6 (blocs 2x3). */
enum class SudokuSize(val label: String, val boxRows: Int, val boxCols: Int, val holes: Int) {
    PETIT("4 × 4", 2, 2, 8),
    GRAND("6 × 6", 2, 3, 18);

    val n: Int get() = boxRows * boxCols
}

data class SudokuState(
    val size: SudokuSize,
    val solution: List<Int>,
    val given: List<Boolean>,
    val values: List<Int>, // 0 = case vide
    val selected: Int? = null,
) {
    val n: Int get() = size.n
    val isSolved: Boolean get() = values == solution

    fun isWrong(index: Int): Boolean = values[index] != 0 && values[index] != solution[index]

    fun select(index: Int): SudokuState =
        if (index in values.indices && !given[index]) copy(selected = index) else this

    fun enter(value: Int): SudokuState {
        val i = selected ?: return this
        if (given[i] || value !in 0..n) return this
        return copy(values = values.toMutableList().also { it[i] = value })
    }

    companion object {
        fun newGame(size: SudokuSize, random: Random = Random.Default): SudokuState {
            val solution = SudokuGenerator.solvedGrid(size, random)
            val puzzle = SudokuGenerator.makePuzzle(solution, size, random)
            return SudokuState(
                size = size,
                solution = solution,
                given = puzzle.map { it != 0 },
                values = puzzle,
            )
        }
    }
}

object SudokuGenerator {

    /** Grille complète valide, obtenue en mélangeant un motif de base. */
    fun solvedGrid(size: SudokuSize, random: Random): List<Int> {
        val n = size.n
        val br = size.boxRows
        val bc = size.boxCols
        // Bandes = groupes de `br` lignes ; piles = groupes de `bc` colonnes.
        val rows = (0 until n / br).shuffled(random)
            .flatMap { band -> (0 until br).shuffled(random).map { band * br + it } }
        val cols = (0 until n / bc).shuffled(random)
            .flatMap { stack -> (0 until bc).shuffled(random).map { stack * bc + it } }
        val digits = (1..n).shuffled(random)
        return List(n * n) { i ->
            val r = rows[i / n]
            val c = cols[i % n]
            digits[(bc * (r % br) + r / br + c) % n]
        }
    }

    /** Retire des chiffres tant que la solution reste unique. */
    fun makePuzzle(solution: List<Int>, size: SudokuSize, random: Random): List<Int> {
        val grid = solution.toMutableList()
        var removed = 0
        for (i in grid.indices.shuffled(random)) {
            if (removed == size.holes) break
            val backup = grid[i]
            grid[i] = 0
            if (countSolutions(grid, size, limit = 2) == 1) removed++ else grid[i] = backup
        }
        return grid
    }

    fun isValidPlacement(grid: List<Int>, size: SudokuSize, index: Int, value: Int): Boolean {
        val n = size.n
        val r = index / n
        val c = index % n
        for (k in 0 until n) {
            if (k != c && grid[r * n + k] == value) return false
            if (k != r && grid[k * n + c] == value) return false
        }
        val r0 = r / size.boxRows * size.boxRows
        val c0 = c / size.boxCols * size.boxCols
        for (rr in r0 until r0 + size.boxRows) for (cc in c0 until c0 + size.boxCols) {
            val j = rr * n + cc
            if (j != index && grid[j] == value) return false
        }
        return true
    }

    fun countSolutions(grid: List<Int>, size: SudokuSize, limit: Int): Int {
        val work = grid.toMutableList()
        fun solve(): Int {
            val empty = work.indexOf(0)
            if (empty == -1) return 1
            var count = 0
            for (v in 1..size.n) {
                if (isValidPlacement(work, size, empty, v)) {
                    work[empty] = v
                    count += solve()
                    work[empty] = 0
                    if (count >= limit) break
                }
            }
            return count
        }
        return solve()
    }
}
