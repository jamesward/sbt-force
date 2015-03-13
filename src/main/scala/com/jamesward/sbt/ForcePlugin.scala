package com.jamesward.sbt

import sbt._

object ForcePlugin extends AutoPlugin {

  override def trigger = allRequirements

  object autoImport {
    lazy val Force = config("force")
    lazy val username = SettingKey[String]("username", "Salesforce Username")
    lazy val password = SettingKey[String]("password", "Salesforce Password")
    lazy val sandbox = SettingKey[Boolean]("sandbox", "Salesforce Sandbox")
    lazy val sourceDir = SettingKey[File]("source-dir", "Salesforce Source Directory")
    lazy val unpackagedComponents = SettingKey[Map[String, Seq[String]]]("unpackaged-components", "Unpackaged Metadata Components")
    lazy val deploy = TaskKey[Unit]("deploy", "Deploy the metadata")
    lazy val fetch = TaskKey[Set[File]]("fetch", "Fetch the metadata")
  }

  import autoImport._

  lazy val baseForceSettings: Seq[Def.Setting[_]] = Seq(
    username := username.value,
    password := password.value,
    sandbox := false,
    sourceDir := new File("salesforce"),
    unpackagedComponents := Map.empty[String, Seq[String]],
    deploy := ForceMetadataDeploy(username.value, password.value, sandbox.value, sourceDir.value),
    fetch := ForceMetadataFetch(username.value, password.value, sandbox.value, sourceDir.value, unpackagedComponents.value)
  )

  override lazy val projectSettings = inConfig(Force)(baseForceSettings)

}