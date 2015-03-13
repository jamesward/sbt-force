import bintray.Keys._

sbtPlugin := true

organization := "com.jamesward"

name := "sbt-force"

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

javacOptions ++= Seq("-source", "1.6", "-target", "1.6")

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "com.force.api" % "force-metadata-api" % "29.0.0",
  "com.force.api" % "force-partner-api" % "29.0.0",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test"
)

publishMavenStyle := false

bintraySettings

repository in bintray := "sbt-plugins"

bintrayOrganization in bintray := None

com.typesafe.sbt.SbtGit.versionWithGit