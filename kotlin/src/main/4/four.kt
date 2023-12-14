import java.io.File

fun main() {
    main3()
}

fun main1() {
    val inputFilePath = "full"
    val lines = File(inputFilePath).useLines { it.toList() }
    var res = 0
    var res2 = 0
    var cardsWon: Int
    var numberOfDuplicates = HashMap<Int, Int>()


    lines.forEachIndexed { index, line ->
        val cards = line.split(":")[1]
        val draws = cards.split("|")
        val WinningNumbers = draws[0].trim().replace("\\s+".toRegex(), ";").split(";")
        val secondDraw = draws[1].trim().replace("\\s+".toRegex(), ";").split(";")
        var drawResult = 0
        cardsWon = 0

        secondDraw.forEach { number ->
            if (WinningNumbers.contains(number)) {
                if ( drawResult == 0 ) {
                    drawResult = 1
                    cardsWon++
                } else {
                    drawResult = drawResult * 2
                    cardsWon++
                }
            }
        }

        if ( numberOfDuplicates.containsKey(index) ) {
            numberOfDuplicates[index] = numberOfDuplicates[index]!! + 1
        } else {
            numberOfDuplicates[index] = 1
        }

        val replay = numberOfDuplicates[index]!!

        for ( j in 0..replay-1 ){
            for ( i in 0..cardsWon-1 ) {
                val cardWon = index + i + 1
                res2++
                if ( !numberOfDuplicates.containsKey(cardWon) ) {
                    numberOfDuplicates[cardWon] = 1
                } else {
                    numberOfDuplicates[cardWon] = numberOfDuplicates[cardWon]!! + 1
                }
                
            }
        }
        res2++
        res += drawResult
    }

    println("First part result: $res")
    println("Second part result: $res2")

}

fun main2() {
    val inputFilePath = "small"
    val lines = File(inputFilePath).readLines()
    var res1 = 0

    for ( line in lines ) {
        val (winningNumbers, draw) = line.split(":")[1].split("|").map {
             it.trim().replace("\\s+".toRegex(), ";").split(";") }
        val drawResult = draw.fold(0) { acc, number ->
            if (winningNumbers.contains(number)) {
                if (acc == 0) 1 else acc * 2
            } else acc
        }
        res1 += drawResult
    }

    println("Second part result: $res1")
}


fun main3() {
    val inputFilePath = "full"
    val lines = File(inputFilePath).readLines()

    var totalScore = 0
    var totalWins = 0
    val numberOfDuplicates = mutableMapOf<Int, Int>()

    lines.forEachIndexed { index, line ->
        val (winningNumbers, secondDraw) = line.split(":")[1]
            .split("|")
            .map { it.trim().split("\\s+".toRegex()) }

        var drawScore = 0
        var cardsWon = 0

        secondDraw.forEach { number ->
            if (winningNumbers.contains(number)) {
                drawScore = if (drawScore == 0) 1 else drawScore * 2
                cardsWon++
            }
        }

        val replay = numberOfDuplicates.getOrDefault(index, 0)
        for (i in 0 until (replay + 1)) {
            for (j in 0 until cardsWon) {
                val cardWon = index + j + 1
                totalWins++
                numberOfDuplicates[cardWon] = numberOfDuplicates.getOrDefault(cardWon, 0) + 1
            }
        }

        totalWins++
        totalScore += drawScore
    }

    println("First part result: $totalScore")
    println("Second part result: $totalWins")
}
            