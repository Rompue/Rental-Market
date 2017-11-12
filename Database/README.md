# Database

## How to add to project
Add the **database** package to the **src** folder in the workspace.<br />
**Ensure that the mysql-connector-java-5.1.40-bin.jar file is added correctly to the workspace.**<br />
Add the **createRentalMarketplaceDatabase.sql** script to MySQLWorkbench and run it once to create the database. Note: the database assumes the password is CSCI-201.<br />
To access the database in a jsp file, put the following at the top:
```
<%@ page import="database.*" %>
```

## Initialization
You **must** initialize the database first by calling ```RMDatabase.initializeDatabase()``` before calling other methods, otherwise it will crash with a null pointer exception. You shouldn't need to call it again after that (on other pages, for example) but calling it again won't do anything so feel free to call it as many times as you want.

## Current functionality
Create users with ```RMDatabase.createUser(firstName, lastName, email, password)```<br />
Authenticate users (log in) with ```RMDatabase.authenticateUser(email, password)```<br />
Get all marketplace posts with ```RMDatabase.getPosts()```<br />
