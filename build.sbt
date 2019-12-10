import sbt.Keys.libraryDependencies

ThisBuild / organization := "co.com.ceiba"
ThisBuild / version := "1.0.0"
ThisBuild / scalaVersion := "2.13.0"

name := "Hexagonal-Scala"

lazy val IntegrationTests = config("it") extend (Test)

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .configs(IntegrationTests)
  .settings(
    inConfig(IntegrationTests)(Defaults.testSettings),
    libraryDependencies ++= Seq(
      guice,
      "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % "it,test",
      "org.mockito" % "mockito-core" % "2.28.2" % "it,test"
    )
  )
  .dependsOn(domain, application, infrastructure)

scalaSource in IntegrationTests := baseDirectory.value / "/it"

lazy val domain = (project in file("app/domain"))
  .settings(
    libraryDependencies ++= Seq("org.typelevel" %% "cats-core" % "2.0.0")
  )

lazy val application = (project in file("app/application"))
  .enablePlugins(PlayService)
  .dependsOn(domain)

lazy val infrastructure = (project in file("app/infrastructure"))
  .enablePlugins(PlayService)
  .settings(
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play-slick" % "4.0.2",
      "com.typesafe.play" %% "play-slick-evolutions" % "4.0.2",
      "com.h2database" % "h2" % "1.4.192")
  )
  .dependsOn(domain)



