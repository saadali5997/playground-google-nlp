package com.datumbrain

import java.io.FileInputStream
import java.nio.file.Paths

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.storage.{BlobId, StorageOptions}

/**
 * @author ${user.name}
 */
object MainClass {

  private val credentialFileAddress: String = "/home/saad/learning/google-cloud/my-first-project-995398db50b9.json"
  private val credentials = GoogleCredentials.fromStream(new FileInputStream(s"$credentialFileAddress"))
  GoogleCredentials.
  private val storage = StorageOptions.newBuilder.setCredentials(credentials).setProjectId("adroit-minutia-250910").build.getService

  private val bucket = storage.get("test-bucket-fahad")

  def downloadFile() = {
    val blobs = bucket.list().iterateAll()
    val path = Paths.get("/home/saad/learning/google-cloud/saad ali.pdf")
    blobs.forEach { blob =>
      if (blob.getBlobId.getName.endsWith("saad ali.pdf")) {
        val blob = storage.get(BlobId.of("test-bucket-fahad", "saad ali.pdf"))
        blob.downloadTo(path)
      }
    }

  }

  def main(args: Array[String]) {
    printBucketContent()
    downloadFile()
//    uploadFile("/home/saad/Desktop/Saad Ali.pdf")
//    printBucketContent()
  }

  def printBucketContent(): Unit = {
    println("******************************************************")
    println("**************** PRINTING BUCKET CONTENT *************")
    println("******************************************************")

    println(s"Bucket generated Id => ${bucket.getGeneratedId}")

    val blobs = bucket.list()
    blobs.iterateAll().forEach { blob =>
      println(blob.getBlobId.getName)
    }
  }

  def uploadFile(filePath: String) = {
    val fileToUpload = new FileInputStream(filePath)
    val fileName = filePath.split("/").last.toLowerCase
    val blob = bucket.create(fileName, fileToUpload)
  }

}
