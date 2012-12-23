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
        factorize(number).max()
    }

    def factorize(number) {
        def primes = []
        while (number % 2L == 0) {
            number = (long)(number / 2)
            primes.push(2)
        }
        def end = sqrt(number)
        for (def candFactor = 3; candFactor <= end; candFactor += 2) {
            while (number % candFactor == 0) {
                primes.push(candFactor)
                number = (long)(number / candFactor)
            }
        }
        if (number > 1) primes.push(number)
        primes
    }

    //4: Largest palindrome product
    def problem4() {
        (100..999).collect {a -> (100..999).collect {b -> a*b}}
            .flatten()
            .findAll { (""+it).equals( (""+it).reverse() ) }
            .max()
    }

    //9: Special Pythagorean Triplets
    def problem9() {
        (332..1).each {
          a -> (998..a).each {
            b -> (999..b).each {
              c-> if (Math.pow(a,2) + Math.pow(b,2) == Math.pow(c,2) && a+b+c==1000) println a*b*c}}}
    }

    //19: Counting Sundays
    def problem19() {   
        def lastFirstSunday = 0
        def sundaysOnFirstOfMonth = []
        (1900..2000).collect { year ->
            def febDays = year % 4 || (year % 100 == 0 && year % 400) ? 28 : 29
            [year: year, months:  
                (1..12).findAll { month -> 
                    def nofDays = [4,6,9,11].contains(month) ? 30 : month == 2 ? febDays : 31                
                    1 == (lastFirstSunday = (1..5).collect {it*7 + lastFirstSunday}.findAll {it > nofDays}.min() - nofDays)
                }
            ]
        }0 7 14 21        
        .collect { 
            it.year == 1900 ?
                it.months.contains(12) ? 1 : 0
            : it.year == 2000 ?
                it.months.findAll{it < 12}.size()
            : it.months.size()
        }
        .flatten().sum()
    }

    void main() {
        assert [5,10] == [1,2]*.multiply(5)
        assert [1904,1908,1912,1916,1920,1924,1928,1932,1936,1940,1944,1948,1952,1956,1960,1964,1968,1972,1976,1980,1984,1988,1992,1996,2000] ==
        (1900..2000).findAll{ year -> !( year % 4 || (year % 100 == 0 && year % 400) )}
        println problem19()
    }
}