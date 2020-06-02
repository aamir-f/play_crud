package utils

import play.api.http.Status._
import play.api.libs.json._
import play.api.mvc.Results.Status
import play.api.mvc.{ Result, Results }

object JSONUtils {

  final private val MIME_TYPE = "application/vnd.api+json"

  private def getStatus(error: JsValue, statusCode: Int): Result = {
    new Status(if (!isValidStatus(statusCode)) 500 else statusCode)(wrapError(error)).as(MIME_TYPE)
  }
  private def getStatus(error: String, statusCode: Int): Result = {
    new Status(if (!isValidStatus(statusCode)) 500 else statusCode)(wrapError(error)).as(MIME_TYPE)
  }

  private def isValidStatus(statusCode: Int) = statusCode >= 400 && statusCode <= 509

  def extractData(data: JsValue): Option[JsValue] = (data \ "data").toOption

  def extractErrors(error: Seq[(JsPath, Seq[JsonValidationError])]): JsValue = Json.toJson(error.flatMap(_._2).map(_.messages.mkString(";")).mkString(";"))

  def wrapData(data: JsValue): JsObject = Json.obj("data" -> data)

  def wrapIncluded(included: Option[JsValue]): Option[JsObject] = included.map(inc => Json.obj("included" -> inc))

  def wrapMeta(meta: Option[JsValue]): Option[JsObject] = meta.map(m => Json.obj("meta" -> m))

  def wrapError(error: JsValue): JsObject = Json.obj("errors" -> error)
  def wrapError(error: String): JsObject = Json.obj("error" -> error)

  // Failure
  def notFound(error: JsValue): Result = getStatus(error, NOT_FOUND).as(MIME_TYPE)
  def notFound(error: String): Result = getStatus(error, NOT_FOUND).as(MIME_TYPE)

  def internalServerError(error: JsValue): Result = getStatus(error, INTERNAL_SERVER_ERROR).as(MIME_TYPE)
  def internalServerError(error: String): Result = getStatus(error, INTERNAL_SERVER_ERROR).as(MIME_TYPE)

  def notImplemented(error: JsValue): Result = getStatus(error, NOT_IMPLEMENTED).as(MIME_TYPE)
  def notImplemented(error: String): Result = getStatus(error, NOT_IMPLEMENTED).as(MIME_TYPE)

  def forbidden(error: JsValue): Result = getStatus(error, FORBIDDEN).as(MIME_TYPE)
  def forbidden(error: String): Result = getStatus(error, FORBIDDEN).as(MIME_TYPE)

  def badRequest(error: JsValue): Result = getStatus(error, BAD_REQUEST).as(MIME_TYPE)
  def badRequest(error: String): Result = getStatus(error, BAD_REQUEST).as(MIME_TYPE)

  def conflict(error: JsValue): Result = getStatus(error, CONFLICT).as(MIME_TYPE)
  def conflict(error: String): Result = getStatus(error, CONFLICT).as(MIME_TYPE)

  def methodNotAllowed(error: JsValue): Result = getStatus(error, METHOD_NOT_ALLOWED).as(MIME_TYPE)
  def methodNotAllowed(error: String): Result = getStatus(error, METHOD_NOT_ALLOWED).as(MIME_TYPE)

  // Success
  def ok(data: JsValue, included: Option[JsValue] = None, meta: Option[JsValue] = None): Result = {
    val wrappedData = wrapData(data)
    val includedData = wrapIncluded(included).getOrElse(Json.obj())
    val wrappedMeta = wrapMeta(meta).getOrElse(Json.obj())
    Results.Ok(wrappedMeta ++ wrappedData ++ includedData).as(MIME_TYPE)
  }

  def noContent: Result = Results.NoContent

}

case class Meta(count: Long, total: Long, offset: Long, limit: Long)
object Meta {
  implicit val reads: Reads[Meta] = Json.reads[Meta]
  implicit val writes: Writes[Meta] = Json.writes[Meta]
}

