package com.datumbrain

/**
 * @author ${user.name}
 */

import com.google.cloud.language.v1.Document.Type
import com.google.cloud.language.v1.{AnalyzeEntitiesRequest, Document, EncodingType, LanguageServiceClient}

import scala.collection.JavaConversions._


object MainClass {

  def main(args: Array[String]): Unit = {
    val text = "My name is Saad. I work at ABC INC. My company is situated in Lahore. I owe Afzal 23$. My phone number is +923242225259."
    try {
      val language = LanguageServiceClient.create
      try {
        val doc = Document.newBuilder.setContent(text).setType(Type.PLAIN_TEXT).build
        val request = AnalyzeEntitiesRequest.newBuilder.setDocument(doc).setEncodingType(EncodingType.UTF16).build
        val response = language.analyzeEntities(request)
        // Print the response
        for (entity <- response.getEntitiesList) {
          for (em <- entity.getMentionsList) {
            if (em.getType.toString == "PROPER" || entity.getType.toString == "PHONE_NUMBER") {
              println(s"Entity: ${entity.getName}")
              println(s"Entity Type: ${entity.getType}")
            }
          }
        }
      } catch {
        case exc: Exception => println(s"Exception occured. Detaisl: ${exc.getMessage}")
      } finally if (language != null) language.close()
    } catch {
      case exc: Exception => println(s"Exception occured. Detaisl: ${exc.getMessage}")
    }
  }

}
