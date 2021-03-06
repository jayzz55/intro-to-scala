package fundamentals.level02

/**
  * These exercises introduce data types and also algebraic data types (ADTs). ADTs are a huge part of typed functional programming.
  * You will also be introduced to a very useful technique for working with ADTs, i.e. pattern matching.
  */
object TypesExercises {

  /**
    * A simple data type
    *
    * Here is an example of a `Person` type, which is a wrapper on `String` and `Int`.
    *
    * This is a "product type", i.e. a `Person` is a "product" of `String` and `Int`.
    *
    */
  case class Person(name: String, age: Int)

  /**
    * case class instances are immutable!
    *
    * scala> val person = Person(name = "John Kane", age = 35)
    *
    * scala> person.age = 25
    * <console>:18: error: reassignment to val
    * person.age = 25
    *
    * Notice as well how there is no need for using the `new` operator
    */
  val person = Person(name = "John Kane", age = 35)

  /**
    * scala> val person = Person("Bob", 50)
    * scala> showPerson1(person)
    * = "Bob is 50 years old"
    *
    * This uses a technique called pattern matching. You will see more of this later.
    **/
  def showPerson1(person: Person): String =
    person match {
      case Person(name, age) => s"${name} is ${age} years old"
    }

  /**
    * Same as `showPerson1`, but using string interpolation only.
    *
    * Hint: Navigate the Person class' fields using the "." operator
    */
  def showPerson2(person: Person): String =
    s"${person.name} is ${person.age} years old"

  /**
    * scala> val person = Person("Bob", 50)
    * scala> changeName("Bobby", person)
    * = Person("Bobby", 50)
    *
    * `person` is immutable! This function returns a new instance of `Person` with the `name` changed.
    * Check out the corresponding test in `TypesExercisesTest` to understand why.
    *
    * Hint: Use the .copy method
    */
  def changeName(newName: String, person: Person): Person =
    person.copy(name = newName)

  /**
    * Let's look at another data type.
    *
    * `Wallet` is a tiny type on `Double` to represent the amount of money someone has.
    */

  case class Wallet(amount: Double)

  /**
    * scala> val wallet = Wallet(20.5)
    * scala> showWallet(wallet)
    * = "The wallet amount is 20.5"
    *
    * You can solve this like how you solved `showPerson1` or `showPerson2`.
    */
  def showWallet(wallet: Wallet): String =
    s"The wallet amount is ${wallet.amount}"

  /**
    * Here is another example of working with immutable values.
    *
    * scala> val wallet = Wallet(100)
    * scala> purchase(80, wallet)
    * = Wallet(20)
    **/
  def purchase(cost: Double, wallet: Wallet): Wallet =
    wallet.copy(amount = wallet.amount - cost)

  /**
    * scala> showTrafficLightStr("red")
    * = "The traffic light is red"
    *
    * scala> showTrafficLightStr("yellow")
    * = "The traffic light is yellow"
    *
    * scala> showTrafficLightStr("green")
    * = "The traffic light is green"
    *
    * What if `trafficLight` is not "red", "yellow" or "green"?
    *
    * Go to `TypesExercisesTest.scala` and implement the test for this scenario: "should return a default on other inputs"
    *
    **/
  def showTrafficLightStr(trafficLight: String): String =
    trafficLight match {
      case "red" => s"The traffic light is red"
      case "yellow" => s"The traffic light is yellow"
      case "green" => s"The traffic light is green"
      case "flashing" => s"The traffic light is flashing"
      case _ => s"The traffic light is green"
    }

  /**
    * We have a new traffic light called Flashing.
    *
    * Extend `showTrafficLightStr` that you have just implemented above to support this new functionality.
    *
    * Use a test driven approach to implement this new functionality: "showTrafficLightStr should show Flashing"
    *
    * scala> showTrafficLightStr("flashing")
    * = "The traffic light is flashing"
    *
    **/

  /**
    * A "sum type" represents more than one possible value.
    *
    * You can read the following as a `TrafficLight` is either `Red` or `Yellow` or `Green`.
    *
    * A sealed trait can only be extended in the same file that it is defined.
    *
    * This technique helps you make invalid states/values irrepresentable in your programs
    */
  sealed trait TrafficLight

  object TrafficLight {

    case object Red extends TrafficLight

    case object Yellow extends TrafficLight

    case object Green extends TrafficLight

    case object Flashing extends TrafficLight

  }

  /**
    * scala> showTrafficLight(Red)
    * = "The traffic light is red"
    *
    * scala> showTrafficLight(Yellow)
    * = "The traffic light is yellow"
    *
    * scala> showTrafficLight(Green)
    * = "The traffic light is green"
    *
    * It is impossible to get an invalid TrafficLight as input
    *
    * Hint: Use pattern matching
    **/

  import TrafficLight._

  def showTrafficLight(trafficLight: TrafficLight): String =
    trafficLight match {
      case Red => s"The traffic light is red"
      case Yellow => s"The traffic light is yellow"
      case Green => s"The traffic light is green"
      case Flashing => s"The traffic light is flashing"
    }

  /**
    * Now introduce a new type of `TrafficLight` called `Flashing`.
    *
    * What happens when you try to compile now?
    *
    * Don't forget to fill in the unit test for this new scenario: "showTrafficLight should show Flashing"
    */

}
