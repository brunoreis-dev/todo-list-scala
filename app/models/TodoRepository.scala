package models

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.Await
import scala.concurrent.duration.Duration

import scala.concurrent.{ Future, ExecutionContext }

/**
 * A repository for people.
 *
 * @param dbConfigProvider The Play db config provider. Play will inject this for you.
 */
@Singleton
class TodoRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig._
  import profile.api._


  val query = TableQuery[TodosTable]

  def create(description: String, completed: Boolean): Future[Todo] = db.run {
    (query.map(t => (t.description, t.completed))
      returning query.map(_.id)
      into ((descriptionCompleted, id) => Todo(id, descriptionCompleted._1, descriptionCompleted._2))
    ) += (description, completed)
  }
  def list(): Future[Seq[Todo]] = db.run {
    query.result
  }
  def delete(id: Long): Future[Int] = db.run {
    query.filter(_.id === id).delete
  }
  def check(id: Long): Future[Int] = db.run {
    val foundTodo = Await.result(db.run(query.result.headOption), Duration.Inf)
    val completed = foundTodo.map(_.completed).getOrElse(false)

    val q = for { c <- query if c.id === id } yield c.completed
    q.update(!completed)
  }
}
