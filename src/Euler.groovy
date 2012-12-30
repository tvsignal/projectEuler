
import static java.lang.Integer.*
import static java.lang.Math.*

public class Euler {

    public static void main(String[] args) {
        def e = new Euler()
        assert e.noDigitsInCommon("4327","659")
        println e.problem42()
    }
    
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
        }
        .collect { 
            it.year == 1900 ?
                it.months.contains(12) ? 1 : 0
            : it.year == 2000 ?
                it.months.findAll{it < 12}.size()
            : it.months.size()
        }
        .flatten().sum()
    }

    //32: Pandigital products
    def problem32() {
        def products = []
        (1..99).collect{""+it}.findAll {it.matches(/[1-9]+/)}.findAll { !(it =~ /(.).*\1/) }
        .each {a ->
            (100..9999).collect{""+it}.findAll {it.matches(/[1-9]+/)}.findAll { !(it =~ /(.).*\1/) && noDigitsInCommon(a,it)}
            .each { b ->
                def c = ""+(valueOf(a)*valueOf(b))
                if ( c.matches(/[1-9]+/) && (a+b+c).size() == 9 && !(c =~ /(.).*\1/) && noDigitsInCommon(c, a+b)) {
                    println ((a+b) + c)
                    products.push(valueOf(c))
                }
            }
        }
        products.unique().sum()
    }

    def pentagons = []
    def pentagon(n) { pentagons[n] ? pentagons[n] : (pentagons[n] = n*(3*n-1)/2) }

    //42: Pentagon numbers
    def problem42() {
        def minDelta = Integer.MAX_VALUE
        def minSep = 1
        def minJ = 1
        (minSep..minSep+20).each { sep ->
            (minJ..minJ+100-1).each { j ->
                def k = j + sep
                if (pentagons.contains(pentagon(k) - pentagon(j))) {
                    println "$j, $k: " + pentagon(j) + " " + pentagon(k)
                    def sum = pentagon(k) + pentagon(j)
                    def searchInd = k+1
                    while(pentagon(searchInd) <= sum) {
                        if (pentagon(searchInd) == sum)) {
                            minDelta = min(pentagon(k) - pentagon(j), minDelta)
                        }
                        searchInd++
                    }
                }
            }
        }
    }

    def noDigitsInCommon(a, b) {
        b.findAll {a.contains(it)}.size() == 0
    }

}