import java.io.File

fun main() {
    val inputFilePath = "/Users/saalik/dev/aoc/aoc/src/main/3/full"
    val activationPoint = mutableMapOf<Int, MutableList<Int>>()
    val gears = mutableMapOf<Int, MutableList<Int>>()

    val lines = File(inputFilePath).useLines { it.toList() } // Read file lines once
    val height = lines.size
    val width = lines.first.length

    val charArray = Array(height) { CharArray(width) { '*' } }

    lines.forEachIndexed { rowIndex, line ->
        line.forEachIndexed { colIndex, char ->
            if (char.isDigit()) {
                charArray[rowIndex][colIndex] = char
            } else if (char != '.') {
                activationPoint.addToMultimap(rowIndex, colIndex)
            }
            if ( char == '*' ) {
                gears.addToMultimap(rowIndex, colIndex)
            }
        }
    }

    var res = 0
    val numberBuilder = StringBuilder()

    charArray.forEachIndexed { line, chars ->
        numberBuilder.clear()
        var isValid = false

        chars.forEachIndexed { col, char ->
            if (char.isDigit()) {
                numberBuilder.append(char)
                isValid = isValid || searchNeighbourSignal(line, col, activationPoint)
            } else {
                if (numberBuilder.isNotEmpty()) {
                    if (isValid) {
                        res += numberBuilder.toString().toInt()
                    } else {
                        println("${numberBuilder} is not valid on line $line")
                    }
                    numberBuilder.clear()
                    isValid = false
                }
            }
        }
        if (numberBuilder.isNotEmpty() && isValid) {
            res += numberBuilder.toString().toInt()
        }
    }
    println("Result is $res")
}

fun <K, V> MutableMap<K, MutableList<V>>.addToMultimap(key: K, value: V) {
    this.computeIfAbsent(key) { mutableListOf() }.add(value)
}

fun searchNeighbourSignal(h: Int, w: Int, activationPoint: Map<Int, MutableList<Int>>) : Boolean {
    return activationPoint.filterKeys {
        it in h-1..h+1
    }.any { (_, valueList) ->
        valueList.any { it in w-1.. w+1 }
    }
}