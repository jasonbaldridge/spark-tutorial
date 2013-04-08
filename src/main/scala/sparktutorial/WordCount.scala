package sparktutorial

import spark.SparkContext
import SparkContext._

object WordCount extends App {

  val Array(sparkHost, sparkLocation, jar, inputfile, outputDir) = args
  val sc = new SparkContext(sparkHost, "WordCount", sparkLocation, Array(jar))
  //val sc = new SparkContext("local", "WordCount", "/home/jbaldrid/devel/spark", List("target/scala-2.9.2/spark-tutorial_2.9.2-0.1.jar"))
  val file = sc.textFile(inputfile)
  val counts = file.flatMap(line => line.split(" "))
    .map(word => (word, 1))
    .reduceByKey(_ + _)
  counts.saveAsTextFile(outputDir)
}
