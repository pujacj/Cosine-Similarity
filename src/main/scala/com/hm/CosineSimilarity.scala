package com.hm

/**
  * Created by pooja on 14/4/17.
  */
import org.apache.spark
import scopt.OptionParser
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.linalg.distributed.{CoordinateMatrix, MatrixEntry, RowMatrix}
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{StringType, StructField, StructType}


object CosineSimilarity {

  def main(args: Array[String]): Unit = {
    val filename = "/home/pooja/Downloads/lastfm-matrix-germany.csv"
   // val threshold = 10
    val conf = new SparkConf().setAppName("SalesData").setMaster("local") //create a spark conf that stores configuration parameters that your Spark driver application will pass to SparkContext
    val sc = new SparkContext(conf) // A SparkContext represents the connection to a Spark cluster

    val row = sc.textFile(filename)//converts the file to an RDD
    println(row)
    println(row.count()) //prints the number of items in this RDD
    println(row.first()) //prints the first item this RDD


    val row1 = sc.textFile(filename).map{line => val values = line.split(' ')} //Return a new RDD by applying a function to all elements of this RDD
    println(row1.first())

    val rows = sc.textFile(filename).map { line =>
      val values = line.split(' ').map(_.toDouble)
      Vectors.dense(values)          // creating a dense format like [1.0, 0.0, 3.0]
    }.cache()
    println(rows.first())

    val mat = new RowMatrix(rows) //Represents a row-oriented distributed Matrix
    mat
      .rows
      .collect
      .foreach(println)

    // Compute similar columns perfectly, with brute force.
//    val exact = mat.columnSimilarities()  //Compute all cosine similarities between columns of this matrix
//    exact
//      .entries
//      .collect.map{ case MatrixEntry(i, j, u) => ((i, j), u) }
//      .foreach(println)

    val exact1 = mat.columnSimilarities()  //Compute all cosine similarities between columns of this matrix
    exact1
      .entries
      .collect.sortBy({ case MatrixEntry(i, j, u) => ((i, j), u) }).map{case MatrixEntry(i, j, u) => ((i, j), u)}
      .foreach(println)

    println("----------------------------------------------------")
    val exact2 = mat.columnSimilarities()//Compute all cosine similarities between columns of this matrix
    exact2
        .entries
        .collect({case MatrixEntry(120, j, u) => ((120, j), u)
                  case MatrixEntry(i, 120, u) => ((i, 120), u)}).sortBy(_._2).take(10)

        .foreach(println)

    println("----------------------------------------------------")

//    val transformedRDD = exact1.entries.map{case MatrixEntry(row: Long, col:Long, sim:Double) => Array(row,col,sim).mkString(",")}
//    val rdd2 = transformedRDD.map(a => Row(a))
//    rdd2.foreach(println)


    //exact.entries.sortBy()
   // val exactEntries = exact.entries
    // .map { case MatrixEntry(i, j, u) => ((i, j), u) }
   // println(exactEntries.first())



   // val approx = mat.columnSimilarities()
    //println("Estimated pairwise similarities are: " +approx.entries.collect.mkString(", "))

    //approx.entries.collect().foreach(println)
    //val transformedRDD = exact.entries.map{case MatrixEntry(row: Long, col:Long, exact:Double) => Array(row,col,exact).mkString(",")}
    //transformedRDD.saveAsTextFile("target/items")



  }
}