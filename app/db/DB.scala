package db

import play.api.db.slick.HasDatabaseConfig
import slick.jdbc.JdbcProfile

trait DB extends HasDatabaseConfig[JdbcProfile]
