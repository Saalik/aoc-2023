import java.io.File

fun main() {
    val input = "./src/main/1/fullInput"
    var res = 0
    File(input).forEachLine {
        val nums = it.replace("[^0-9]".toRegex(), "")
        res += nums.first().digitToInt() * 10 + nums.last().digitToInt()
    }
    println("First part result: $res")

    val dictionary = arrayOf<String>("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    res = 0
    File(input).forEachLine {
        var cpt = 1
        var sent = it
        for ( str in dictionary ) {
            sent = sent.replace(str, str.first()+cpt.toString()+str.last())
            cpt++
        }
        sent = sent.replace("[^0-9]".toRegex(), "")
        res += sent.first().digitToInt() * 10 + sent.last().digitToInt()
    }

    println("Second part result: $res")

}