import java.io.File
import kotlin.math.max

fun main() {
    val INPUT = "./src/main/2/simple"
    val BLUE    : Int = 14
    val RED     : Int = 12
    val GREEN   : Int = 13

    var res     : Int = 0
    var res2    : Int = 0

    File(INPUT).forEachLine {
        var game : Int
        var games : Array<String> = arrayOf()
        var isValid : Boolean = true
        val seq = it.split(":")
        var minB  : Int = 0
        var minR : Int = 0
        var minG : Int = 0

        game = seq.first().replace("[^0-9]".toRegex(), "").toInt()
        games = seq.last().split(";").toTypedArray()

        for ( str in games ) {
            val draws = str.split(",")

            for (draw in draws) {
                val tmp = draw.replace("\\s+".toRegex(), "")
                val number = tmp.replace("[^0-9]".toRegex(), "").toInt()
                val color = tmp.replace("[0-9]".toRegex(), "")

                when (color) {
                    "red" -> {
                        minR = max(minR, number)
                        if (number > RED) isValid = false
                    }

                    "blue" -> {
                        minB = max(minB, number)
                        if (number > BLUE) isValid = false
                    }

                    "green" -> {
                        minG = max(minG, number)
                        if (number > GREEN) isValid = false
                    }
                }
            }
        }

        if ( isValid ) {
            res += game
        }

        res2 += ( minB * minR * minG )
    }

    println("Result for first part is $res")
    println("Result for second part is $res2")
}