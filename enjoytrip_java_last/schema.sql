CREATE
DATABASE IF NOT EXISTS UX;
USE
UX;

-- Table for MemberDto
CREATE TABLE `members`
(
    `userId`      VARCHAR(255) NOT NULL,
    `userName`    VARCHAR(255) NOT NULL,
    `userPwd`     VARCHAR(255) NOT NULL,
    `userSalt`    VARCHAR(255) NOT NULL,
    `emailId`     VARCHAR(255) NOT NULL,
    `emailDomain` VARCHAR(255) NOT NULL,
    `joinDate`    DATETIME     NOT NULL,
    `token`       VARCHAR(1000),
    PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table for BoardDto
CREATE TABLE `boards`
(
    `articleNo`    INT AUTO_INCREMENT NOT NULL,
    `userId`       VARCHAR(255) NOT NULL,
    `subject`      VARCHAR(255) NOT NULL,
    `content`      TEXT         NOT NULL,
    `hit`          INT          NOT NULL DEFAULT 0,
    `registerTime` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `type`         VARCHAR(50)  NOT NULL,
    `ref`          INT          NOT NULL,
    `step`         INT          NOT NULL DEFAULT 0,
    `depth`        INT          NOT NULL DEFAULT 0,
    PRIMARY KEY (`articleNo`),
    FOREIGN KEY (`userId`) REFERENCES `members` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



CREATE TABLE TravelRoute
(
    `travelRouteNo` INT AUTO_INCREMENT PRIMARY KEY,
    `userId`        VARCHAR(255) NOT NULL,
    `subject`       VARCHAR(255) NOT NULL,
    `content`       TEXT         ,
    `hit`           INT          NOT NULL DEFAULT 0,
    `registerTime`  DATETIME,
    FOREIGN KEY (`userId`) REFERENCES `members` (`userId`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE Marker
(
    `addr1`         VARCHAR(255) NOT NULL,
    `areacode`      INT          NOT NULL,
    `firstimage`    VARCHAR(255),
    `title`         VARCHAR(255) NOT NULL,
    `mapX`          DOUBLE       NOT NULL,
    `mapY`          DOUBLE       NOT NULL,
    `travelRouteNo` INT          NOT NULL,
    `step`          INT          NOT NULL,
    PRIMARY KEY (`travelRouteNo`, `step`),
    FOREIGN KEY (`travelRouteNo`) REFERENCES TravelRoute (`travelRouteNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE comments (
  commentNo INT AUTO_INCREMENT PRIMARY KEY,
  content VARCHAR(255) NOT NULL,
  articleNo INT NOT NULL,
  userId varchar(255) NOT NULL,
  FOREIGN KEY (articleNo) REFERENCES boards(articleNo) ON DELETE CASCADE,
  FOREIGN KEY (userId) REFERENCES members(userId) ON DELETE CASCADE
);