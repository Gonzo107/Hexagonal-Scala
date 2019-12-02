import sbt.Keys.libraryDependencies

ThisBuild / organization := "co.com.ceiba"
ThisBuild / version := "1.0.0"
ThisBuild / scalaVersion := "2.13.0"

name := "Hexagonal-Scala"

libraryDependencies ++= Seq(
  guice,
  "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % "it,Test",
  "org.mockito" % "mockito-core" % "2.28.2" % "it,Test",
  "com.typesafe.play" %% "play-slick" % "4.0.2",
  "com.typesafe.play" %% "play-slick-evolutions" % "4.0.2",
  "com.h2database" % "h2" % "1.4.192",
  "org.typelevel" %% "cats-core" % "2.0.0"
)

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .configs(IntegrationTest)
  .settings(Defaults.itSettings)

scalaSource in IntegrationTest := baseDirectory.value / "/it"
