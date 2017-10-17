lazy val forcePlugin = RootProject(file("..").getAbsoluteFile.toURI)

lazy val root = Project("test-project", file(".")).dependsOn(forcePlugin)
