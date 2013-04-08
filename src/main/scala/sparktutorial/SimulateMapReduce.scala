
object SimulateMapReduce {

  def main (args: Array[String]) {
    val lines = io.Source.fromFile(args(0)).getLines.toSeq
    
    report("MutableMap", mutableMapCount(lines))
    report("MapByLine", mapByLineCount(lines))
    report("SeqTupleByLine", seqTupleByLineCount(lines))

  }

  def report(description: String, counts: Map[String,Int]) {
    println
    println("Counting using " + description)
    println("Count of the: " + counts("the"))
    println("Count of a: " + counts("a"))
    println
  }

  def mutableMapCount(lines: Seq[String]) = {
    val counts = collection.mutable.HashMap[String,Int]().withDefaultValue(0)
    lines.foreach { line => {
      val tokens = line.split("\\s+")
      tokens.foreach(tok => counts(tok) += 1)
    }}
    counts.toMap
  }

  def mapByLineCount(lines: Seq[String]) = {
    val perLineCountMaps: Seq[Map[String,Int]] = lines.map { line => {
      val tokens = line.split("\\s+")
      val groupedTokens = tokens.groupBy(x=>x)
      val countedTokens = groupedTokens.mapValues(tokenList=>tokenList.length)
      countedTokens.toMap
    }}

    val emptyMap = Map[String,Int]().withDefaultValue(0)
    val counts = perLineCountMaps.foldLeft(emptyMap) {
      (accum, lineCounts) => 
	val updates = for ((k,v) <- lineCounts) yield (k -> (v + accum(k)))
	accum ++ updates
    }
    counts
  }

  def seqTupleByLineCount(lines: Seq[String]) = {
    val countEmissions: Seq[(String,Int)] = 
      lines
	.flatMap(line => line.split("\\s+"))
	.map(token => (token,1))

    val groupedEmissions: Map[String,Seq[Int]] =
      countEmissions
	.groupBy(wordOneTuple => wordOneTuple._1)
	.mapValues(seqWordsAndOnes => seqWordsAndOnes.unzip._2)

    val counts = for ((word,emissions) <- groupedEmissions) yield {
      val countOfWord = emissions.reduce(_ + _)
      (word -> countOfWord)
    }

    counts.withDefaultValue(0)
  }

}
