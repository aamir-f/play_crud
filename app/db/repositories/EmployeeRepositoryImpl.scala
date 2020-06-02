package db.repositories

import com.google.inject.{Inject, Singleton}
import db._
import db.tables.EmployeeTable
import models.Employee
import play.api.db.slick.DatabaseConfigProvider
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

@Singleton
class EmployeeRepositoryImpl @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)(implicit val executionContext: ExecutionContext)
  extends EmployeeRepository  with EmployeeTable {

  override protected val dbConfig: DatabaseConfig[JdbcProfile] = dbConfigProvider.get[JdbcProfile]

  import profile.api._

  override def getAllRecords: Future[Seq[Employee]] = db.run(employee.result)

  override def getRecord(id: String): Future[Option[Employee]] = {
    db.run(employee.filter(_.id === id).result.headOption)
  }

  override def saveRecord(record: Employee): Future[DBResult] = {
    db.run(DBIO.seq(employee += record).asTry).map {
      case Success(code) => SaveSuccess(code.toString)
      case Failure(exception) => SaveFailure(exception.getMessage)
    }
  }

  override def deleteRecord(id: String): Future[DBResult] = {
    db.run(DBIO.seq(employee.filter(_.id === id).delete).asTry).map {
      case Success(_) => DeleteSuccess
      case Failure(exception) => DeleteFailure(exception.getMessage)
    }
  }

  override def updateRecord(record: Employee): Future[DBResult] = {
    db.run(employee.filter(_.id === record.id).update(record).asTry).map {
      case Success(code) => UpdateSuccess(code.toString)
      case Failure(exception) => UpdateFailure(exception.getMessage)
    }
  }

}
