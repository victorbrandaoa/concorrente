package main

import (
	"fmt"
	"math/rand"
	"time"
)

func doSomething(routineId int) {
	rand.Seed(time.Now().UnixNano())
	sleepTime := rand.Intn(10)
	fmt.Printf("Goroutine %d will sleep for %d seconds...\n", routineId, sleepTime)
	time.Sleep(time.Duration(sleepTime) * time.Second)

	ch <- "Acordei"
}

func main() {

	recieveCh := make(chan string)
	i := 0

	// for i := 0; i < 5; i++ {

	// timerCh := time.Tick(time.Second * 3)
	go doSomething(i, ch)
	select {
	case resp <- ch:
		fmt.Printf("Goroutine %d finish...\n", i)
	case <-time.After(time.Second * 3):
		fmt.Printf("Goroutine %d didn't finish...\n", i)
	}
	// }
}
