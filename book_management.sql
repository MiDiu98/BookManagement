CREATE DATABASE IF NOT EXISTS `book_management`;
USE `book_management`;

CREATE TABLE IF NOT EXISTS `book_management`.`role` (
	`id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
LOCK TABLES `book_management`.`role` WRITE;
UNLOCK TABLES;

CREATE TABLE IF NOT EXISTS `book_management`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` NVARCHAR(50) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `first_name` NVARCHAR(100) NOT NULL,
  `last_name` VARCHAR(100),
  `enabled` BIT NOT NULL,
  `avatar` NVARCHAR(255) NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `role_id` FOREIGN KEY (`role_id`) REFERENCES `book_management`.`role` (`id`)
  						ON UPDATE RESTRICT
						ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
LOCK TABLES `book_management`.`user` WRITE;
UNLOCK TABLES;


CREATE TABLE IF NOT EXISTS `book_management`.`book` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` NVARCHAR(100) NOT NULL,
  `author` NVARCHAR(100) NOT NULL,
  `description` NVARCHAR(5000) NOT NULL,
  `create_at` DATETIME NOT NULL,
  `update_at` DATETIME,
  `image` 	NVARCHAR(100) NOT NULL,
  `enabled` BIT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `book_management`.`user` (`id`)
  						ON UPDATE RESTRICT
						ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
LOCK TABLES `book_management`.`book` WRITE;
UNLOCK TABLES;

CREATE TABLE IF NOT EXISTS `book_management`.`comment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `message` NVARCHAR(1000) NOT NULL,
  `create_at` DATETIME NOT NULL,
  `update_at` DATETIME,
  `book_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `book_management`.`user` (`id`)
						ON UPDATE RESTRICT
						ON DELETE CASCADE,
  CONSTRAINT `book_id` FOREIGN KEY (`book_id`) REFERENCES `book_management`.`book` (`id`)
						ON UPDATE RESTRICT
						ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
LOCK TABLES `book_management`.`comment` WRITE;
UNLOCK TABLES;
