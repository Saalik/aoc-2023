import scala.io.Source
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.HashMap

object Seven extends App {
    val filename = "fake.in"

    println("Starting part one")
    val allHands = ArrayBuffer[String]()
    val allBets = HashMap[String, Int]()

    val handsByTypes = Array.fill(7)(ArrayBuffer[String]())

    val lines = Source.fromFile(filename).getLines
    val ranks = lines.size
    for (line <- lines) {
        val parts = line.split(" ")
        allHands += parts.head
        allBets.put(parts.head, parts.last.toInt)
    }

    println("Parsed all the hands and bets")

    allHands.foreach { hand =>
        handsByTypes(getType(hand)).addOne(hand)
    }

    handsByTypes.zipWithIndex { case (hand, index) =>
        
    }

    def getValue(card: String): Int = {
        val score = card match {
            case "A" => 14
            case "J" => 11
            case "Q" => 12
            case "K" => 13
            case _ => card.toInt
        }
        score
    }


    def compareHands(hand1: Array[String], hand2: Array[String]): Int = {
        hand1.zip(hand2).collectFirst {
            case (card1, card2) if getValue(card1) > getValue(card2) => 1
            case (card1, card2) if getValue(card1) < getValue(card2) => 2
        }.getOrElse(0)
    }

    def getType(hand: String): Int = {
        val counts = hand.groupBy(identity).view.mapValues(_.length).toMap
        counts.keys.size match {
            case 1 => 0
            case 2 => 
                if (counts.values.exists(_ == 4)) { 1 }
                else { 2 }
            case 3 => 
                if (counts.values.exists(_ == 3)) { 3 }
                else { 4 }
            case 4 => 5
            case _ : Int => 6
        }
    }

}
