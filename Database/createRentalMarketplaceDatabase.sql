DROP DATABASE IF EXISTS RentalMarketplace;
CREATE DATABASE RentalMarketplace;

USE RentalMarketplace;

CREATE TABLE ProfileImage (
	imageID INT(11) PRIMARY KEY AUTO_INCREMENT,
    imageLink VARCHAR(1000) NOT NULL
);

CREATE TABLE Chat (
	chatID INT(11) PRIMARY KEY AUTO_INCREMENT
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
    completed BOOLEAN NOT NULL,
    deleted BOOLEAN NOT NULL,
    userID INT(11) NOT NULL,
    FOREIGN KEY fk1(userID) REFERENCES Person(userID),
	chatID INT(11) NOT NULL,
    FOREIGN KEY fk2(chatID) REFERENCES Chat(chatID)
);

CREATE TABLE Request (
	requestID INT(11) PRIMARY KEY AUTO_INCREMENT,
    itemName VARCHAR(100) NOT NULL,
    postDescription VARCHAR(10000) NOT NULL,
    dateCreated DATETIME NOT NULL,
    dateCompleted DATETIME NOT NULL,
    completed BOOLEAN NOT NULL,
    deleted BOOLEAN NOT NULL,
    rating INT(1),
    borrowerID INT(11) NOT NULL,
    lenderID INT(11) NOT NULL,
    FOREIGN KEY fk1(borrowerID) REFERENCES Person(userID),
    FOREIGN KEY fk2(lenderID) REFERENCES Person(userID),
    chatID INT(11) NOT NULL,
    FOREIGN KEY fk3(chatID) REFERENCES Chat(chatID)
);

CREATE TABLE ChatMessage (
    message VARCHAR(10000) NOT NULL,
    dateSent DATETIME NOT NULL,
    
    chatID INT(11) NOT NULL,
    FOREIGN KEY fk1(chatID) REFERENCES Chat(chatID),
    userID INT(11) NOT NULL,
    FOREIGN KEY fk2(userID) REFERENCES Person(userID)
);