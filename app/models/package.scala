import play.api.libs.json.{JsObject, JsValue, Json, Writes}

package object models {

  abstract class Resource {
    val `type`: String

    val id: String

    def toJson(relations: Seq[Resource] = Seq()): JsValue = Json.obj("type" -> this.`type`, "id" -> this.id)
  }

  object Resource {
    implicit val writes: Writes[Resource] = (r: Resource) => r.toJson(Seq())
  }

  def getResourceJson[T <: Resource](resource: T, relations: Seq[Resource])(implicit tjs: Writes[T]): JsValue = {
    if (relations.isEmpty) {
      Json.toJson(resource).as[JsObject]
    } else {
      Json.toJson(resource).as[JsObject] ++ Json.obj("relationships" -> getRelationshipsJson(relations))
    }
  }

  def getRelationshipsJson(relations: Seq[Resource]): JsValue = {
    relations.groupBy(_.getClass).map(g => {
      Json.obj(s"${g._2.head.`type`}s" -> Json.obj(
        "data" -> g._2.map(r => Json.obj("type" -> r.`type`, "id" -> r.id.toString))))
    }).foldLeft(Json.obj())((obj, a) => obj.deepMerge(a.as[JsObject]))
  }

  object ConfigTemplateAction extends Enumeration {
    type ConfigTemplateAction = Value
    val FETCH: Value = Value("fetch")
    val CHANGE: Value = Value("change")
    val BACKUP: Value = Value("backup")
    val RESTORE: Value = Value("restore")
  }

}
