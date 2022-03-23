package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json.{Json, __}
import scala.concurrent.ExecutionContext

@Singleton
class AppController @Inject()(
  implicit executionContext: ExecutionContext,
  val controllerComponents: ControllerComponents
) extends BaseController {

  def index(): Action[AnyContent] = Action {
    Ok("Back is up")
  }
}