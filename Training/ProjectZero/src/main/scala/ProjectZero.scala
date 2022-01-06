//Import packages
import java.util.Scanner
import java.sql.Connection
import java.sql.DriverManager


//code start
object Project0 {

    def main(args:Array[String]): Unit = {
            //CONNECTION TO DATABASE
    val driver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://localhost:3306/projectzero" 
    val username = "root"
    val password = "Greenairplane.87" 
    var scanner = new Scanner(System.in)
    var connection:Connection = null

    mainMenu()
  

    def mainMenu() {
//greeting and start menu leads to sign in or create method
    println("     BANK MENU")
    println("_____________________")
    println(" ")
    println("Welcome to F & D Bank")
    println(" ")
    println("Select an option between 1-2")
    println("1.) Sign in")
    println("2.) Create a new account ")
    var mainMenuOption = scanner.nextInt();

//invalid input into Main Menu
    while ((mainMenuOption != 1) && (mainMenuOption !=2)) {
        println("Invalid input. Please select 1 or 2")
        var mainMenuOption=scanner.nextInt()
    }
    if (mainMenuOption ==1) {
      signIn()
    } else if (mainMenuOption == 2) {
      createNewaccount()
    }
  }
  
    
// option 1 -- Sign in Attempt
    var user_nameAttempt= ""
    var passwordAttemt= 0
    var signedIn= true
    def signIn(){
        println("Enter your user name ")
        var user_nameAttempt=scanner.nextLine()
        println("Enter your password")
        var passwortAttemnpt=scanner.nextInt()
        while ((user_nameAttempt == "BankUser.user_Name") && (passwordAttempt == "BankUser.pin")) {
        var signedIn= true}
        if (signedIn = true) {
             signedInMenu()
        }
        while (signedIn=false) {
          println("ERROR: Sign-In credentials incorrect. Try Again")
          println("Enter your user name ")
          user_nameAttempt=scanner.nextLine()
          println("Enter yourpassword")
          passwortAttemnpt=scanner.nextInt()
        }
        // if both are equal to a value in database then the user ID  is the userID(primary key attached to name pin pair)
        signedInMenu()
    }
  
  // option 1 main menu after successful login
      def signedInMenu() {
        println("     BANK MENU")
        println("_____________________")
        println(" ")
        println("Select between options 1-5")
        println("1.) Withdrawls")
        println("2.) Deposits")
        println("3.) Account Details")
        println("4.) User Information")
        println("5.) Exit to Main Menu")
     }
       var loggedInOption=scanner.nextInt()
    // invalid input into Sign In menu
          while ((loggedInOption >= 5) && (loggedInOption <= 1)) {
          println("Invalid input. Please select from options 1-5")
          var loggedInOption=scanner.nextInt()
          }

          while (loggedInOption == 1) {
          println("Enter withdrawl amount.(min 20, max 2000)")
           var withdraw = scanner.nextInt()
          // add this as amount to data base in trasanction table as a withdraw for that specific user ID
          }
           while (loggedInOption == 2) {
           println("Enter deposit amount.(min 20, max 2000)")
           var deposit = scanner.nextInt()
           // add this as amount to data base in trasanction table as a deposit for the speciffic user ID
          }
           while (loggedInOption == 3) {
          println("Select an Account for transaction list)")
           println(resultfromquery)
          // will display all account name and type  and total connected to a user and byu selecting will allow they to see trasactions that occured in that acccount and amount.
          }
          while (loggedInOption == 4) {
          println("User Information)")
          println(resultfromquery)
         // will display user info: name address, email from Contact info
          }
          while (loggedInOption == 5) {
          // return to main menu 
          }
     





//option 2 -- Create account
      if (mainMenuOption == 2) {
        //code here 
      }

//database stuff   
    try {
      // make the connection to db
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)

      // create the statement, and insert

      //val statement = connection.createStatement()
      //val resultSet = statement.executeUpdate("INSERT INTO BankUser() values ()")

    /*  while ( resultSet.next() ) {
       print(resultSet.getString(1) + " "+ resultSet.getString(2))
       println()
      } */

    } catch {
      case e: Exception => e.printStackTrace
    }
    connection.close()     
     }
  }