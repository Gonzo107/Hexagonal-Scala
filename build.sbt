
ThisBuild / organization := "co.com.ceiba"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.12.8"

name := "Hexagonal-Scala-Ceiba"

lazy val root = Project(
  id = "hexagonal-scala",
  base = file("."))
  .dependsOn(
    dominio,
    aplicacion,
    infraestructura)
  .aggregate(
    dominio,
    aplicacion,
    infraestructura
  )
lazy val dominio = Project(
  id = "dominio",
  base = file("dominio"))

lazy val aplicacion = Project(
  id = "aplicacion",
  base = file("aplicacion"))
  .settings(
    libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.2" % Test,
    libraryDependencies += "org.mockito" % "mockito-core" % "2.28.2" % Test
  )
  .dependsOn(dominio % "test->test;compile")

lazy val infraestructura = Project(
  id = "infraestructura",
  base = file("infraestructura"))