//Import packages
import java.util.Scanner
import java.sql.Connection
import java.sql.DriverManager
import com.mysql.cj.xdevapi.UpdateStatement
import java.io.File
import java.io.PrintWriter

//code start
object Project0 {
  val log = new PrintWriter(new File("BankRecords.log"))

  def exit() {

    println("______________________________________________")
    println(" ")
    println(" Goodbye! Thank you for banking with us!")
    println()
    println()
  }
  def accountChoice(
      userSQL: Int,
      connection: Connection,
      scanner: Scanner
  ): String = {
    val statement = connection.createStatement()
    val statement1 = connection.createStatement()
    println(" ")
    println("ALL ACCOUNTS: ")
    val account = statement.executeQuery(
      "Select * from accounts where userID = " + userSQL + ";"
    )
    log.write(
      "Executing 'Select * from account where userID = " + userSQL + ";'\n"
    )
    while (account.next()) {
      print(
        account.getString(2) + "                      Available: $" + account
          .getString(3) + ".00"
      )
      println()
    }
    println("Select an account by entering name of account")
    return scanner.next().toUpperCase()
  }
  def accountTotal(userSQL: Int, chose: String, connection: Connection) {
    val statement = connection.createStatement()
    val statement1 = connection.createStatement()
    val depositTotal1 = statement.executeQuery(
      "Select Sum(amount) from transactions where transaction_type = \"Deposits\" and userID =" + userSQL + " and account_nickname =\"" + chose + "\";"
    )
    log.write(
      "Executing 'Select Sum(amount) from transactions where transaction_type = \"Deposits\" and userID =" + userSQL + " and account_nickname =\"" + chose + "\";'\n"
    )
    depositTotal1.next()
    val depositTotalRes = depositTotal1.getInt(1)
    val depositTotal = depositTotalRes.toInt
    val withdrawTotal1 = statement1.executeQuery(
      "Select Sum(amount) from transactions where transaction_type = \"Withdrawls\" and userID =" + userSQL + " and account_nickname =\"" + chose + "\";"
    )
    log.write(
      "Executing 'Select Sum(amount) from transactions where transaction_type = \"Withdrawls\" and userID =" + userSQL + " and account_nickname =\"" + chose + "\";'\n"
    )
    withdrawTotal1.next()
    val withdrawlTotalRes = withdrawTotal1.getInt(1)
    val withdrawlTotal = withdrawlTotalRes.toInt
    var accountsTotal = depositTotal - withdrawlTotal
    val updateAccountTotal = statement.executeUpdate(
      "UPDATE accounts SET total = " + accountsTotal + " where userID =" + userSQL + " and account_nickname = \"" + chose + "\"; "
    )
    log.write(
      "Executing 'UPDATE accounts SET total = " + accountsTotal + " where userID =" + userSQL + " and account_nickname = \"" + chose + "\";'\n"
    )

  }
  // Option 3 -- Delete an account
  def deleteAccount(
      userSQL: Int,
      chose: String,
      connection: Connection,
      accountsTotal: Int,
      scanner: Scanner
  ) {
    val statement = connection.createStatement()
    val statement1 = connection.createStatement()

    scanner.useDelimiter(System.lineSeparator())
    println("       BANK MENU: Close an Account")
    println("____________________________________________")
    println(" ")

    println("Close account? Y/N :")
    var deleteChoice = scanner.next().toUpperCase()
    if (deleteChoice == "Y") {
      var chose = accountChoice(userSQL, connection, scanner)
      println(chose)
      println(userSQL)
      var deletedAccount = statement.executeUpdate(
        "Delete from accounts where userID = " + userSQL + " and account_nickname = \"" + chose + "\";"
      )
      log.write(
        "Executing 'Delete from accounts where userID = " + userSQL + " and account_nickname = \"" + chose + "\";'\n"
      )
      println("Account was deleted.")
    } else if (deleteChoice == "N") {
      exit()
    }
  }
  def withdrawAct(
      userSQL: Int,
      chose: String,
      withdraw: Int,
      accountsTotal: Int,
      connection: Connection,
      scanner: Scanner
  ) {
    val statement = connection.createStatement()
    val statement1 = connection.createStatement()
    scanner.useDelimiter(System.lineSeparator())
    println("        BANK MENU: Make Withdrawl")
    println("____________________________________________")
    println(" ")
    var chose = accountChoice(userSQL, connection, scanner)
    println("Enter withdrawl amount.(min 20, max 2000)")
    var withdraw = scanner.nextInt()
    var withdrawl = statement.executeUpdate(
      "Insert into transactions(userID,account_nickname,transaction_type,amount) values (" + userSQL + ", \"" + chose + "\", \"Withdrawls\"," + withdraw + ");"
    )
    log.write(
      "Executing 'Insert into transactions(userID,account_nickname,transaction_type,amount) values (" + userSQL + ", \"" + chose + "\", \"Withdrawls\"," + withdraw + ");'\n"
    )
    accountTotal(userSQL, chose, connection)
    println(withdraw + " was withdrawn from " + chose)
    exit()
  }

  def depositAct(
      userSQL: Int,
      chose: String,
      deposit: Int,
      accountsTotal: Int,
      connection: Connection,
      scanner: Scanner
  ) {
    val statement = connection.createStatement()
    val statement1 = connection.createStatement()
    scanner.useDelimiter(System.lineSeparator())
    println("         BANK MENU: Make Deposit")
    println("____________________________________________")
    println(" ")
    var chose = accountChoice(userSQL, connection, scanner)
    accountTotal(userSQL, chose, connection)
    println("Enter deposit amount.(min 20, max 2000)")
    var deposit = scanner.next()
    var deposited = statement.executeUpdate(
      "Insert into transactions(userID,account_nickname,transaction_type, amount) values (" + userSQL + ", \"" + chose + "\", \"Deposits\", " + deposit + ");"
    )
    log.write(
      "Executing 'Insert into transactions(userID,account_nickname,transaction_type, amount) values (" + userSQL + ", \"" + chose + "\", \"Deposits\", " + deposit + ");'\n"
    )
    println(deposit + " was deposited into " + chose)
    accountTotal(userSQL, chose, connection)
    println()
    exit()
  }
  def accountTrans(
      userSQL: Int,
      chose: String,
      accountsTotal: Int,
      connection: Connection,
      scanner: Scanner
  ) {
    val statement = connection.createStatement()
    val statement1 = connection.createStatement()
    var chose = accountChoice(userSQL, connection, scanner)
    accountTotal(userSQL, chose, connection)
    val trans = statement.executeQuery(
      "Select * from transactions where userId = " + userSQL + " and account_nickname = \"" + chose + "\" ;"
    )
    log.write(
      "Executing 'Select * from transactions where userId = " + userSQL + " and account_nickname = \"" + chose + "\";'\n"
    )
    while (trans.next()) {
      print(
        "Account Name: " + trans.getString(
          2
        ) + "                       Transaction: " + trans.getString(
          3
        ) + "                          Amount: $" + trans.getInt(4) + ".00"
      )
      println()
    }
    exit()
  }
  def userInfo(userSQL: Int, connection: Connection) {
    val statement = connection.createStatement()
    val statement1 = connection.createStatement()
    println("    BANK MENU: User Personal Information")
    println("____________________________________________")
    println(" ")
    println("  User Information")
    println("______________________")
    val userDetail = statement.executeQuery(
      "Select user_name,legal_name,address, email from BankUser where userId = " + userSQL + ";"
    )
    log.write(
      "Executing 'Select user_name,legal_name,address, email from BankUser where userId = " + userSQL + ";'\n"
    )
    while (userDetail.next()) {
      println(
        "Username: " + userDetail.getString(
          1
        ) + "                                                           Customer Name: " + userDetail
          .getString(2)
      )
      println()
      println(
        "Street Address: " + userDetail.getString(
          3
        ) + "                                                                Email: " + userDetail
          .getString(4)
      )
    }
    exit()
  }
  //existing user
  def addExistingAccount(
      userSQL: Int,
      connection: Connection,
      scanner: Scanner
  ) {
    val statement = connection.createStatement()
    val statement1 = connection.createStatement()
    scanner.useDelimiter(System.lineSeparator())
    println("Enter knickname for Account")
    val accountName1 = scanner.next()
    var accountName = accountName1.toString().toUpperCase()
    println("Enter amount of deposit (min 100, max 9999)")
    val initialDeposit1 = scanner.next()
    var initialDeposit = initialDeposit1.toInt
    val addedAccount = statement.executeUpdate(
      "Insert Into accounts(userID, account_nickname, total) values (" + userSQL + ", \"" + accountName + "\", " + initialDeposit + ");"
    )
    log.write(
      "Executing 'Insert Into accounts(userID, account_nickname, total) values (" + userSQL + ", \"" + accountName + "\", " + initialDeposit + ");'\n"
    )
    val firstTrans = statement.executeUpdate(
      "Insert Into transactions(userID, account_nickname, transaction_type, amount) values (" + userSQL + ", \"" + accountName + "\",\"Deposits\", " + initialDeposit + ");"
    )
    log.write(
      "Executing 'Insert Into transactions(userID, account_nickname, transaction_type, amount) values (" + userSQL + ", \"" + accountName + "\",\"Deposits\", " + initialDeposit + ");'\n"
    )
    println(
      "New Account was created successfully.       " + accountName + " now has $" + initialDeposit + ".00"
    )
  }
//new  user

  def createNewUserAccount(
      userSQL: Int,
      connection: Connection,
      scanner: Scanner
  ) {
    val statement = connection.createStatement()
    val statement1 = connection.createStatement()
    scanner.useDelimiter(System.lineSeparator())
    println("      BANK MENU: Create New Account")
    println("____________________________________________")
    println(" ")
    println("Enter a user Name")
    val userName1 = scanner.next()
    var userName = userName1.toString()
    println("Enter a 4 digit pin that doesn't start with 0")
    val password1 = scanner.next()
    var password = password1.toInt
    println("Enter your name")
    val customerName1 = scanner.next()
    var customerName = customerName1.toString()
    println("Enter your street address")
    val address1 = scanner.next()
    var address = address1.toString()
    println("Enter a valid email address")
    val email1 = scanner.next()
    var email = email1.toString()
    println("Enter knickname for Account")
    val accountName1 = scanner.next()
    var accountName = accountName1.toString().toUpperCase()
    println("Enter amount of deposit (min 100, max 9999)")
    val initialDeposit1 = scanner.next()
    var initialDeposit = initialDeposit1.toInt

    val addedBankUsser = statement.executeUpdate(
      "Insert Into BankUser(user_Name, pin, legal_name, address,email) values (\"" + userName + "\", " + password + ", \"" + customerName + "\",\"" + address + "\",\"" + email + "\");"
    )
    log.write(
      "Executing 'Insert Into BankUser(user_Name, pin, legal_name, address,email) values (\"" + userName + "\", " + password + ", \"" + customerName + "\",\"" + address + "\",\"" + email + "\");;'\n"
    )
    val userRes = statement1.executeQuery(
      "Select userID from BankUser where user_Name = \"" + userName + "\";"
    )
    log.write(
      "Executing 'Select userID from BankUser where user_Name = \"" + userName + "\";'\n"
    )
    while (userRes.next()) {
      var userSQL1 = userRes.getString(1)
      var userSQL = userSQL1.toInt
      val addedAccount = statement.executeUpdate(
        "Insert Into accounts(userID, account_nickname, total) values (" + userSQL + ", \"" + accountName + "\", " + initialDeposit + ");"
      )
      log.write(
        "Executing 'Insert Into accounts(userID, account_nickname, total) values (" + userSQL + ", \"" + accountName + "\", " + initialDeposit + ");'\n"
      )
      val firstTrans = statement.executeUpdate(
        "Insert Into transactions(userID, account_nickname, transaction_type, amount) values (" + userSQL + ", \"" + accountName + "\",\"Deposits\", " + initialDeposit + ");"
      )
      log.write(
        "'Insert Into transactions(userID, account_nickname, transaction_type, amount) values (" + userSQL + ", \"" + accountName + "\",\"Deposits\", " + initialDeposit + ");'\n"
      )
    }
    println()
    println()
    println(
      "Welcome " + userName + ", Account created return to main menu to sign in!"
    )
    exit()
  }
  // END of Option 2
  // Menu after succesful login and access to OPTION 1 methods
  def signedInMenu(
      userSQL: Int,
      accountChose: String,
      withdraw: Int,
      deposit: Int,
      accountsTotal: Int,
      connection: Connection,
      scanner: Scanner
  ) {
    val statement = connection.createStatement()
    val statement1 = connection.createStatement()
    println("  BANK MENU: What would you like to do?")
    println("____________________________________________")
    println(" ")
    println("Signed In : Select between options 1-6")
    println("1.) Withdrawls")
    println("2.) Deposits")
    println("3.) Account Transactions ")
    println("4.) User Information")
    println("5.) Create a New account")
    println("6.) Close an Account ")
    println("7.) Exit")
    var loggedInOption = scanner.nextInt()
    // invalid input into Main Menu
    while ((loggedInOption >= 8) && (loggedInOption <= 1)) {
      println("Invalid input. Please select from options 1-6")
      var loggedInOption = scanner.nextInt()
    }
    // login options execute method listed
    if (loggedInOption == 1) {
      withdrawAct(
        userSQL,
        accountChose,
        withdraw,
        accountsTotal,
        connection,
        scanner
      )
    } else if (loggedInOption == 2) {
      depositAct(
        userSQL,
        accountChose,
        deposit,
        accountsTotal,
        connection,
        scanner
      )
    } else if (loggedInOption == 3) {
      accountTrans(userSQL, accountChose, accountsTotal, connection, scanner)
    } else if (loggedInOption == 4) {
      userInfo(userSQL, connection)
    } else if (loggedInOption == 5) {
      addExistingAccount(
        userSQL,
        connection,
        scanner
      )
    } else if (loggedInOption == 6) {
      deleteAccount(
        userSQL,
        accountChose,
        connection,
        accountsTotal,
        scanner
      )
    } else if (loggedInOption == 7) {
      exit()
    }
  }
  def signIn(
      userSQL: Int,
      chose: String,
      withdraw: Int,
      deposit: Int,
      accountsTotal: Int,
      connection: Connection,
      scanner: Scanner
  ) {
    var user_nameAttempt = ""
    var passwordAttempt = 0
    val statement = connection.createStatement()
    val statement1 = connection.createStatement()
    var signedIn = false
    scanner.useDelimiter(System.lineSeparator())
    println("         BANK MENU: Sign-In")
    println("____________________________________________")
    println(" ")
    println("Enter your user name ")
    var user_nameAttempt1 = scanner.next()
    user_nameAttempt = user_nameAttempt1.toString.toUpperCase
    val userRes = statement.executeQuery(
      "Select userID from BankUser where user_Name = \"" + user_nameAttempt + "\";"
    )
    log.write(
      "Executing 'Select userID from BankUser where user_Name = " + user_nameAttempt + "';\n"
    )
    while (userRes.next()) {
      var userSQL1 = userRes.getString(1)
      var userSQL = userSQL1.toInt

      println("Enter your security pin:")
      var passwordAttempt1 = scanner.next()
      passwordAttempt = passwordAttempt1.toInt
      val pinRes = statement1.executeQuery(
        "Select userID from BankUser where pin = " + passwordAttempt + "; "
      )
      log.write(
        "Executing 'Select userID from BankUser where pin = " + passwordAttempt + "';\n"
      )
      while (pinRes.next()) {
        var pinSQL1 = pinRes.getString(1)
        var pinSQL = pinSQL1.toInt
        if (pinSQL == userSQL) {
          signedIn = true
        
        if (signedIn) {
          signedInMenu(
            userSQL,
            chose,
            withdraw,
            deposit,
            accountsTotal,
            connection,
            scanner
          )
        }else if (!signedIn){
        println("Invalid credentials.  Try again later.")
        exit()
        } 
      }
      }
    }
  }
  //option 2 -- Create account adds all to contact info or bank user table in database. UsedID will
//            be auto generated and used as primary key to select use info for all option 1 choices.
  def mainMenu(
      userSQL: Int,
      chose: String,
      withdraw: Int,
      deposit: Int,
      accountsTotal: Int,
      connection: Connection,
      scanner: Scanner
  ) {
//greeting and start menu leads to sign in, create account or exit
    println("             BANK MAIN MENU")
    println("____________________________________________")
    println(" ")
    println("Welcome to Online Bank")
    println(" ")
    println("Select an option between 1-3")
    println("1.) Sign in")
    println("2.) Create a new account ")
    println("3.) Exit ")
    var mainMenuOption = scanner.nextInt()
// if invalid input into Main Menu
    while ((mainMenuOption <= 0) || (mainMenuOption >= 4)) {
      println("Invalid input. Please select between options 1-3 ")
      var mainMenuOption = scanner.nextInt()
    }
    if (mainMenuOption == 1) {
      signIn(
        userSQL,
        chose,
        withdraw,
        deposit,
        accountsTotal,
        connection,
        scanner
      )
    } else if (mainMenuOption == 2) {
      createNewUserAccount(userSQL, connection, scanner)
    } else if (mainMenuOption == 3) {
      exit()
    }
  }
  def main(args: Array[String]): Unit = {
    val driver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://localhost:3306/projectzero"
    val username = "root"
    val password = "Greenairplane.87"
    var scanner = new Scanner(System.in)
    var accountsTotal = 0
    var chose = ""
    var initialDeposit = 0
    var userSQL = 0
    var withdraw = 0
    var deposit = 0
    var connection: Connection = null
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)

      mainMenu(
        userSQL,
        chose,
        withdraw,
        deposit,
        accountsTotal,
        connection,
        scanner
      )

    } catch {
      case e: Exception => {
        e.printStackTrace
        println("Invalid input: Try again from main menu")
      }
    }
    connection.close()
    log.close()
  }
}
