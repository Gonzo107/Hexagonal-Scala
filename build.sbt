import sbt.Keys.libraryDependencies

ThisBuild / organization := "co.com.ceiba"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.12.8"

name := "Hexagonal-Scala"

libraryDependencies ++= Seq(
  guice,
  "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.2" % Test,
  "org.mockito" % "mockito-core" % "2.28.2" % Test,
  "com.typesafe.play" %% "play-slick" % "4.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "4.0.0",
  "com.h2database" % "h2" % "1.4.192",
  "net.codingwell" %% "scala-guice" % "4.2.3"
)

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)

