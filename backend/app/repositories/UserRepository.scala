package repositories

import javax.inject._
import reactivemongo.api.bson.collection.BSONCollection
import play.modules.reactivemongo.ReactiveMongoApi
import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.api.{Cursor, ReadPreference}
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import reactivemongo.api.commands.WriteResult

import models.User

@Singleton
class UserRepository @Inject()(
  implicit executionContext: ExecutionContext,
  reactiveMongoApi: ReactiveMongoApi
) {

  def collection: Future[BSONCollection] = reactiveMongoApi.database.map(db => db.collection("users"))

  def list(limit: Int = 100): Future[Seq[User]] = {

    collection.flatMap(
      _.find(BSONDocument(), Option.empty[User])
        .cursor[User](ReadPreference.Primary)
        .collect[Seq](limit, Cursor.FailOnError[Seq[User]]())
    )
  }

  def findUser(id: BSONObjectID): Future[Option[User]] = {
    collection.flatMap(_.find(BSONDocument("_id" -> id), Option.empty[User]).one[User])
  }

  def findUserByUsername(username: String): Future[Option[User]] = {
    collection.flatMap(_.find(BSONDocument("username" -> username), Option.empty[User]).one[User])
  }

  def addUser(user: User): Future[WriteResult] = {
    collection.flatMap(_.insert(ordered = false)
      .one(user))
  }
  
  def createUser(user: User): Future[WriteResult] = {
    findUserByUsername(user.username).map { existingUser =>
      if (existingUser.isEmpty) {        
        collection.flatMap(_.insert(ordered = false)
          .one(user))
      }
    }
  }
}