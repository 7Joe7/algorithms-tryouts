package main.scala.com.herokuapp.jotc.algorithms

import java.time.LocalDateTime

import scala.io.Source

/**
 * Created by Joe on 25/10/15 at 20:05.
 *
 * Requirements: ${REQUIREMENTS}
 */
object Comparisons {
  var comparisons : Long = 0

  def less(x: Int, y: Int): Boolean = {
    comparisons += 1
    x <= y
  }

  def main(args: Array[String]): Unit = {
    val file = new java.io.File("./src/main/resources/1000.txt")
    val numbers : Array[Int] = io.Source.fromFile(file, bufferSize = Source.DefaultBufSize * 2).getLines().toArray.map(_.toInt)
    println(LocalDateTime.now())
//    val numbers : Array[Int] = Array(37, 7, 2, 14, 35, 47, 10, 24, 44, 17, 34, 11, 16, 48, 1, 39, 6, 33, 43, 26, 40, 4, 28, 5, 38, 41, 42, 12, 13, 21, 29, 18, 3, 19, 0, 32, 46, 27, 31, 25, 15, 36, 20, 8, 9, 49, 22, 23, 30, 45)
    sortArray(numbers, 0, numbers.length - 1)
//    numbers.foreach(println)
    println(LocalDateTime.now())
    printf("Number of comparisons is %s", comparisons)
  }

  def swap(array: Array[Int], i1: Int, i2: Int): Unit = {
    val tmp = array(i1)
    array(i1) = array(i2)
    array(i2) = tmp
  }

  def sortArray(array: Array[Int], left: Int, right: Int) {
    if (right <= left) {
      return
    }
    comparisons += right - left + 2
    val middle_index = ((right + 1 - left).toFloat / 2).round - 1 + left
    if (array(left) < array(right)) {
      if (array(right) < array(middle_index)) swap(array, right, left)
      else if (array(left) < array(middle_index)) swap(array, middle_index, left)
    } else if (array(middle_index) < array(left)) {
      if (array(right) < array(middle_index)) swap(array, middle_index, left)
      else swap(array, right, left)
    }

    val pivot = array(left)
    var p = left + 1
    var i = left + 1
    while (i <= right) {
      if (array(i) < pivot) {
        if (p != i) swap(array, p, i)
        p += 1
      }
      i += 1
    }
    array(left) = array(p - 1)
    array(p - 1) = pivot
    sortArray(array, left, p - 2)
    sortArray(array, p, right)
  }
}
