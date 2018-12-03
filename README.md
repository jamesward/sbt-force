Salesforce sbt Plugin
=====================


Usage
-----

Add the plugin in your `project/plugins.sbt` file:

    addSbtPlugin("com.jamesward" % "sbt-force" % "0.0.3")


Enable the plugin and set its config in your `build.sbt` file:

    enablePlugins(ForcePlugin)
    
    username in Force := sys.env("SALESFORCE_USERNAME")
    
    password in Force := sys.env("SALESFORCE_PASSWORD")
    
    unpackagedComponents in Force := Map("ApexClass" -> Seq("*"))
    
    packagedComponents in Force := Seq("com.foo")

Fetch Metadata:

    SALESFORCE_USERNAME=foo@foo.test SALESFORCE_PASSWORD=password activator force:fetch

Deploy Metadata:

    SALESFORCE_USERNAME=foo@foo.test SALESFORCE_PASSWORD=password activator force:deploy


Developer Info
--------------

Test Project:

    cd test-project
    SALESFORCE_USERNAME=foo@foo.test SALESFORCE_PASSWORD=password activator
    force:fetch
    force:deploy

Release:

1. Replace version in `README.md`
1. Tag the release: `git tag v0.0.x`
1. Publish the release: `activator publish`
