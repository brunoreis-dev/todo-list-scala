package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.db.Database
import models._
import play.api.libs.json.Json
import play.api.libs.json._

import scala.concurrent.{ExecutionContext, Future}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class TodoController @Inject()(val controllerComponents: ControllerComponents, repo: TodoRepository)(implicit ec: ExecutionContext) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */

  def index() = Action.async { implicit request: Request[AnyContent] =>
    repo.list().map { todos =>
      Ok(Json.toJson(todos))
    }
  }

  def create() = Action.async { implicit request: Request[AnyContent] =>
    val requestBodyJson: Option[JsValue] = request.body.asJson
    requestBodyJson match {
      case Some(json) =>
        val description = (json \ "description").as[String]
        val completed = (json \ "completed").as[Boolean]  

        repo.create(description, completed).map { _ =>
          Ok(Json.toJson("Success"))
        }

      case None =>
        Future.successful(BadRequest(Json.toJson("Erro: Corpo da solicitação não é um JSON válido")))
    }
  }

  def delete() = Action { implicit request: Request[AnyContent] =>
    Ok("Todo Deleted")
  }

  def check() = Action { implicit request: Request[AnyContent] =>
    Ok("Todo done")
  }
}
