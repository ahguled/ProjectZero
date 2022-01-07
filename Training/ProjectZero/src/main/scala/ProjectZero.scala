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
    
       try {
// makes the connection to db
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      val statement = connection.createStatement()

    mainMenu()
  
    def mainMenu() {
//greeting and start menu leads to sign in or create method
      println("    BANK MENU")
      println("_____________________")
      println(" ")
      println("Welcome to F & D Bank")
      println(" ")
      println("Select an option between 1-3")
      println("1.) Sign in")
      println("2.) Create a new account ")
      println("3.) Exit ")
      var mainMenuOption = scanner.nextInt();
//invalid input into Main Menu
      while ((mainMenuOption <= 0) || (mainMenuOption >= 4)) {
      println("Invalid input. Please select between options 1-3")
      var mainMenuOption=scanner.nextInt()
       }
      if (mainMenuOption == 1 ) {
          signIn()
      } else  (mainMenuOption == 3){
          exit()
      } else if (mainMenuOption == 2) {
          createNewUserAccount()
    }
  }
    def exit(){
    println("Goodbye!")
  }

// option 1 -- Sign in Attempt
    var user_nameAttempt= ""
    var passwordAttemt= 0
    var signedIn= false

    def signIn(){
        println("    BANK MENU")
        println("_____________________")
        println(" ")
        println("Enter your user name (case sensitive)")
        var user_nameAttempt = scanner.nextLine()
        println("Enter your password(case sensitive")
        var passwordAttemnpt = scanner.nextInt()

        val userSQL = statement.executeQuery("Select userID from BankUser where user_Name = "+user_nameAttempt" ")
        val pinSQL = statement.executeQuery("Select userID from BankUser where pin = "+passwordAttempt" ")
        while (userSQL == pinSQL) {
        var signedIn= true}
        if (signedIn) {
             signedInMenu()
        } else if (!signedIn) {
          signedIn = false
          println("ERROR: Sign-In credentials incorrect. Try again or create a new account.")
          println("Enter your user name ")
          var user_nameAttempt=scanner.nextLine()
          println("Enter your password")
          var passwortAttemnpt=scanner.nextInt()
        } 
        // if both are equal to a value in database then the user ID will be equal. This is primary key and foriegn key.
     }
  
  // option 1 main menu after successful login
    var loggedInOption=scanner.nextInt()  
    def signedInMenu() {
        println("    BANK MENU")
        println("_____________________")
        println(" ")
        println("Signed In : Select between options 1-5")
        println("1.) Withdrawls")
        println("2.) Deposits")
        println("3.) Account Details")
        println("4.) User Information")
        println("5.) Exit to Main Menu")

        // invalid input into Sign In menu
          while ((loggedInOption >= 5) && (loggedInOption <= 1)) {
          println("Invalid input. Please select from options 1-5")
          var loggedInOption=scanner.nextInt()
          }
          while (loggedInOption == 1) { // need to create error message for if account empty compare user input with a query of total in account from transaction table
          withdraw()
          }
           while (loggedInOption == 2) {
           deposit()
          }
           while (loggedInOption == 3) {
          accountTrans()
          }
          while (loggedInOption == 4) {
          userInfo()
          }
          while (loggedInOption == 5) {
          mainMenu()
          }
      }

      def withdraw(){
        println("    BANK MENU")
        println("_____________________")
        println(" ")
        accountChoice() // var accountChose holds which account to add withdraw to
        println("Enter withdrawl amount.(min 20, max 2000)")
        var withdraw = scanner.nextInt()
        var withdrawl =statement.executeUpdate("Insert into transactions(userID,account_nickname,amount) values ("+userSQL","+accountChose","+withdraw") ")
        println(withdraw+" was withdrawn from "+ accountChose)
                  // add this as amount to data base in trasanction table as a withdraw for that specific user ID and account
      }
      def deposit(){
        println("     BANK MENU")
        println("_____________________")
        println(" ") 
        accountChoice() // var accountChose holds which account to add deposit too
        println("Enter deposit amount.(min 20, max 2000)")
        var deposit = scanner.nextInt()
        var deposited =statement.executeUpdate("Insert into transactions(userID,account_nickname,amount) values ("+userSQL","+accountChose","+deposit") ")
        println(deposited + " was deposited into "+ accountChose)
           // add this as amount to data base in trasanction table as a deposit for the speciffic user ID
      }

      def accountTrans(){
         println("     BANK MENU")
         println("_____________________")
         println(" ")
         println("Select an Account for transaction list)")
        accountChoice() 
          // will display all account name and total connected to a userID
          // Will allow them to see trasactions with date that occured in that acccount and amounts from transaction table.
      }

      def userInfo(){
         println("    BANK MENU")
         println("_____________________")
         println(" ")
         println("User Information")
         println()
          println(resultfromquery)
         // will display user info: name address, email from BankUSer table
      }

      def accountTotal(){
        val withdrawTotal = statement.executeQuery("Select ")// select the sum of amount where transaction type = withdraw
        val depositTotal = statement.executeQuery("Select") //select the sum of amount where transaction type = deposit
        var accountTotal =  depositTotal-withdrawTotal
      }

      def accountChoice(){
        println("    BANK MENU")
        println("_____________________")
        println(" ")
        println("ALL ACCOUNTS: ")
        val account = statement.executeQuery("Select * from accounts")
        while (account.next()){
        print(account.getString(2) + " "+ account.getString(3))
        println()
        }
        println("Select an account by entering name of account")
        var accountChose = scanner.nextLine()
      }

      //END OF OPTION 1 FROM MAIN MENU

//option 2 -- Create account adds all to contact info or bank user table in database. UsedID will
//            be auto generated and used as primary key to select use info for all option 1 choices.

     def createNewUserAccount() {
       println("    BANK MENU")
       println("_____________________")
       println(" ")
       println("Enter a user Name")
        var userName= scanner.nextLine()
       println("Enter a 4 digit pin that doesn't start with 0")
        var password = scanner.nextInt()
       println("Enter your name")
        var customerName = scanner.nextLine()
       println("Enter your address") 
        var address = scanner.nextLine()
       println("Enter an email address")
        var email = scanner.nextLine()
        println("Enter knickname for Account")
        var accountName=scanner.nextLine()
        println("Enter amount of deposit (min 100, max 2000)")
        var deposit = scanner.nextInt
      val addedBankUsser = statement.executeUpdate("Insert Into BankUser(user_Name, pin, legal_name, address,email) values ("+userName", "+password", "+customerName","+address","+email")")
      val addedAccount = statement.executeUpdate("Insert Into accounts(userID, account_nickname, amount) values("+accountName"+"+deposit")" )
     }      

          /*  while ( resultSet.next() ) {
       print(resultSet.getString(1) + " "+ resultSet.getString(2))
       println()
      } */

    } catch {
      case e: Exception => e.printStackTrace
    }
    connection.close()     

    } // end main

} // end object
