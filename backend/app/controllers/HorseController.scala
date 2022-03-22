package controllers

import javax.inject._
import play.api.mvc._
import reactivemongo.bson.BSONObjectID
import play.api.libs.json.{Json, __}
import scala.util.{Failure, Success}
import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json.JsValue

import models.Horse
import repositories.HorseRepository

@Singleton
class HorseController @Inject()(
  implicit executionContext: ExecutionContext,
  val horseRepository: HorseRepository,
  val controllerComponents: ControllerComponents
) extends BaseController {

  def listHorses(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    horseRepository.list().map {
      horses => Ok(Json.toJson(horses))
    }
  }

  def findHorse(id: String): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    val objectIdTryResult = BSONObjectID.parse(id)
    objectIdTryResult match {
      case Success(objectId) => horseRepository.findHorse(objectId).map {
        horse => Ok(Json.toJson(horse))
      }
      case Failure(_) => Future.successful(BadRequest("Unknown horse id"))
    }
  }

  def createHorse(): Action[JsValue] = Action.async(controllerComponents.parsers.json) { implicit request => {
    request.body.validate[Horse].fold(
      _ => Future.successful(BadRequest("Cannot parse request body")),
      horse =>
        horseRepository.createHorse(horse).map {
          _ => Created(Json.toJson(horse))
        }
    )
  }}

  def updateHorse(id: String): Action[JsValue] = Action.async(controllerComponents.parsers.json) { implicit request => {
    request.body.validate[Horse].fold(
      _ => Future.successful(BadRequest("Cannot parse request body")),
      horse =>{
        val objectIdTryResult = BSONObjectID.parse(id)
        objectIdTryResult match {
          case Success(objectId) => horseRepository.updateHorse(objectId, horse).map {
            result => Ok(Json.toJson(result.ok))
          }
          case Failure(_) => Future.successful(BadRequest("Unknown horse id"))
        }
      }
    )
  }}

  def deleteHorse(id: String): Action[AnyContent]  = Action.async { implicit request => {
    val objectIdTryResult = BSONObjectID.parse(id)
    objectIdTryResult match {
      case Success(objectId) => horseRepository.deleteHorse(objectId).map {
        _ => NoContent
      }
      case Failure(_) => Future.successful(BadRequest("Unknown horse id"))
    }
  }}
}