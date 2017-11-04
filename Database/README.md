# Database

## How to add to project
Add the **database** package to the **src** folder in the workspace.<br />
Add the **RM_Database.jsp** file to the **WebContent** folder in the workspace.<br />
**Ensure that the mysql-connector-java-5.1.40-bin.jar file is added correctly to the workspace.**<br />
Add the **createRentalMarketplaceDatabase.sql** script to MySQLWorkbench and run it once to create the database. Note: the database assumes the password is CSCI-201.

## Current functionality
Create users with the createUser() function and authenticate (log in) users with the authenticateUser() function. See the RM_Database.jsp function to see what parameters must be passed into these. These functions both throw checked exceptions so they must be encased in a try-catch statement. The createUser() function returns an RMCreateUserException with useful error messages and error codes (see RMCreateUserException.java in the database package). Similar situation with authenticateUser, but it throws an RMAuthenticateUserException. The authenticateUser function returns an RMUser object if successful. Check out RMUser.java in the database package to see what you can do with it (I will be extending this further as I add more database features).
