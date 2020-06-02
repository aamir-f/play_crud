package controllers

import com.google.inject.Inject
import db.repositories.EmployeeRepository
import db._
import models.Employee
import models.endpoints.{CreateEmployeeRequest, UpdateEmployeeRequest}
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import play.api.mvc._
import utils.JSONUtils._

import scala.concurrent.{ExecutionContext, Future}

class EmployeeController@Inject() (val controllerComponents: ControllerComponents,
                                   employeeRepository: EmployeeRepository)(implicit executionContext: ExecutionContext)
  extends BaseController {

  def getEmployees: Action[AnyContent] = Action.async {
    employeeRepository.getAllRecords.map(employees => ok(Json.toJson(employees)))
  }


  def createEmployee: Action[JsValue] = Action.async(parse.tolerantJson) { implicit request: Request[JsValue] =>
    request.body.validate[CreateEmployeeRequest] match {
      case JsSuccess(employeeRequest, _) =>
        val employee = employeeRequest.generateEmployee
        employeeRepository.saveRecord(employee).map {
          case SaveSuccess(_) => ok(Json.toJson(employee))
          case SaveFailure(failureMsg) => internalServerError(s"Failed to save employee: $employee to database due to: $failureMsg")
        }
      case JsError(errors) => Future(badRequest(errors.mkString(",")))
    }
  }

  def deleteEmployee(id: String): Action[AnyContent] = Action.async {
    employeeRepository.deleteRecord(id).map {
      case DeleteSuccess => noContent
      case DeleteFailure(failureMsg) => internalServerError(s"Failed to delete tag: $id to database due to: $failureMsg")
    }
  }
  def updateEmployee(): Action[JsValue] = Action.async(parse.tolerantJson) { implicit request: Request[JsValue] =>
    request.body.validate[UpdateEmployeeRequest] match {
      case JsSuccess(employeeRequest, _) =>
        val employee = Employee(employeeRequest.data.id, employeeRequest.data.name, employeeRequest.data.age, employeeRequest.data.address)
        employeeRepository.updateRecord(employee).map {
          case UpdateSuccess(_) => noContent
          case UpdateFailure(failureMsg) => internalServerError(s"Failed to update tag: $employee to database due to: $failureMsg")
        }
      case JsError(errors) => Future(badRequest(errors.mkString(",")))
    }
  }




}