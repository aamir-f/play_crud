package db.tables


import db.DB
import models.Employee

trait EmployeeTable {
  this: DB =>

  import dbConfig.profile.api._

  protected class EmployeeTableStructure(tag: Tag) extends Table[Employee](tag, Some("test"),"Employee") {
    def id: Rep[String] = column[String]("id", O.PrimaryKey)

    def name: Rep[String] = column[String]("name")

    def age: Rep[Long] = column[Long]("age")

    def address: Rep[String] = column[String]("address")

    def serviceNowUserId: Rep[String] = column[String]("service_now_user_id", O.Unique)

    def * = (id, name, age, address) <> ({ data =>
    {
      new Employee(data._1, data._2, data._3, data._4)
    }
    }, {
      r: Employee =>
      {
        Some(r.id, r.name, r.age, r.address)
      }
    })
  }

  protected val employee = TableQuery[EmployeeTableStructure]

}