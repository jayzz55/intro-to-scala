package fundamentals.level04

/**
  * The exercises here are adapted from: http://www.cis.upenn.edu/~cis194/spring13/hw/02-ADTs.pdf
  *
  * Let's finish off this course by building a CLI program!
  * We will build a program to parse a log file (containing Info, Warn and Error messages) and print out errors over a severity level.
  *
  * Finish each exercise and we will head over to `Main.scala` to hook it all up with the CLI.
  */
object LogParser {

  /**
    * Here is how a log file may look.
    *
    * The `|` at the start of each line can be ignored. This is how Scala represents a multi-line String.
    */
  val logFile: String =
    """|I,147,mice in the air
       |W,149,could've been bad
       |E,5,158,some strange error
       |E,2,148,istereadea""".stripMargin

  /**
    * Let's define an ADT to represent all possible log messages.
    *
    * Hint: Add `sealed` in front of the `trait`s below to make sure you can only extend them from within this file.
    */

  /**
    * Start with all the possible log levels
    * - Info
    * - Warning
    * - Error with (severity: Int)
    */

  sealed trait LogLevel

  case object Info extends LogLevel
  case object Warning extends LogLevel
  case class Error(severity: Int) extends LogLevel

  /**
    * Now create an ADT for `LogMessage`, where `LogMessage` can be one of two possibilities:
    * - KnownLog with (logLevel, timestamp, message)
    * - UnknownLog with (message)
    */
  type Timestamp = Int

  sealed trait LogMessage

  case class KnownLog(logLevel: LogLevel, timeStamp: Timestamp, message: String) extends LogMessage
  case class UnknownLog(message: String) extends LogMessage

 /**
   * - Once you have defined your data types:
   * 1. Remove `import Types._` from [LogParserTest.scala](src/test/scala/fundamentals/level04/LogParserTest.scala)
   * 2. Comment out the contents of src/test/scala/fundamentals/level04/Types.scala
   */

  /**
    * Define a function to parse an individual log message.
    *
    * scala> parseLog("I,147,mice in the air")
    * = KnownLog(Info, 147, "mice in the air")
    *
    * scala> parseLog("E,2,148,weird")
    * = KnownLog(Error(2), 148, "weird")
    *
    * scala> parseLog("X blblbaaaaa")
    * = UnknownLog("X blblbaaaaa")
    **/
  def parseLog(str: String): LogMessage =
    str.split(",") match {
      case Array("E", severityStr, timestampStr, message) => KnownLog(Error(severityStr.toInt), timestampStr.toInt, message)
      case Array("I", timestampStr, message) => KnownLog(Info, timestampStr.toInt, message)
      case Array("W", timestampStr, message) => KnownLog(Warning, timestampStr.toInt, message)
      case Array(str) => UnknownLog(str)
    }

  /**
    * scala> parseLogFile("I,147,mice in the air\nX blblbaaaaa")
    * = List(KnownLog(Info, 147, "mice in the air"), UnknownLog("X blblbaaaaa"))
    *
    * Hint: Use `parseLog`
    * Hint: Convert an Array to a List with .toList
    * What if we get an empty line from the fileContent?
    */
  def parseLogFile(fileContent: String): List[LogMessage] =
    fileContent match {
      case "" => List()
      case _ => fileContent.split("\n").toList.map(str => parseLog(str))
    }

  /**
    * Define a function that returns only logs that are unknown
    *
    * scala> getUnknowns(List(KnownLog(Info, 147, "mice in the air"), UnknownLog("blblbaaaaa")))
    * = List(UnknownLog("blblbaaaaa"))
    **/
  def getUnknowns(logs: List[LogMessage]): List[LogMessage] =
    logs.collect {
      case log: UnknownLog => log
    }
 
  /**
    * Write a function to convert a `LogMessage` to a readable `String`.
    *
    * scala> showLogMessage(KnownLog(Info, 147, "mice in the air"))
    * = "Info (147) mice in the air"
    *
    * scala> showLogMessage(KnownLog(Error(2), 147, "weird"))
    * = "Error 2 (147) weird"
    *
    * scala> showLogMessage(UnknownLog("message"))
    * = "Unknown log: message"
    *
    * Hint: Pattern match and use string interpolation
    **/
  def showLogMessage(log: LogMessage): String =
    log match {
      case KnownLog(Error(severity), timestamp, message) => s"Error ${severity} (${timestamp}) ${message}"
      case KnownLog(logLevel, timestamp, message) => s"${logLevel} (${timestamp}) ${message}"
      case UnknownLog(message) => s"Unknown log: ${message}"
    }

  /**
    * Use `showLogMessage` on error logs with severity greater than the given `severity`.
    *
    * scala> showErrorsOverSeverity(logFile, 2)
    * = List("Error 5 (158) some strange error")
    *
    * Hint: Use `parseLogFile` and `showLogMessage`
    **/
  def showErrorsOverSeverity(fileContent: String, severity: Int): List[String] =
    parseLogFile(fileContent).collect {
      case KnownLog(Error(svr), timestamp, message) if svr > severity => showLogMessage(KnownLog(Error(svr), timestamp, message))
    }

  /**
    * Now head over to `Main.scala` in the same package to complete the rest of the program.
    */

}
