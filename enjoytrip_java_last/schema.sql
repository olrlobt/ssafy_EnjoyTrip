CREATE DATABASE IF NOT EXISTS UX;
USE UX;

-- Table for MemberDto
CREATE TABLE `members` (
                           `userId` VARCHAR(255) NOT NULL,
                           `userName` VARCHAR(255) NOT NULL,
                           `userPwd` VARCHAR(255) NOT NULL,
                           `userSalt` VARCHAR(255) NOT NULL,
                           `emailId` VARCHAR(255) NOT NULL,
                           `emailDomain` VARCHAR(255) NOT NULL,
                           `joinDate` DATETIME NOT NULL,
                           PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table for BoardDto
CREATE TABLE `boards` (
                          `articleNo` INT AUTO_INCREMENT NOT NULL,
                          `userId` VARCHAR(255) NOT NULL,
                          `subject` VARCHAR(255) NOT NULL,
                          `content` TEXT NOT NULL,
                          `hit` INT NOT NULL,
                          `registerTime` DATETIME NOT NULL,
                          `type` VARCHAR(50) NOT NULL,
                          PRIMARY KEY (`articleNo`),
                          FOREIGN KEY (`userId`) REFERENCES `members`(`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;