
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
    apiRest)
  .aggregate(
    dominio,
    aplicacion,
    apiRest
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
  .dependsOn(driver, driven)
  .aggregate(driver, driven)

lazy val driver = Project(
  id = "driver",
  base = file("infraestructura/driver"))
  .dependsOn(apiRest)
  .aggregate(apiRest)

lazy val driven = Project(
  id = "driven",
  base = file("infraestructura/driven"))
  .dependsOn(persistenciaH2)
  .aggregate(persistenciaH2)

lazy val persistenciaH2 = Project(
  id = "persistencia-h2",
  base = file("infraestructura/driven/persistencia-h2"))

lazy val apiRest = Project(
  id = "api-rest",
  base = file("infraestructura/driver/api-rest"))
  .enablePlugins(PlayService)
  .dependsOn(aplicacion)