package main.scala.com.herokuapp.jotc.inversionsBench

import scala.io.Source
import java.time.LocalDateTime

/**
 * Created by Joe on 25/10/15 at 20:05.
 *
 * Requirements: ${REQUIREMENTS}
 */
object Inversions {
  var inversions : Long = 0

  def main(args: Array[String]): Unit = {
    val file = new java.io.File("./src/main/resources/IntegerArray.txt")
    val numbers : List[Int] = io.Source.fromFile(file, bufferSize = Source.DefaultBufSize * 2).getLines().toList.map(_.toInt)
    println(LocalDateTime.now())
//    val numbers : List[Int] = List(37, 7, 2, 14, 35, 47, 10, 24, 44, 17, 34, 11, 16, 48, 1, 39, 6, 33, 43, 26, 40, 4, 28, 5, 38, 41, 42, 12, 13, 21, 29, 18, 3, 19, 0, 32, 46, 27, 31, 25, 15, 36, 20, 8, 9, 49, 22, 23, 30, 45)
    msort((x: Int, y: Int) => x <= y)(numbers)
    println(LocalDateTime.now())
    printf("Number of inversions is %s", inversions)
  }

  def msort[T](less: (T, T) => Boolean)(list: List[T]): List[T] = {
    def merge(left: List[T], right: List[T], acc: List[T]): List[T] = (left, right) match {
        case (Nil, _) => right.reverse ::: acc
        case (_, Nil) => left.reverse ::: acc
        case (x :: left1, y :: right1) =>
          if (less(x, y)) merge(left1, right, x :: acc)
          else {
            inversions += left.size
            merge(left, right1, y :: acc)
          }
      }
    val n = list.length / 2
    if (n == 0) list
    else {
      val (left, right) = list splitAt n
      merge(msort(less)(left), msort(less)(right), Nil).reverse
    }
  }
}
