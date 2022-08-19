package main

import (
	"fmt"
	"math/rand"
	"time"
)

type Barrier struct {
	n         int
	counter   int
	signCh    chan int
	isFalling bool
}

func (b *Barrier) Wait() {
	go func() {
		b.signCh <- 1 // cria goroutine para avisar que acabou o que precisava fazer
	}()

	for b.counter > 0 && !b.isFalling {
		// enquanto houver goroutines não finalizadas ou a barreira não estiver caindo
		<-b.signCh // espera os avisos de termino das outras goroutines

		if !b.isFalling {
			// se a barreira não está caindo decremente o número de goroutines não finalizadas
			b.counter--
		} else {
			// se a barreira está caindo incremente o número de goroutines não finalizadas para resetar
			b.counter++
		}
	}

	if b.counter == 0 {
		// se o número de goroutines não finalizadas é 0 incremente o counter para iniciar o reset
		// e informe que a barreira está caindo
		b.counter++
		b.isFalling = true
	} else if b.counter == b.n {
		// se o counter é igual ao n inicial então resete o isFalling
		b.isFalling = false
	}

	go func() {
		b.signCh <- 1 // cria goroutine para continuar o processo de destravar as demais goroutines
	}()
}

func main() {
	fmt.Println("Enter the number of goroutines")
	var n int
	fmt.Scanln(&n)

	joinChan := make(chan int)

	signCh := make(chan int)
	barrier := Barrier{n: n, counter: n, signCh: signCh}

	for i := 0; i < n; i++ {
		go func(routineId int) {
			sleepTime := rand.Intn(5)
			fmt.Printf("The goroutine %d will sleep for %d seconds\n", routineId, sleepTime)
			time.Sleep(time.Duration(sleepTime) * time.Second)

			s := rand.Intn(10)
			time.Sleep(time.Duration(s) * time.Second)

			barrier.Wait()

			fmt.Printf("The final b counter %d and isFalling %t\n", barrier.counter, barrier.isFalling)
			fmt.Printf("The goroutine %d finish its choice\n", routineId)
			joinChan <- 1
		}(i)
	}

	for i := 0; i < n; i++ {
		<-joinChan
	}
	close(joinChan)
	close(signCh)

	fmt.Printf("The n value is %d\n", n)
}
