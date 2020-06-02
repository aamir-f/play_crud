package object db {

  import org.apache.log4j.{ Level, Logger }

  trait DBResult {
  }

  class DbLogger(level: Level, msg: String) {
    val logger: Logger = Logger.getLogger(this.getClass)
    logger.log(level, msg)
  }

  object SaveSuccess extends DbLogger(Level.TRACE, "Save success") with DBResult

  case class SaveSuccess(index: String) extends DbLogger(Level.TRACE, s"Save successful with object Id:$index") with DBResult


  case class SaveFailure(failureMsg: String) extends DbLogger(Level.ERROR, failureMsg) with DBResult

  object DeleteSuccess extends DbLogger(Level.TRACE, "Delete success") with DBResult

  case class DeleteFailure(failureMsg: String) extends DbLogger(Level.ERROR, failureMsg) with DBResult


  case class UpdateSuccess(index: String) extends DbLogger(Level.TRACE, "Update success") with DBResult

  case class UpdateFailure(failureMsg: String) extends DbLogger(Level.ERROR, failureMsg) with DBResult


}
