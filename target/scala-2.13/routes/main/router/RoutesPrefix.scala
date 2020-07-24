// @GENERATOR:play-routes-compiler
// @SOURCE:D:/code/play_crud/conf/routes
// @DATE:Thu Jul 16 23:12:33 IST 2020


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
