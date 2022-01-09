//Import packages
import java.util.Scanner
import java.sql.Connection
import java.sql.DriverManager
import com.mysql.cj.xdevapi.UpdateStatement

//code start
object Project0 {
  var scanner = new Scanner(System.in)
  var connection: Connection = null
  val statement = connection.createStatement()
  val statement1 = connection.createStatement()

  
// exit and farewell if 3 was selected main menu
  def exit() {
      println("Goodbye!")
    }

  // Option 3 -- Delete an account
  def deleteAccount() {
    println("    BANK MENU")
    println("_____________________")
    println(" ")
    println("Delete account? Y/N :")
    var deleteChoice = scanner.nextLine().toUpperCase()
    if (deleteChoice == "Y") {
      accountChoice()
      var deletedAccount = statement.executeUpdate(
        "Delete from accounts where " + userSQL + " and " + accountChose + ""
      )
    } else if (deleteChoice == "N") {
      exit()
    }
  }

  def withdrawAct() {
    println("    BANK MENU")
    println("_____________________")
    println(" ")
    accountChoice()
    accountTotal()
    println("Enter withdrawl amount.(min 20, max 2000)")
    var withdraw = scanner.nextInt()
    if (withdraw > accountsTotal) {
      println("Insufficient Funds")
    } else {
      var withdrawl = statement.executeUpdate(
        "Insert into transactions(userID,account_nickname,amount) values (" + userSQL + ", " + accountChose + ", Withdrawls," + withdraw + ") "
      )
      println(withdraw + " was withdrawn from " + accountChose)
      updateAccountTotal()
      exit()
    }
  }
  def depositAct() {
    println("     BANK MENU")
    println("_____________________")
    println(" ")
    accountChoice()
    println("Enter deposit amount.(min 20, max 2000)")
    var deposit = scanner.nextInt()
    var deposited = statement.executeUpdate(
      "Insert into transactions(userID,account_nickname,amount) values (" + userSQL + ", " + accountChose + ", Deposits, " + deposit + ") "
    )
    println(deposited + " was deposited into " + accountChose)
    updateAccountTotal()
    exit()
  }
  def accountTrans() {
    println("     BANK MENU")
    println("_____________________")
    println(" ")
    println("Select an Account for transaction list)")
    accountChoice()
    deleteAccount()
  }
  def userInfo() {
    println("    BANK MENU")
    println("_____________________")
    println(" ")
    println("User Information")
    val userDetail = statement.executeQuery(
      "Select user_name,legal_name,,address, email from BankUser where userId = " + userSQL + ""
    )
    while (userDetail.next()) {
      print(
        "Username: " + userDetail.getString(
          1
        ) + "  Customer Name: " + userDetail.getString(2)
      )
      println(
        "Street Address: " + userDetail.getString(3) + " Email: " + userDetail
          .getString(4)
      )
    }
  }
  def accountChoice() {
    println("    BANK MENU")
    println("_____________________")
    println(" ")
    println("ALL ACCOUNTS: ")
    val account = statement.executeQuery("Select * from accounts")
    while (account.next()) {
      print(account.getString(2) + " " + account.getString(3))
      println()
    }
    println("Select an account by entering name of account")
    var accountChose = scanner.nextLine()
  }
  def accountTotal() {
    val withdrawTotal1 = statement.executeQuery(
      "Select Sum(Amount) from transactions where transaction_type = \"Withdrawls\" and userID =" + userSQL + " and account_nickname =" + accountChose + ""
    )
    val withdrawlTotalRes = withdrawTotal1.getString(1)
    val withdrawlTotal = withdrawlTotalRes.toInt
    val depositTotal1 = statement.executeQuery(
      "Select Sum(Amount) as WithdrawlTotal from transactions where transaction_type = \"Deposits\" and userID =" + userSQL + " and account_nickname =" + accountChose + ""
    )
    val depositTotalRes = depositTotal1.getString(1)
    val depositTotal = depositTotalRes.toInt
    var AccountsTotal = depositTotal - withdrawlTotal
  }
  def updateAccountTotal() {
    //SQL that updates the amount into account table]
  }

// Sign  in attempt if OPTION 1 was selected on main menu
  var user_nameAttempt = ""
  var passwordAttempt = 0
  var signedIn = false

  def signIn() {
    println("    BANK MENU")
    println("_____________________")
    println(" ")
    println("Enter your user name (case sensitive)")
    var user_nameAttempt = scanner.nextLine()
    println("Enter your password(case sensitive")
    var passwordAttempt = scanner.nextInt()

    val userRes = statement.executeQuery(
      "Select userID from BankUser where user_Name = " + user_nameAttempt + " "
    )
    var userSQL1 = userRes.getString(1)
    var userSQL = userSQL1.toInt
    val pinRes = statement1.executeQuery(
      "Select userID from BankUser where pin = " + passwordAttempt + " "
    )
    var pinSQL1 = pinRes.getString(1)
    var pinSQL = pinSQL1.toInt

    while (userSQL == pinSQL) {
      var signedIn = true
    }
    if (signedIn) {
      signedInMenu()
    } else if (!signedIn) {
      signedIn = false
      println(
        "ERROR: Sign-In credentials incorrect. Try again or create a new account."
      )
      println("Enter your user name ")
      var user_nameAttempt = scanner.nextLine()
      println("Enter your password")
      var passwortAttemnpt = scanner.nextInt()
    }
  }
   //option 2 -- Create account adds all to contact info or bank user table in database. UsedID will
//            be auto generated and used as primary key to select use info for all option 1 choices.

  def createNewUserAccount() {
    println("    BANK MENU")
    println("_____________________")
    println(" ")
    println("Enter a user Name")
    var userName = scanner.nextLine()
    println("Enter a 4 digit pin that doesn't start with 0")
    var password = scanner.nextInt()
    println("Enter your name")
    var customerName = scanner.nextLine()
    println("Enter your address")
    var address = scanner.nextLine()
    println("Enter an email address")
    var email = scanner.nextLine()
    println("Enter knickname for Account")
    var accountName = scanner.nextLine()
    println("Enter amount of deposit (min 100, max 9999)")
    var initialDeposit = scanner.nextInt()
    val addedBankUsser = statement.executeUpdate(
      "Insert Into BankUser(user_Name, pin, legal_name, address,email) values (" + userName + ", " + password + ", " + customerName + "," + address + "," + email + ")"
    )
    val addedAccount = statement.executeUpdate(
      "Insert Into accounts(userID, account_nickname, amount) values (" + userSQL + ", " + accountName + ", " + initialDeposit + ""
    )
    val firstTrans = statement.executeUpdate(
      "Insert Into transactions(userID, account_nickname, transaction_type, amount) values (" + userSQL + ", " + accountName + ",\"Deposits\", " + initialDeposit + ""
    )
  }
  // END of Option2

  // Menu after succesful login and access to OPTION 1 methods
  var loggedInOption = scanner.nextInt()
  def signedInMenu() {
    println("    BANK MENU")
    println("_____________________")
    println(" ")
    println("Signed In : Select between options 1-5")
    println("1.) Withdrawls")
    println("2.) Deposits")
    println("3.) Account Transactions or Delete")
    println("4.) User Information")
    println("5.) Exit ")

    // invalid input into Main Menu
    while ((loggedInOption >= 5) && (loggedInOption <= 1)) {
      println("Invalid input. Please select from options 1-5")
      var loggedInOption = scanner.nextInt()
    }

    // login options execute method listed
    while (loggedInOption == 1) {
      withdrawAct()
    }
    while (loggedInOption == 2) {
      depositAct()
    }
    while (loggedInOption == 3) {
      accountTrans()
    }
    while (loggedInOption == 4) {
      userInfo()
    }
    while (loggedInOption == 5) {
      exit()
    }
  }

  def mainMenu() {
//greeting and start menu leads to sign in, create account or exit,
    println("    BANK MENU")
    println("_____________________")
    println(" ")
    println("Welcome to 'I wouldn't bank here' Bank")
    println(" ")
    println("Select an option between 1-3")
    println("1.) Sign in")
    println("2.) Create a new account ")
    println("3.) Exit ")
    var mainMenuOption = scanner.nextInt();

// if invalid input into Main Menu

    while ((mainMenuOption <= 0) || (mainMenuOption >= 4)) {
      println("Invalid input. Please select between options 1-3 ")
      var mainMenuOption = scanner.nextInt()
    }
    if (mainMenuOption == 1) {
      signIn()
    } else if (mainMenuOption == 2) {
      createNewUserAccount()
    } else if (mainMenuOption == 3) {
      exit()
    }
  }

  def main(args: Array[String]): Unit = {
    //CONNECTION TO DATABASE
    val driver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://localhost:3306/projectzero"
    val username = "root"
    val password = "Greenairplane.87"
    var accountsTotal = 0
    var accountChose = 0
    var userSQL = 0
    var initialDeposit = 0

    try {
// makes the connection to db
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)

      mainMenu()


    } catch {
      case e: Exception => e.printStackTrace
    }
    connection.close()

  } // end main

} // end object
