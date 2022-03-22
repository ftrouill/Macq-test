package controllers

import javax.inject._
import play.api.mvc._
import reactivemongo.bson.BSONObjectID
import play.api.libs.json.{Json, __}
import scala.util.{Failure, Success}
import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json.JsValue

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

  def findUser(id: String): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    val objectIdTryResult = BSONObjectID.parse(id)
    objectIdTryResult match {
      case Success(objectId) => userRepository.findUser(objectId).map {
        user => Ok(Json.toJson(user))
      }
      case Failure(_) => Future.successful(BadRequest("Unknown user id"))
    }
  }

  /*def countUser(username: String): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] => {
    Ok(f"${userRepository.countUserByUsername(username)}")
    }
   /* username match {
      case Success(username) => userRepository.findUserByUsername(username).map {
        user => Ok("1")
      }
      case Failure(_) => Ok("0")
    }*/
  }*/
  def createUser(): Action[JsValue] = Action.async(controllerComponents.parsers.json) { implicit request => {
    request.body.validate[Horse].fold(
      _ => Future.successful(BadRequest("Cannot parse request body")),
      horse =>
        horseRepository.createHorse(horse).map {
          _ => Created(Json.toJson(horse))
        }
    )
  }}

  /*def register(): Action[JsValue] = Action.async(controllerComponents.parsers.json) { implicit request => {
    request.body.validate[User].fold(
      _ => Future.successful(BadRequest("Cannot parse request body")), 
      user => userRepository.findUserByUsername(user.username).map { existingUser => {
        if (existingUser.isEmpty) {
          userRepository.createUser(user).map {
            _ => Created(Json.toJson(user))
          }
        } else {
          Future.successful(BadRequest("Username already taken"))
        }
      }
    })
  }}*/

  /*def login(): Action[JsValue] = Action.async(controllerComponents.parsers.json) { implicit request => {
    Ok("ok")
    /*request.body.validate[User].fold(
      _ => Future.successful(BadRequest("Cannot parse request body")),
      user =>{
        val objectIdTryResult = BSONObjectID.parse(id)
        objectIdTryResult match {
          case Success(objectId) => userRepository.updateHorse(objectId, horse).map {
            result => Ok(Json.toJson(result.ok))
          }
          case Failure(_) => Future.successful(BadRequest("Unknown horse id"))
        }
      }
    )*/
  }}*/
}