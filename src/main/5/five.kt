import java.io.File
import kotlin.io.println

data class Mapping (val src : Long, val des : Long, val range : Long) {
    fun isPresent(key: Long): Boolean = key in src until ( src + range )
    fun getOrKey(key: Long): Long = if (isPresent(key)) des + (key-src) else key
}

fun nextStep (lines: MutableList<String>, seeds: Sequence<Long>): Sequence<Long> {
    var currentLine = lines.removeAt(0)
    var mappings: MutableList<Mapping> = mutableListOf()

    while ( currentLine.isNotEmpty() && lines.isNotEmpty()) {
        currentLine = lines.removeAt(0)
        if ( currentLine.isNotEmpty() ) {
            var (des, src, range) = currentLine.split("\\s+".toRegex())
                .map { it.toLong() }
            mappings.add(Mapping(src, des, range))
        }
    }

    return seeds.map { seed ->
        mappings.firstOrNull { it.isPresent(seed) }?.getOrKey(seed) ?: seed }
}

fun getMinFromSeeds(lines: MutableList<String>, seeds: Sequence<Long>): Long {
    lines.removeAt(0)
    var steps = seeds

    while (lines.isNotEmpty()) steps = nextStep(lines, steps)

    return steps.min()!!
}

fun partOne(inputFilePath: String) : Long {
    var lines = File(inputFilePath).readLines().toMutableList()
    var seeds = lines.removeAt(0).split(":").last().trim().split("\\s+".toRegex()).map { it.toLong() }

    return getMinFromSeeds(lines.toMutableList(), seeds.asSequence())

}

fun partTwo(inputFilePath: String) : Long {
    var lines = File(inputFilePath).readLines().toMutableList()
    var seedRanges = lines.removeAt(0).split(":").last().trim().split("\\s+".toRegex()).map(String::toLong)
    var minsOfRanges = mutableListOf<Long>()
    var threads = mutableListOf<Thread>()
    seedRanges.windowed(2, 2) { (start, len) ->
        val thread = Thread {
            val range = start..start + len
            var seeds = range.asSequence()
            minsOfRanges.add(getMinFromSeeds(lines.toMutableList(), seeds))
        }
        thread.start()
        threads+= thread
    }
     threads.forEach {
         it.join()
     }

    return minsOfRanges.min()
}

fun main() {
    val inputFilePath = "C:\\Users\\saali\\IdeaProjects\\tesing\\src\\main\\5\\full"
    var startTime = System.currentTimeMillis()
    println("Part 1 result is " + partOne(inputFilePath))
    println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms")
    startTime = System.currentTimeMillis()
    println("the start time is $startTime")
    println("Part 2 result is " + partTwo(inputFilePath))
    println("Execution time: " + (System.currentTimeMillis() - startTime)/100 + "s")
}