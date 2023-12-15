import scala.io.Source
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.HashMap
import scala.compiletime.ops.boolean

object Seven extends App {
    val filename = "full.in"

    val allBets = HashMap[String, Int]()  
    val handsByTypes = Array.fill(2, 7)(ArrayBuffer[String]())
    
    for (line <- Source.fromFile(filename).getLines) {
        val parts = line.split(" ")

        handsByTypes(0)(getType(parts.head)).addOne(parts.head)
        handsByTypes(1)(getType(parts.head, false)).addOne(parts.head)
        allBets.put(parts.head, parts.last.toInt)
    }
    
    for ( i <- 0 until 2) {
        var ranks = allBets.size
        var res = 0

        handsByTypes(i).zipWithIndex.foreach { case (hands, index) =>
            hands.sortWith { (hand1, hand2) => 
                compareHands(hand1, hand2, i == 0)
            }.foreach { hand =>
                res += allBets(hand)*ranks
                ranks -= 1
            }
        }
        println(s"Result of part ${i+1} is $res")
    }

    // Functions

    def getValue(card: Char, part1: Boolean = true): Int = card match {
        case 'A' => 14
        case 'K' => 13
        case 'Q' => 12
        case 'J' => if (part1) 11 else 1
        case 'T' => 10
        case _ => card.asDigit
    }
    
    
    def getType(hand: String, part1: Boolean = true): Int = {
        val counts = hand.groupBy(identity).view.mapValues(_.length).toMap

        if (!part1 && hand.contains('J')) {
            if (counts.keys.size <= 2) return 0
            counts('J') match
                case 3 => 1
                case 2 if counts.keys.size == 3 => 1
                case 2 => 3
                case 1 if counts.values.exists(_ == 3) => 1
                case 1 if counts.values.count(_ == 2) == 2 => 2
                case 1 if counts.values.count(_ == 2) == 1 => 3
                case 1 => 5
                case _ => 6
        } else {
            counts.keys.size match {
                case 1 => 0
                case 2 if counts.values.exists(_ == 4) =>  1 
                case 2 => 2
                case 3 if counts.values.exists(_ == 3) => 3
                case 3 => 4 
                case 4 => 5
                case _ => 6
            }
        }
    }
    
    def compareHands(hand1: String, hand2: String, part1: Boolean = true): Boolean = {
        hand1.lazyZip(hand2).collectFirst {
            case (card1, card2) if getValue(card1, part1) > getValue(card2, part1) => true
            case (card1, card2) if getValue(card1, part1) < getValue(card2, part1) => false
        }.get
    }
    
}
