sbtPlugin := true

organization := "com.jamesward"

name := "sbt-force"

licenses += "MIT" -> url("http://opensource.org/licenses/MIT")

javacOptions ++= Seq("-source", "1.6", "-target", "1.6")

scalaVersion := "2.12.3"

libraryDependencies ++= Seq(
  "com.force.api" % "force-metadata-api" % "29.0.0",
  "com.force.api" % "force-partner-api" % "29.0.0",
  "org.scalatest" %% "scalatest" % "3.0.4" % "test"
)

publishMavenStyle := false

bintrayRepository := "sbt-plugins"

bintrayOrganization in bintray := None

enablePlugins(GitVersioning)

git.useGitDescribe := true
