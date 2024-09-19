import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

// Функция создания списка случайных чисел
fun getRandomList(size:Int,a:Int,b:Int): List<Int> {
    return List(size) { (a .. b).random() }
}

// Перегруженная функция создания списка случайных символов
fun getRandomList(size:Int,a:Char,b:Char): List<Char> {
    return List(size) { (a..b).random() }
}

// Функция распаковки списка с задержкой
suspend fun <T> unpack(list: List<T>): Int {
    var count = 0
    for (item in list) {
        count++
        delay(1000) // Задержка в 1 секунду
        println(item)
    }
    return count
}

// Функция объединения списков
fun <T> concatenate(list1: List<T>, list2: List<T>): Pair<Int, MutableList<T>> {
    val combinedList = mutableListOf<T>()
    combinedList.addAll(list1)
    combinedList.addAll(list2)
    return Pair(combinedList.size, combinedList)
}

fun main() {
    runBlocking {
        val time = measureTimeMillis {
            // Получение списков целых чисел и символов
            val intList = getRandomList(10,1,100)
            val charList = getRandomList(10,'a','z')

            // Асинхронная распаковка списков
            val intCount = async { unpack(intList) }
            val charCount = async { unpack(charList) }

            // Ожидание завершения распаковки
            val totalSize = intCount.await() + charCount.await()
            println("Общий размер списка: $totalSize")

            // Объединение списков
            val combinedResult = concatenate(intList, charList)
            println("Объединенный список: ${combinedResult.second}")
        }
        println("Затрачено времени на операции $time мс")
    }
}

