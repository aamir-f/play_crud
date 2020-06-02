package modules

import com.google.inject.AbstractModule
import db.repositories.{EmployeeRepository, EmployeeRepositoryImpl}

class DBModules extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[EmployeeRepository]).to(classOf[EmployeeRepositoryImpl])
  }
}
