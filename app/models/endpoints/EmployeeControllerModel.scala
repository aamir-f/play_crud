package models.endpoints

import models.Employee
import play.api.libs.json.{Json, Reads, Writes}

case class CreateEmployeeRequest(data: CreateEmployeeRequestInput) {
  def generateEmployee: Employee = Employee(data.id, data.name, data.age, data.address)
}

object CreateEmployeeRequest {
  implicit val reads: Reads[CreateEmployeeRequest] = Json.reads[CreateEmployeeRequest]
  implicit val writes: Writes[CreateEmployeeRequest] = Json.writes[CreateEmployeeRequest]
}

case class CreateEmployeeRequestInput(id: String, name: String, age: Long, address: String) {}

object CreateEmployeeRequestInput {
  implicit val reads: Reads[CreateEmployeeRequestInput] = Json.reads[CreateEmployeeRequestInput]
  implicit val writes: Writes[CreateEmployeeRequestInput] = Json.writes[CreateEmployeeRequestInput]
}

case class UpdateEmployeeRequest(data: UpdateEmployeeRequestInput)

object UpdateEmployeeRequest {
  implicit val reads: Reads[UpdateEmployeeRequest] = Json.reads[UpdateEmployeeRequest]
  implicit val writes: Writes[UpdateEmployeeRequest] = Json.writes[UpdateEmployeeRequest]
}

case class UpdateEmployeeRequestInput(id: String, name: String, age: Long, address: String)

object UpdateEmployeeRequestInput {
  implicit val reads: Reads[UpdateEmployeeRequestInput] = Json.reads[UpdateEmployeeRequestInput]
  implicit val writes: Writes[UpdateEmployeeRequestInput] = Json.writes[UpdateEmployeeRequestInput]
}
