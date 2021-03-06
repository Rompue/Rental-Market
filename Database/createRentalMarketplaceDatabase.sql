DROP DATABASE IF EXISTS RentalMarketplace;
CREATE DATABASE RentalMarketplace;

USE RentalMarketplace;

CREATE TABLE ProfileImage (
	imageID INT(11) PRIMARY KEY AUTO_INCREMENT,
    imageLink VARCHAR(1000) NOT NULL
);

CREATE TABLE Person (
	userID INT(11) PRIMARY KEY AUTO_INCREMENT,
    firstName VARCHAR(100) NOT NULL,
    lastName VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    passcode VARCHAR(100) NOT NULL,
    positiveRatings INT(100) NOT NULL,
    negativeRatings INT(100) NOT NULL,
    totalRatings INT(100) NOT NULL,
    image VARCHAR(255) DEFAULT 'https://dl.dropboxusercontent.com/s/qomzwb40imxwkap/DefaultProfile.png?dl=0'
);

CREATE TABLE Post (
	postID INT(11) PRIMARY KEY AUTO_INCREMENT,
    itemName VARCHAR(100) NOT NULL,
    postDescription VARCHAR(10000) NOT NULL,
    borrowAmount VARCHAR(100) NOT NULL,
    postDate DATETIME NOT NULL,
    dueDate DATETIME NOT NULL,
    completed TINYINT(1) NOT NULL,
    deleted TINYINT(1) NOT NULL,
    
    userID INT(11) NOT NULL,
    FOREIGN KEY fk1(userID) REFERENCES Person(userID)
);

CREATE TABLE Request (
	requestID INT(11) PRIMARY KEY AUTO_INCREMENT,
    itemName VARCHAR(100) NOT NULL,
    requestDate DATETIME NOT NULL,
    dueDate DATETIME NOT NULL,
    completed TINYINT(1) NOT NULL,
    deleted TINYINT(1) NOT NULL,
    rating INT(1),
    
    borrowerID INT(11) NOT NULL,
    lenderID INT(11) NOT NULL,
    FOREIGN KEY fk1(borrowerID) REFERENCES Person(userID),
    FOREIGN KEY fk2(lenderID) REFERENCES Person(userID),
    
    postID INT(11),
    FOREIGN KEY fk3(postID) REFERENCES Post(postID)
);

CREATE TABLE Notification (
	notificationID INT(11) PRIMARY KEY AUTO_INCREMENT,
    text VARCHAR(255) NOT NULL,
    notificationDate DATETIME NOT NULL,
    active TINYINT(1) NOT NULL,
    
	userID INT(11) NOT NULL,
    FOREIGN KEY fk1(userID) REFERENCES Person(userID)
);

CREATE TABLE Chat (
	chatID INT(11) PRIMARY KEY AUTO_INCREMENT,
    
	postID INT(11),
    FOREIGN KEY fk1(postID) REFERENCES Post(postID),
    requestID INT(11),
    FOREIGN KEY fk2(requestID) REFERENCES Request(requestID)
);

CREATE TABLE ChatMessage (
    message VARCHAR(255) NOT NULL,
    dateSent DATETIME NOT NULL,
    
    chatID INT(11) NOT NULL,
    FOREIGN KEY fk1(chatID) REFERENCES Chat(chatID),
    userID INT(11) NOT NULL,
    FOREIGN KEY fk2(userID) REFERENCES Person(userID)
);