name := "test-project"

version := "1.0"
 
enablePlugins(ForcePlugin)

username in Force := sys.env("SALESFORCE_USERNAME")

password in Force := sys.env("SALESFORCE_PASSWORD")

unpackagedComponents in Force := Map("ApexClass" -> Seq("*"))