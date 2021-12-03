import java.lang.IllegalStateException

fun main() {

    /*
     * The submarine has been making some odd creaking noises, so you ask it to
     * produce a diagnostic report just in case.
     *
     * The diagnostic report (your puzzle input) consists of a list of binary
     * numbers which, when decoded properly, can tell you many useful things about
     * the conditions of the submarine. The first parameter to check is the power
     * consumption.
     *
     * You need to use the binary numbers in the diagnostic report to generate two
     * new binary numbers (called the gamma rate and the epsilon rate). The power
     * consumption can then be found by multiplying the gamma rate by the epsilon
     * rate.
     *
     * Each bit in the gamma rate can be determined by finding the most common bit
     * in the corresponding position of all numbers in the diagnostic report.
     */
    fun part1(input: List<String>): Int {
        val length = input.first().length

        val charArrays = input.map { it.toCharArray() }

        val gamma = (0 until length).asSequence()
            .map { index ->
                val (ones, zeroes) = charArrays.asSequence()
                    .map { array -> array[index] }
                    .partition { c -> c == '1' }

                if (ones.size > zeroes.size) '1' else '0'
            }
            .joinToString(separator = "")
            .toUInt(2)

        var mask = 0.toUInt()

        for (i in 0 until length) {
            mask = mask or (1.toUInt() shl i)
        }

        val epsilon = (gamma.inv() and mask)

        return (gamma * epsilon).toInt()
    }

    fun mostCommonValue(index: Int, input: List<String>): Char {
        val (ones, zeroes) = input.asSequence()
            .map { it.toCharArray() }
            .map { it[index] }
            .partition { it == '1' }

        return if (ones.count() >= zeroes.count()) '1' else '0'
    }

    fun leastCommonValue(index: Int, input: List<String>): Char {
        return if (mostCommonValue(index, input) == '1') '0' else '1'
    }

    fun filterValues(input: List<String>, criteriaFn: (Int, List<String>) -> Char): String {
        val length = input.first().length

        var values = input

        for (i in 0 until length) {
            val criteria = criteriaFn(i, values)
            values = values.filter { it[i] == criteria }
            if (values.size == 1) {
                return values.first()
            }
        }

        throw IllegalStateException("Unable to find single filtered value from $values")
    }

    /*
     * Both the oxygen generator rating and the CO2 scrubber rating are values
     * that can be found in your diagnostic report - finding them is the tricky
     * part. Both values are located using a similar process that involves
     * filtering out values until only one remains. Before searching for either
     * rating value, start with the full list of binary numbers from your
     * diagnostic report and consider just the first bit of those numbers.
     * Then:
     *
     *  - Keep only numbers selected by the bit criteria for the type of rating
     *    value for which you are searching. Discard numbers which do not match
     *    the bit criteria.
     *  - If you only have one number left, stop; this is the rating value for
     *    which you are searching.
     *  - Otherwise, repeat the process, considering the next bit to the right.
     *
     * The bit criteria depends on which type of rating value you want to find:
     *  - To find oxygen generator rating, determine the most common value (0 or
     *    1) in the current bit position, and keep only numbers with that bit in
     *    that position. If 0 and 1 are equally common, keep values with a 1 in
     *    the position being considered.
     *  - To find CO2 scrubber rating, determine the least common value (0 or 1)
     *    in the current bit position, and keep only numbers with that bit in
     *    that position. If 0 and 1 are equally common, keep values with a 0 in
     *    the position being considered.
     */
    fun part2(input: List<String>): Int {

        val oxygenGeneratorRating = filterValues(input, ::mostCommonValue).toInt(2)
        val co2ScrubberRating = filterValues(input, ::leastCommonValue).toInt(2)

        return oxygenGeneratorRating * co2ScrubberRating
    }



    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println("part 1 answer: ${part1(input)}")
    println("part 2 answer: ${part2(input)}")
}
