package main

import (
	"fmt"
	"math/rand"
	"time"
)

type Request struct {
	id   int
	size int
}

type Bucket struct {
	maxTokens int
	nTokens   int
	bucketCh  chan int
}

func (b *Bucket) addToken() {
	b.bucketCh <- 1

	b.nTokens++

	<-b.bucketCh
}

func (b *Bucket) removeTokens(nTokensToRemove int) {
	b.bucketCh <- 1

	b.nTokens -= nTokensToRemove

	<-b.bucketCh
}

func fillBucket(freq int) {
	fmt.Printf("Entrou!!!\n")
	sleepTime := time.Duration(1 / 10)

	for {
		time.Sleep(sleepTime * time.Second)
		fmt.Printf("nTokens == %d\n maxTokens == %d\n", bucket.nTokens, bucket.maxTokens)
		if bucket.maxTokens != bucket.nTokens {
			bucket.addToken()
			fmt.Printf("Adding token: %d\n", bucket.nTokens)
		}
	}
}

func limitCap_wait(req Request) {
	s := req.size

	done := false
	for !done {
		if bucket.nTokens >= s {
			bucket.removeTokens(s)
			done = true
		} else {
			time.Sleep(time.Second * time.Duration(rand.Intn(3)))
		}
	}
}

func run(req Request) {
	syncCh <- 1
	fmt.Printf("Tokens in the bucket before: %d\n", bucket.nTokens)
	limitCap_wait(req)
	fmt.Printf("Req %d with size %d is done...\n", req.id, req.size)
	fmt.Printf("Tokens in the bucket after: %d\n", bucket.nTokens)
	<-syncCh
}

var syncCh chan int
var bucket Bucket

func main() {
	BUCKET_SIZE := 10
	R := 10

	joinCh := make(chan int)

	syncCh = make(chan int, 1)

	bucketCh := make(chan int, 1)
	bucket = Bucket{maxTokens: BUCKET_SIZE, nTokens: BUCKET_SIZE, bucketCh: bucketCh}
	go fillBucket(1 / R)

	for i := 0; i < 10; i++ {
		go func(id int, joinCh chan int) {
			reqSize := rand.Intn(10)
			req := Request{id: id, size: reqSize}

			run(req)
			joinCh <- 1
		}(i, joinCh)
	}

	for i := 0; i < 10; i++ {
		<-joinCh
	}
}
