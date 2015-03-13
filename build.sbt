sbtPlugin := true

organization := "com.jamesward"

name := "sbt-force"

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

javacOptions ++= Seq("-source", "1.6", "-target", "1.6")

scalaVersion := "2.10.4"

crossScalaVersions := Seq("2.10.5", "2.11.5")

libraryDependencies ++= Seq(
  "com.force.api" % "force-metadata-api" % "29.0.0",
  "com.force.api" % "force-partner-api" % "29.0.0",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test"
)

bintraySettings

com.typesafe.sbt.SbtGit.versionWithGit