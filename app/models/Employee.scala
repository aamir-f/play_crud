package models

import play.api.libs.json._
case class Employee(id: String, name: String, age: Long, address: String) extends Resource {
  override val `type`: String = "employee"
  override def toJson(relations: Seq[Resource]): JsValue = getResourceJson(this, relations)
}

object Employee {
  implicit val reads: Reads[Employee] = (a: JsValue) => {
    val id = (a \ "id").as[String]
    val name = (a \ "name").as[String]
    val age = (a \ "age").as[Long]
    val address = (a \ "address").as[String]
    JsSuccess(Employee(id, name, age, address))
  }
  implicit val writes: Writes[Employee] = (a: Employee) => Json.obj("id" -> a.id, "name" -> a.name, "age" -> a.age, "address" -> a.address)
}
