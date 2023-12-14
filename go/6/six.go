package main

import (
	"bufio"
	"errors"
	"log"
	"os"
	"strconv"
	"strings"
)

func main() {
	time, distance, longTime, longDistance := parseInput("6/full.in")

	res := 1
	for i := 0; i < len(time); i++ {
		res *= numberOfWays(time[i], distance[i])
	}

	res2 := numberOfWays(longTime, longDistance)

	println("The result for part 1 is", res)
	println("The result for part 2 is", res2)

}

func distTraveled(speed, time int) int {
	return speed * time
}

func numberOfWays(duration, record int) int {
	ways := 0
	for i := 1; i < duration; i++ {
		if distTraveled(i, duration-i) > record {
			ways++
		}
	}
	return ways
}

func parseInput(path string) ([]int, []int, int, int) {
	file, err := os.Open(path)
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	var time []int
	var distance []int
	var longTime string
	var longDistance string

	for scanner.Scan() {
		elem := strings.Fields(scanner.Text())
		for i, num := range elem {
			if i == 0 {
				continue
			}
			value, err := strconv.Atoi(num)
			if err != nil {
				log.Fatal(err)
			}

			if strings.HasPrefix(scanner.Text(), "Time") {
				time = append(time, value)
				longTime += num
			} else {
				distance = append(distance, value)
				longDistance += num
			}
		}
	}

	if len(time) != len(distance) {
		err := errors.New("Lengths of time and distance are not equal")
		log.Fatal(err)
	}

	longTimeInt, _ := strconv.Atoi(longTime)
	longDistanceInt, _ := strconv.Atoi(longDistance)

	return time, distance, longTimeInt, longDistanceInt
}
