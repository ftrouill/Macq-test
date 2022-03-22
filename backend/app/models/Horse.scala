package models

import play.api.libs.json.{Format, Json}
import reactivemongo.play.json._
import reactivemongo.bson.BSONObjectID
import reactivemongo.bson._
import play.api.libs.json.JodaWrites._
import play.api.libs.json.JodaReads._

case class Horse(
  _id: Option[BSONObjectID],
  name: String,
  color: String,
  speed: String,
  breed: String,
  image: String
)

object Horse{
  implicit val format: Format[Horse] = Json.format[Horse]
  implicit object HorseBSONReader extends BSONDocumentReader[Horse] {
    def read(doc: BSONDocument): Horse = {
      Horse(
        doc.getAs[BSONObjectID]("_id"),
        doc.getAs[String]("name").get,
        doc.getAs[String]("color").get,
        doc.getAs[String]("speed").get,
        doc.getAs[String]("breed").get,
        doc.getAs[String]("image").get
      )
    }
  }

  implicit object HorseBSONWriter extends BSONDocumentWriter[Horse] {
    def write(horse: Horse): BSONDocument = {
      BSONDocument(
        "_id" -> horse._id,
        "name" -> horse.name,
        "color" -> horse.color,
        "speed" -> horse.speed,
        "breed" -> horse.breed,
        "image" -> horse.image
      )
    }
  }
}
