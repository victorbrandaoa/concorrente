package main

import (
	"fmt"
	"math/rand"
	"time"
)

func main() {
	fmt.Println("Enter the number of goroutines")
	var n int
	fmt.Scanln(&n)

	joinChan := make(chan int)

	for i := 0; i < n; i++ {
		go func(routineId int) {
			sleepTime := rand.Intn(5)
			fmt.Printf("The goroutine %d will sleep for %d seconds\n", routineId, sleepTime)
			time.Sleep(time.Duration(sleepTime) * time.Second)
			joinChan <- 1
		}(i)
	}

	for i := 0; i < n; i++ {
		<-joinChan
	}
	close(joinChan)

	fmt.Printf("The n value is %d\n", n)
}
