import java.io.File

data class Gear (val row : Int, val col : Int)


fun main() {

    val inputFilePath = "./aoc/src/main/3/small"
    val activationPoint = mutableMapOf<Int, MutableList<Int>>()

    val gears = mutableMapOf<Gear, MutableList<Int>>() // Map of gear to list of numbers

    val lines = File(inputFilePath).useLines { it.toList() } // Read file lines once
    val height = lines.size
    val width = lines.first().length

    val charArray = Array(height) { CharArray(width) { '*' } }

    lines.forEachIndexed { rowIndex, line ->
        line.forEachIndexed { colIndex, char ->
            if (char.isDigit()) {
                charArray[rowIndex][colIndex] = char
            } else if (char != '.') {
                activationPoint.addToMultimap(rowIndex, colIndex)
            }
            if ( char == '*' ) {
                gears[Gear(rowIndex, colIndex)] = mutableListOf()
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
                    }
                    numberBuilder.clear()
                    isValid = false
                }
            }
        }
        if (numberBuilder.isNotEmpty()) {
            if ( isValid ) {
                res += numberBuilder.toString().toInt()
            }
        }

    }
    println("Result is $res")

    gears.forEach { (gear, _) ->
        gears[gear] = searchGearNeighbours(gear, charArray).toMutableList()
    }

    gears.filterValues { it.size == 2 }
        .map { (_, value) -> value.reduce { acc, i -> acc * i } }
        .reduce { acc, i -> acc + i }
        .let { println("Result for part 2 is $it") }
}

fun <K, V> MutableMap<K, MutableList<V>>.addToMultimap(key: K, value: V) {
    this.computeIfAbsent(key) { mutableListOf() }.add(value)
}

fun searchNeighbourSignal(h: Int, w: Int, multiMap: Map<Int, MutableList<Int>>) : Boolean {
    return multiMap.filterKeys {
        it in h-1..h+1
    }.any { (_, valueList) ->
        valueList.any { it in w-1.. w+1 }
    }
}

fun searchGearNeighbours(gear: Gear, charArray: Array<CharArray>) : List<Int> {
    val row = gear.row
    val neighbours = mutableListOf<Int>()
    var str = StringBuilder()
    var isNear = false

    for (i in row-1..row+1) {
        for (j in 0..charArray[i].size-1) {
            if (charArray[i][j].isDigit()) {
                str.append(charArray[i][j])
                isNear = isNear || isNearTheGear(i, j, gear)
            } else {
                if (str.isNotEmpty() && isNear) {
                    neighbours.add(str.toString().toInt())
                    isNear = false
                }
                str.clear()
            }
        }
        str.clear()
    }
    return neighbours
}
fun isNearTheGear(row: Int, index: Int, gear: Gear): Boolean {
    val (gearRow, gearCol) = gear
    return (row in gearRow-1..gearRow+1 && index in gearCol-1..gearCol+1)
}
