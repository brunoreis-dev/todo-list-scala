name := """todo-list"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.12"

libraryDependencies += guice
libraryDependencies += filters
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.0" % Test
libraryDependencies += "org.postgresql" % "postgresql" % "42.7.1" % "runtime"
libraryDependencies += "com.typesafe.play" %% "play-slick" % "5.2.0"
libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "5.2.0"
libraryDependencies += "com.typesafe.slick" %% "slick" % "3.4.1"
libraryDependencies += "com.h2database" % "h2" % "2.2.224"
