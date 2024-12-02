import java.io.InputStream

fun main() {
    val url = readFileFromResources("input.txt")
    var safeCounter = 0
    url?.lines()?.onEach { line ->
        if (line.isEmpty())
            return@onEach
        val arr = line.split(" ").map { it.toInt() }
        val isIncrease = arr[0] < arr[1]

        for ((index, value) in arr.withIndex()) {
            if (index == 0) {
                continue
            }
            val diff = if (isIncrease) {
                value - arr[index - 1]
            } else {
                arr[index - 1] - value
            }

            val valid = diff in intArrayOf(1, 2, 3)

            if (valid.not()) {
                return@onEach
            }
        }
        safeCounter += 1
    }

    println(safeCounter)
}

fun readFileFromResources(fileName: String): String? {
    val classLoader = Thread.currentThread().contextClassLoader
    val inputStream: InputStream? = classLoader.getResourceAsStream(fileName)
    return inputStream?.bufferedReader()?.use { it.readText() }
}