package repositories

import javax.inject._
import reactivemongo.api.bson.collection.BSONCollection
import play.modules.reactivemongo.ReactiveMongoApi
import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.api.{Cursor, ReadPreference}
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import reactivemongo.api.commands.WriteResult

import models.Horse

@Singleton
class HorseRepository @Inject()(
  implicit executionContext: ExecutionContext,
  reactiveMongoApi: ReactiveMongoApi
) {
  def collection(user_token: String): Future[BSONCollection] = reactiveMongoApi.database.map(db => db.collection(user_token))

  def list(user_token: String, limit: Int = 100): Future[Seq[Horse]] = {
    collection(user_token).flatMap(
      _.find(BSONDocument(), Option.empty[Horse])
        .cursor[Horse](ReadPreference.Primary)
        .collect[Seq](limit, Cursor.FailOnError[Seq[Horse]]())
    )
  }

  def findHorse(user_token: String, id: BSONObjectID): Future[Option[Horse]] = {
    collection(user_token).flatMap(_.find(BSONDocument("_id" -> id), Option.empty[Horse]).one[Horse])
  }

  def createHorse(user_token: String, horse: Horse): Future[WriteResult] = {
    collection(user_token).flatMap(_.insert(ordered = false)
      .one(horse))
  }

  def updateHorse(user_token: String, id: BSONObjectID, horse: Horse):Future[WriteResult] = {

    collection(user_token).flatMap(
      _.update(ordered = false).one(BSONDocument("_id" -> id),
        horse)
    )
  }

  def deleteHorse(user_token: String, id: BSONObjectID):Future[WriteResult] = {
    collection(user_token).flatMap(
      _.delete().one(BSONDocument("_id" -> id), Some(1))
    )
  }


}