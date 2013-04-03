import AssemblyKeys._ 

assemblySettings

name := "Spark Tutorial"

version := "0.1"

scalaVersion := "2.9.2"

libraryDependencies += "org.spark-project" %% "spark-core" % "0.7.0"

resolvers ++= Seq(
  "Akka Repository" at "http://repo.akka.io/releases/",
  "Spray Repository" at "http://repo.spray.cc/")

mergeStrategy in assembly := { case _ => MergeStrategy.first }