package controllers

import javax.inject._
import play.api.mvc._
import reactivemongo.bson.BSONObjectID
import play.api.libs.json.{Json, __}
import scala.util.{Failure, Success}
import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json.{JsValue, JsString}

import models.User
import repositories.UserRepository

@Singleton
class UserController @Inject()(
  implicit executionContext: ExecutionContext,
  val userRepository: UserRepository,
  val controllerComponents: ControllerComponents
) extends BaseController {

  def listUsers(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    userRepository.list().map {
      users => Ok(Json.toJson(users))
    }
  }

  def register(): Action[JsValue] = Action.async(controllerComponents.parsers.json) { implicit request => {
    println((request.body \ "username").as[String])
    request.body.validate[User].fold(
      _ => Future.successful(BadRequest("Cannot parse request body")),
      user =>
        userRepository.createUser(user).map {
          _ => Created
        }
    )
  }}

  def login() = Action.async(controllerComponents.parsers.json) { implicit request => {
    request.body.validate[User].fold(
      _ => Future.successful(BadRequest("Cannot parse request body")),
      user => {
        println(user)
        userRepository.login(user).map { userLogged => {
          val objectId = userLogged.get._id.get
          val token = JsString(objectId.stringify)
          Ok(Json.obj("username" -> userLogged.get.username, "token" -> token))
        }}
      }
    )
  }}
}