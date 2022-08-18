package main

import (
	"fmt"
	"math/rand"
	"time"
)

type Bid struct {
	item      int
	bidValue  int64
	bidFailed bool
}

// this function sents items into the itemChannel
func startItemsStream(itemCh chan<- int) {
	rand.Seed(time.Now().UnixNano()) // used to set a seed value to generate pseudorandom numbers

	item := 0
	for {
		sleepTime := rand.Intn(5)
		time.Sleep(time.Second * time.Duration(sleepTime))
		fmt.Printf("Item %d will take %d seconds to be sent into the channel\n", item, sleepTime)
		itemCh <- item
		item++

		stopSign := rand.Intn(100)
		if stopSign > 50 { // randomly decides when the items' production stop
			break
		}
	}
	close(itemCh) // close the itemsChannel
}

// this function produce a Bid for an item
func bid(item int) Bid {
	value := rand.Int63n(100)
	failed := false
	if value < 50 {
		failed = true
	}

	bidObj := Bid{item: item, bidValue: value, bidFailed: failed}
	sleepTime := rand.Intn(10)

	fmt.Printf("The request to item %d will take %d seconds\n", item, sleepTime)

	time.Sleep(time.Second * time.Duration(sleepTime))
	return bidObj
}

func handle(nServers int) chan Bid {
	itemCh := make(chan int)    // create the itemsChannel
	go startItemsStream(itemCh) // starts a goroutine to populate the itemsChannel

	bidChan := make(chan Bid)  // create the bidChannel
	joinChan := make(chan int) // create the joinChannel, which is used to wait all the goroutines to finish

	for i := 0; i < nServers; i++ { // for each server starts a goroutine that consume items until there's no item left
		go func() {
			for item := range itemCh {
				bidObj := bid(item)
				bidChan <- bidObj
			}
			joinChan <- 1 // sign that the goroutine's job has finished
		}()
	}

	go func() {
		for i := 0; i < nServers; i++ { // for each server waits until its goroutine's done
			<-joinChan
		}
		close(joinChan) // close the joinChannel
		close(bidChan)  // close the bidChannel
	}()

	return bidChan
}

func main() {
	fmt.Println("Enter the number of servers")
	var nServers int
	fmt.Scanln(&nServers)

	bidChan := handle(nServers)

	for bidObj := range bidChan { // consume all Bids that are produced
		fmt.Printf("Bid %+v is available to process...\n", bidObj)
	}
}
