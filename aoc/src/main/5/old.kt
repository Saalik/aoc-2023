// import java.io.File

// class Mapping {
//     val src: Long
//     val des: Long
//     val range: Long

//     constructor(src: Long, des: Long, range: Long) {
//         this.src = src
//         this.des = des
//         this.range = range
//     }

//     override fun toString(): String {
//         return "src: ${src}, des: ${des}, range: ${range}"
//     }

//     fun getOrKey(key: Long): Long {
//         if ( key >= src && key < src+range ) {
//             return des + (key-src)
//         }
//         return key
//     }

// }
// fun main() {
//     val inputFilePath = "small"
//     var lines = File(inputFilePath).readLines().toMutableList()

//     val firstLine = lines.removeAt(0).split(":").last().trim().split("\\s+".toRegex()).map { it.toLong() }

//     println("Here are the seeds: ${firstLine}")
//     lines.removeAt(0)
//     var currentLine = lines.removeAt(0)

//     var seedToSoil = HashMap<Long, Long>()
//     var seedToSoil2 = listOf<Mapping>()
//     while ( currentLine.isNotEmpty() ) {
//         currentLine = lines.removeAt(0)
//         if ( currentLine.isNotEmpty() ) {
//             var (des, src, range) = currentLine.split("\\s+".toRegex()).map { it.toLong() }
//             // println("Des: ${des}, src: ${src}, range: ${range}")

//             for ( i in 0..range-1 ) {
//                 seedToSoil[src.toLong()+i] = des.toLong()+i 
//             }
//         }
//     }

//     currentLine = lines.removeAt(0)
//     var soilToFertilizer = HashMap<Long, Long>()

//     while ( currentLine.isNotEmpty() ) {
//         currentLine = lines.removeAt(0)
//         if ( currentLine.isNotEmpty() ) {
//             var (des, src, range) = currentLine.split("\\s+".toRegex()).map { it.toLong() }
//             // println("Des: ${des}, src: ${src}, range: ${range}")
//             for ( i in 0..range-1 ) {
//                 soilToFertilizer[src.toLong()+i] = des.toLong()+i 
//             }
//         }
//     }

//     currentLine = lines.removeAt(0)
//     var fertilizerToWater = HashMap<Long, Long>()

//     while ( currentLine.isNotEmpty() ) {
//         currentLine = lines.removeAt(0)
//         if ( currentLine.isNotEmpty() ) {
//             var (des, src, range) = currentLine.split("\\s+".toRegex()).map { it.toLong() }
//             // println("Des: ${des}, src: ${src}, range: ${range}")
//             for ( i in 0..range-1 ) {
//                 fertilizerToWater[src.toLong()+i] = des.toLong()+i 
//             }
//         }
//     }

//     currentLine = lines.removeAt(0)
//     var waterToLight = HashMap<Long, Long>()

//     while ( currentLine.isNotEmpty() ) {
//         currentLine = lines.removeAt(0)
//         if ( currentLine.isNotEmpty() ) {
//             var (des, src, range) = currentLine.split("\\s+".toRegex()).map { it.toLong() }
//             // println("Des: ${des}, src: ${src}, range: ${range}")
//             for ( i in 0..range-1 ) {
//                 waterToLight[src.toLong()+i] = des.toLong()+i 
//             }
//         }
//     }

//     currentLine = lines.removeAt(0)
//     var lightToTemp = HashMap<Long, Long>()

//     while ( currentLine.isNotEmpty() ) {
//         currentLine = lines.removeAt(0)
//         if ( currentLine.isNotEmpty() ) {
//             var (des, src, range) = currentLine.split("\\s+".toRegex()).map { it.toLong() }
//             // println("Des: ${des}, src: ${src}, range: ${range}")
//             for ( i in 0..range-1 ) {
//                 lightToTemp[src.toLong()+i] = des.toLong()+i 
//             }
//         }
//     }

//     currentLine = lines.removeAt(0)
//     var tempToHumidity = HashMap<Long, Long>()

//     while ( currentLine.isNotEmpty() ) {
//         currentLine = lines.removeAt(0)
//         if ( currentLine.isNotEmpty() ) {
//             var (des, src, range) = currentLine.split("\\s+".toRegex()).map { it.toLong() }
//             // println("Des: ${des}, src: ${src}, range: ${range}")
//             for ( i in 0..range-1 ) {
//                 tempToHumidity[src.toLong()+i] = des.toLong()+i 
//             }
//         }
//     }

//     currentLine = lines.removeAt(0)
//     var humidityToLocation = HashMap<Long, Long>()

//     while ( currentLine.isNotEmpty() && lines.isNotEmpty() ) {
//         currentLine = lines.removeAt(0)
//         if ( currentLine.isNotEmpty() ) {
//             var (des, src, range) = currentLine.split("\\s+".toRegex()).map { it.toLong() }
//             // println("Des: ${des}, src: ${src}, range: ${range}")
//             for ( i in 0..range-1 ) {
//                 humidityToLocation[src.toLong()+i] = des.toLong()+i 
//             }
//         }
//     }



//     val res = firstLine.map { 
//         val soil = seedToSoil.getOrDefault(it.toLong(), it.toLong())
//         val fertilizer = soilToFertilizer.getOrDefault(soil, soil)
//         val water = fertilizerToWater.getOrDefault(fertilizer, fertilizer)
//         val light = waterToLight.getOrDefault(water, water)
//         val temp = lightToTemp.getOrDefault(light, light)
//         val humidity = tempToHumidity.getOrDefault(temp, temp)
//         val location = humidityToLocation.getOrDefault(humidity, humidity)
//         location
//     }.min()

//     println("Result: ${res}")
    
// }