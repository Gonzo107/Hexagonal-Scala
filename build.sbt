
ThisBuild / organization := "co.com.ceiba"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.12.8"

name := "Hexagonal-Scala"

lazy val bootstrap = Project(
  id = "hexagonal-scala",
  base = file("."))
  .enablePlugins(PlayScala)
  .settings(
    libraryDependencies += guice
  )
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
    libraryDependencies += guice,
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
  .enablePlugins(PlayService)
  .settings(
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play-slick" % "4.0.0",
      "com.typesafe.play" %% "play-slick-evolutions" % "4.0.0",
      "com.h2database" % "h2" % "1.4.192",
      guice
    )
  )
  .dependsOn(dominio)

lazy val apiRest = Project(
  id = "api-rest",
  base = file("infraestructura/driver/api-rest"))
  .enablePlugins(PlayService)
  .dependsOn(aplicacion)