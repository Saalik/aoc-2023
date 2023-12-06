import java.io.File

fun main() {
    val inputFilePath = "./src/main/3/small"
    val activationPoint = mutableMapOf<Int, MutableList<Int>>()
    val gearMap = mutableMapOf<Int, MutableList<Int>>()
    val gears = mutableMapOf<Gear, MutableList<Int>>()

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
                gearMap.addToMultimap(rowIndex, colIndex)
            }
        }
    }

    var res = 0
    val numberBuilder = StringBuilder()

    charArray.forEachIndexed { line, chars ->
        numberBuilder.clear()
        var isValid = false
        val gearList = mutableListOf<Gear>()

        chars.forEachIndexed { col, char ->
            if (char.isDigit()) {
                numberBuilder.append(char)
                isValid = isValid || searchNeighbourSignal(line, col, activationPoint)
                searchNeighbourGear(line, col, gearMap).let { gear -> gearList += gear }
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
            if ( isValid) {
                res += numberBuilder.toString().toInt()
            }
            for (gear in gearList) {
                println("This gear is addded $gear")
                gears.addToMultimap(gear, numberBuilder.toString().toInt())
            }
        }
    }
    println("Result is $res")

    gears.forEach { (gear, numbers) ->
        println("Gear before at (row=${gear.row}, col=${gear.col}): $numbers")
    }

//    val filteredGears = gears.filter { (_, numList) ->  numList.size == 2}
//    filteredGears.forEach { (gear, numbers) ->
//        println("Gear at (row=${gear.row}, col=${gear.col}): $numbers")
//    }
//
//    filteredGears.forEach { (_, numList) ->  numList.reduce{ product, i -> product*i }}

    //filteredGears.forEach { (_, num) -> println(num.first) }

    println("This is version 9")
}

fun <K, V> MutableMap<K, MutableList<V>>.addToMultimap(key: K, value: V) {
    this.computeIfAbsent(key) { mutableListOf() }.add(value)
}

data class Gear (val row : Int, val col : Int)
fun searchNeighbourSignal(h: Int, w: Int, multiMap: Map<Int, MutableList<Int>>) : Boolean {
    return multiMap.filterKeys {
        it in h-1..h+1
    }.any { (_, valueList) ->
        valueList.any { it in w-1.. w+1 }
    }
}

fun searchNeighbourGear(h: Int, w: Int, multiMap: Map<Int, MutableList<Int>>) : List<Gear> {
    return multiMap.flatMap { (row, colList) -> colList.map { col -> Gear(row, col) }
    }.filter {
        gear -> gear.row in h-1..h+1 && gear.col in w-1..w+1
    }
}