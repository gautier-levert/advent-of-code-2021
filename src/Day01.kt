/*
 * As the submarine drops below the surface of the ocean, it automatically
 * performs a sonar sweep of the nearby sea floor. On a small screen, the
 * sonar sweep report (your puzzle input) appears: each line is a measurement
 * of the sea floor depth as the sweep looks further and further away from the
 * submarine.
 */

fun main() {

    /*
     * The first order of business is to figure out how quickly the depth
     * increases, just so you know what you're dealing with - you never know if
     * the keys will get carried into deeper water by an ocean current or a fish
     * or something.
     *
     * To do this, count the number of times a depth measurement increases from
     * the previous measurement. (There is no measurement before the first
     * measurement.)
     */
    fun part1(input: List<String>): Int {
        var count = 0
        for (i in 0 until (input.size - 1)) {
            val first = input[i].toInt()
            val second = input[i + 1].toInt()
            if (second > first) {
                count += 1
            }
        }
        return count
    }

    /*
     * Your goal now is to count the number of times the sum of measurements in
     * this sliding window increases from the previous sum. So, compare A with B,
     * then compare B with C, then C with D, and so on. Stop when there aren't
     * enough measurements left to create a new three-measurement sum.
     */
    fun part2(input: List<String>): Int {
        return input.asSequence()
            .map(String::toInt)
            .windowed(3, 1, false)
            .map(List<Int>::sum)
            .windowed(2, 1, false)
            .filter { sums -> sums[1] > sums[0] }
            .count()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println("part 1 answer: ${part1(input)}")
    println("part 2 answer: ${part2(input)}")
}
