package db

import models.Resource

import scala.concurrent.{ExecutionContext, Future}

trait BaseRepository[T <: Resource] extends DB {

  implicit val executionContext: ExecutionContext

  def getAllRecords: Future[Seq[T]]

  def getRecord(id: String): Future[Option[T]]

  def saveRecord(record: T): Future[DBResult]

  def deleteRecord(id: String): Future[DBResult]

  def updateRecord(record: T): Future[DBResult]

}