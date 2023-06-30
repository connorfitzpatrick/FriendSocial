-- Create database
CREATE DATABASE FRIENDSOCIAL;

-- Switch to database
USE FRIENDSOCIAL;

-- Create the profile table
CREATE TABLE profiles (
  profile_id INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  profile_picture VARCHAR(255),
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  bio TEXT,
  join_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create the profile table
CREATE TABLE posts (
  post_id INT PRIMARY KEY AUTO_INCREMENT,
  profile_id INT NOT NULL,
  content TEXT,
  image_url VARCHAR(255),
  content_type ENUM('text', 'image') NOT NULL,
  timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (profile_id) REFERENCES profiles(profile_id)
);

-- Create the profile table
CREATE TABLE followers (
  follower_id INT NOT NULL,
  following_id INT NOT NULL,
  PRIMARY KEY (follower_id, following_id),
  FOREIGN KEY (follower_id) REFERENCES profiles(profile_id),
  FOREIGN KEY (following_id) REFERENCES profiles(profile_id)
);

-- Create the profile table
CREATE TABLE post_likes (
  like_id INT PRIMARY KEY AUTO_INCREMENT,
  profile_id INT NOT NULL,
  post_id INT NOT NULL,
  timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (profile_id) REFERENCES profiles(profile_id),
  FOREIGN KEY (post_id) REFERENCES posts(post_id)
);

-- Create the profile table
CREATE TABLE comments (
  comment_id INT PRIMARY KEY AUTO_INCREMENT,
  profile_id INT NOT NULL,
  post_id INT NOT NULL,
  content TEXT,
  timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (profile_id) REFERENCES profiles(profile_id),
  FOREIGN KEY (post_id) REFERENCES posts(post_id)
);

-- List Tables:
SHOW TABLES;

-- Show columns in table
SHOW COLUMNS FROM <TABLE_NAME>;