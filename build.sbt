
ThisBuild / organization := "co.com.ceiba"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.0"

name := "Hexagonal-Scala-Ceiba"

lazy val dominio = Project(
  id = "dominio",
  base = file("dominio"))

lazy val aplicacion = Project(
  id = "aplicacion",
  base = file("aplicacion"))

lazy val infraestructura = Project(
  id = "infraestructura",
  base = file("infraestructura"))