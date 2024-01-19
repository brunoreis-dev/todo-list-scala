package models
import play.api.libs.json._

case class Todo(id: Option[Long], description: String, completed: Boolean)

object Todo {
  implicit val todoFormat = Json.format[Todo]
}
