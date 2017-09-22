name := "CosineSim"

version := "1.0"

scalaVersion := "2.11.8"

val commonSettings = Seq(
  organization := "com.hm",
  version := "0.1",
  scalaVersion := "2.11.8",
  scalacOptions := Seq("-encoding", "utf8")
)
libraryDependencies += "org.slf4j" % "slf4j-log4j12" % "1.7.12"
libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "2.1.0"
libraryDependencies += "org.apache.spark" % "spark-sql_2.11" % "2.1.0"
libraryDependencies += "org.apache.spark" % "spark-mllib_2.11" % "2.1.0"
libraryDependencies += "com.github.scopt" %% "scopt" % "3.5.0"