INSERT INTO `members` (`userId`, `userName`, `userPwd`, `userSalt`, `emailId`, `emailDomain`, `joinDate`)
VALUES
    ('1234', 'UserTest', '1234', '1234', '1234', 'email.com', NOW()),
    ('user1', 'User One', 'pwd1', 'salt1', 'user1', 'email.com', NOW()),
    ('user2', 'User Two', 'pwd2', 'salt2', 'user2', 'email.com', NOW()),
    ('user3', 'User Three', 'pwd3', 'salt3', 'user3', 'email.com', NOW()),
    ('user4', 'User Four', 'pwd4', 'salt4', 'user4', 'email.com', NOW()),
    ('user5', 'User Five', 'pwd5', 'salt5', 'user5', 'email.com', NOW());


INSERT INTO `boards` (`userId`, `subject`, `content`, `hit`, `registerTime`, `type`)
VALUES
    ('user1', 'Subject 1', 'Content of article 1', 0, NOW(), 'hot'),
    ('user2', 'Subject 2', 'Content of article 2', 0, NOW(), 'notice'),
    ('user3', 'Subject 3', 'Content of article 3', 0, NOW(), 'notice'),
    ('user4', 'Subject 4', 'Content of article 4', 0, NOW(), 'travel'),
    ('user5', 'Subject 5', 'Content of article 5', 0, NOW(), 'travel'),
    ('user1', 'Subject 6', 'Content of article 1', 0, NOW(), 'hot'),
    ('user4', 'Subject 7', 'Content of article 2', 0, NOW(), 'notice'),
    ('user4', 'Subject 8', 'Content of article 3', 0, NOW(), 'notice'),
    ('user4', 'Subject 9', 'Content of article 4', 0, NOW(), 'travel'),
    ('user5', 'Subject 10', 'Content of article 5', 0, NOW(), 'travel'),
    ('user4', 'Subject 11', 'Content of article 1', 0, NOW(), 'hot'),
    ('user4', 'Subject 12', 'Content of article 2', 0, NOW(), 'notice'),
    ('user4', 'Subject 13', 'Content of article 3', 0, NOW(), 'notice'),
    ('user4', 'Subject 14', 'Content of article 4', 0, NOW(), 'travel'),
    ('user4', 'Subject 15', 'Content of article 5', 0, NOW(), 'travel'),
    ('user4', 'Subject 16', 'Content of article 1', 0, NOW(), 'hot'),
    ('user4', 'Subject 17', 'Content of article 2', 0, NOW(), 'notice'),
    ('user4', 'Subject 18', 'Content of article 3', 0, NOW(), 'notice'),
    ('user4', 'Subject 19', 'Content of article 4', 0, NOW(), 'travel'),
    ('user4', 'Subject 20', 'Content of article 5', 0, NOW(), 'travel');