lazy val root = Project("test-project", file(".")) dependsOn(forcePlugin)
 
lazy val forcePlugin = file("..").getAbsoluteFile.toURI
