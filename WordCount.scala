import spark.SparkContext
import SparkContext._

object WordCount extends App {
  //val sc = new SparkContext("local", "WordCount", args(0), List(args(1)))
  val sc = new SparkContext("local", "WordCount", "/home/jbaldrid/devel/spark", List("target/scala-2.9.2/spark-tutorial_2.9.2-0.1.jar"))
  val file = sc.textFile(args(0))
  val counts = file.flatMap(line => line.split(" "))
    .map(word => (word, 1))
    .reduceByKey(_ + _)
  counts.saveAsTextFile(args(1))
}
