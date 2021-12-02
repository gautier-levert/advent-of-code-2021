fun main() {

    /*
     *  * It seems like the submarine can take a series of commands like forward 1,
     *  down 2, or up 3:
     *  - forward X increases the horizontal position by X units.
     *  - down X increases the depth by X units.
     *  - up X decreases the depth by X units.
     *
     * Note that since you're on a submarine, down and up affect your depth, and
     * so they have the opposite result of what you might expect.
     * Calculate the horizontal position and depth you would have after following
     * the planned course. What do you get if you multiply your final horizontal
     * position by your final depth?
     */
    fun part1(input: List<String>): Int {
        var position = 0
        var depth = 0

        for (line in input) {
            val (verb, count) = line.split(" ")
            when (verb) {
                "forward" -> position += count.toInt()
                "down" -> depth += count.toInt()
                "up" -> depth -= count.toInt()
            }
        }

        return position * depth
    }

    /*
     * Based on your calculations, the planned course doesn't seem to make any
     * sense. You find the submarine manual and discover that the process is
     * actually slightly more complicated.
     *
     * In addition to horizontal position and depth, you'll also need to track a
     * third value, aim, which also starts at 0. The commands also mean something
     * entirely different than you first thought:
     *  - down X increases your aim by X units.
     *  - up X decreases your aim by X units.
     *  - forward X does two things:
     *     - It increases your horizontal position by X units.
     *     - It increases your depth by your aim multiplied by X.
     */
    fun part2(input: List<String>): Int {
        return input.asSequence()
            .map { line ->
                val splitted = line.split(" ")
                Pair(splitted[0], splitted[1].toInt())
            }
            .fold(Position()) { position, order ->
                when (order.first) {
                    "forward" -> position.forward(order.second)
                    "down" -> position.down(order.second)
                    "up" -> position.up(order.second)
                    else -> position
                }
            }
            .value()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println("part 1 answer: ${part1(input)}")
    println("part 2 answer: ${part2(input)}")
}

data class Position(
    val horizontal: Int = 0,
    val depth: Int = 0,
    val aim: Int = 0,
) {
    fun forward(count: Int) = copy(
        horizontal = horizontal + count,
        depth = depth + aim * count
    )

    fun down(count: Int) = copy(
        aim = aim + count
    )

    fun up(count: Int) = copy(
        aim = aim - count
    )

    fun value() = horizontal * depth
}
