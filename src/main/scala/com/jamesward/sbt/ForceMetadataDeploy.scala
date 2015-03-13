package com.jamesward.sbt

import java.net.URL

import com.sforce.soap.metadata.{DeployStatus, DeployOptions}
import sbt.{Path, File, IO}

object ForceMetadataDeploy {

  def apply(username: String, password: String, sandbox: Boolean, sourceDir: File): Unit = {
    println("Deploying all metadata in: " + sourceDir)

    IO.withTemporaryFile("deploy", ".zip") { zipFile =>

      val loginUrl = if (sandbox) { ForceMetadataUtil.SANDBOX_URL } else { ForceMetadataUtil.LOGIN_URL }

      val files = Path.allSubpaths(sourceDir)

      IO.zip(files, zipFile)

      val zipBytes = IO.readBytes(zipFile)

      val metadataConnection = ForceMetadataUtil.createMetadataConnection(username, password, loginUrl)

      val deployOptions = new DeployOptions()

      val asyncResult = metadataConnection.deploy(zipBytes, deployOptions)

      ForceMetadataUtil.waitForResult(metadataConnection, asyncResult.getId, 60, 1000)

      val deployResult = metadataConnection.checkDeployStatus(asyncResult.getId, true)

      val serviceUrl = new URL(metadataConnection.getConfig.getServiceEndpoint)

      val deployStatusUrl = "https://" + serviceUrl.getHost + "/changemgmt/monitorDeploymentsDetails.apexp?retURL=/changemgmt/monitorDeployment.apexp&asyncId=" + asyncResult.getId

      deployResult.getStatus match {
        case DeployStatus.Succeeded =>
          println("Deploy Succeeded! Details: " + deployStatusUrl)
        case _ =>
          throw new Exception("Deploy Failed! Details: " + deployStatusUrl)
      }

    }

  }

}
