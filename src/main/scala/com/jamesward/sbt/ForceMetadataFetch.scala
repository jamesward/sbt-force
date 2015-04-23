package com.jamesward.sbt

import java.io.ByteArrayInputStream
import java.util.zip.ZipInputStream

import com.sforce.soap.metadata.{RetrieveRequest, PackageTypeMembers}
import sbt.{IO, File}

object ForceMetadataFetch {

  def apply(username: String, password: String, sandbox: Boolean, sourceDir: File, unpackagedComponents: Map[String, Seq[String]], packagedComponents: Seq[String]): Set[File] = {
    println("Fetching Unpackaged Metadata: " + unpackagedComponents)
    println("Fetching Packaged Metadata: " + packagedComponents)
    println("Storing in: " + sourceDir)

    val loginUrl = if (sandbox) { ForceMetadataUtil.SANDBOX_URL } else { ForceMetadataUtil.LOGIN_URL }

    val metadataConnection = ForceMetadataUtil.createMetadataConnection(username, password, loginUrl)

    val packageTypeMembersList = unpackagedComponents.map { case (name, value) =>
        val packageTypeMembers = new PackageTypeMembers()
        packageTypeMembers.setName(name)
        packageTypeMembers.setMembers(value.toArray)
        packageTypeMembers
    }

    val unpackaged = new com.sforce.soap.metadata.Package()

    unpackaged.setTypes(packageTypeMembersList.toArray)

    val retrieveRequest = new RetrieveRequest()
    retrieveRequest.setApiVersion(ForceMetadataUtil.API_VERSION)
    retrieveRequest.setUnpackaged(unpackaged)
    retrieveRequest.setPackageNames(packagedComponents.toArray)

    val asyncResult = metadataConnection.retrieve(retrieveRequest)

    ForceMetadataUtil.waitForResult(metadataConnection, asyncResult.getId, 60, 1000)

    val result = metadataConnection.checkRetrieveStatus(asyncResult.getId)

    result.getMessages.foreach(println)

    val byteArrayInputStream = new ByteArrayInputStream(result.getZipFile)

    val files = IO.unzipStream(byteArrayInputStream, sourceDir)

    byteArrayInputStream.close()

    files
  }

}
