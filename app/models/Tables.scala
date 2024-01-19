package models

import slick.jdbc.PostgresProfile.api._
import models.Todo._

class TodosTable(tag: Tag) extends Table[Todo](tag, "todos") {
  def id = column[Option[Long]]("id", O.PrimaryKey, O.AutoInc)
  def description = column[String]("description")
  def completed = column[Boolean]("completed")

  def * = (id, description, completed) <> ((Todo.apply _).tupled, Todo.unapply)
}
