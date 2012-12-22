new Euler().main()

import static java.lang.Math.*

public class Euler {

    // 1: multiples of 3 or 5
    def problem1() {(3..999).findAll { it % 3 == 0 || it % 5 == 0}.sum()}

    // 2: Even Fibonacci primes
    def problem2() {
        def list = [1,1]
        generateFibonacci(list, 4000000)
        println list.findAll {it % 2 == 0}.sum()
    }

    def generateFibonacci(list, limit) {
        if (list[-2] + list[-1] <= limit) {
            list.push (list[-2] + list[-1])
            generateFibonacci(list, limit)
        }
    }

    // 3: Largest prime factor
    def problem3() {
        def number = 600851475143
        def primes = []
        divide(number, primes)
    }

    def divide(number, primes) {
        int currPrime = nextPrime(1)
        while(mod(number, currPrime) != 0 && sqrt(number) >= currPrime) {
            currPrime = nextPrime(currPrime)
        }
        if (mod(number, currPrime) == 0) {
            println "$number $currPrime"
            primes.push(currPrime)
            return divide(number / currPrime, primes)
        }
        primes.push(number)
        return primes
    }

    def nextPrime(givenPrime) {
        def candPrime = givenPrime+1
        if ( (1..2).contains(givenPrime) ) return candPrime
        while(true) {
            def possibleFactors = 2..floor(sqrt(candPrime))
            for (candFactor in possibleFactors) {
                if (mod(candPrime, candFactor) == 0) {
                    break
                }
                return candPrime
            }
            candPrime++
        }
    }

    def mod(number, divisor) {
        return ((int)number) % ((int)divisor)
    }


    //9: Special Pythagorean Triplets
    def problem9() {
        (332..1).each {
          a -> (998..a).each {
            b -> (999..b).each {
              c-> if (Math.pow(a,2) + Math.pow(b,2) == Math.pow(c,2) && a+b+c==1000) println a*b*c}}}
    }

    void main() {
        assert 5 == nextPrime(3)
        assert 7 == nextPrime(5)
        assert 9 == nextPrime(7)
        assert [2, 5] == divide(10, [])
    }
}