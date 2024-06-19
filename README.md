<div align='center'>

<h1>Quizzenia</h1>
<p>A user-friendly online quiz web application</p>

<h4> <span> · </span> <a href="https://github.com/Absolute-oreZ/quizzenia/blob/master/README.md"> Documentation </a> <span> · </span> <a href="https://github.com/Absolute-oreZ/quizzenia/issues"> Report Bug </a> <span> · </span> <a href="https://github.com/Absolute-oreZ/quizzenia/issues"> Request Feature </a> </h4>

</div>

# :notebook_with_decorative_cover: Table of Contents

- [About the Project](#star2-about-the-project)
- [Roadmap](#compass-roadmap)
- [FAQ](#grey_question-faq)
- [License](#warning-license)
- [Contact](#handshake-contact)

## :star2: About the Project

### :camera: Screenshots

<div align="center"> <a href=""><img src="https://i.postimg.cc/yxKsXswW/Screenshot-2024-06-19-210737.jpg" alt='image' width='800'/></a> </div>
<div align="center"> <a href=""><img src="https://i.postimg.cc/RZYmpcjk/Screenshot-2024-06-19-210910.jpg" alt='image' width='800'/></a> </div>
<div align="center"> <a href=""><img src="https://i.postimg.cc/ncvTPZNp/Screenshot-2024-06-19-211422.jpg" alt='image' width='800'/></a> </div>
<div align="center"> <a href=""><img src="https://i.postimg.cc/9QC13KjT/Screenshot-2024-06-19-211514.jpg" alt='image' width='800'/></a> </div>

### :space_invader: Tech Stack

<details> <summary>Client</summary> <ul>
<li><a href="https://www.oracle.com/java/technologies/jspt.html">JSP</a></li>
<li><a href="https://tailwindcss.com/">Tailwind CSS</a></li>
</ul> </details>
<details> <summary>Server</summary> <ul>
<li><a href="https://tomcat.apache.org/">TomCat</a></li>
</ul> </details>
<details> <summary>Database</summary> <ul>
<li><a href="https://i.postimg.cc/RZYmpcjk/Screenshot-2024-06-19-210910.jpg">MySQL</a></li>
</ul> </details>

### :dart: Features

- User Friendly
- Create Quiz
- Register Account For Students
- Attempt Quiz
- Manage Profile

## :toolbox: Getting Started

### :bangbang: Prerequisites

- Install Tomcat Server<a href="https://tomcat.apache.org/download-90.cgi"> Here</a>
- Install Java JDK<a href="https://www.oracle.com/my/java/technologies/downloads/"> Here</a>
- Install Java IDE (Netbeans or any other)<a href="https://netbeans.apache.org/front/main/download/nb22/"> Here</a>

### :running: Run Locally

Clone the project

```bash
Absolute-oreZ
```

Go to the project directory

```bash
cd quizzenia
```

create a local database with following script

```bash
-- Create the database
-- DROP DATABASE IF EXISTS quizzenia;
CREATE DATABASE IF NOT EXISTS quizzenia;
USE quizzenia;

-- Create the Class table
CREATE TABLE IF NOT EXISTS class (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(225) NOT NULL
);

-- Create the Users table
CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role ENUM('Lecturer', 'Student') NOT NULL,
    classId INT,
    FOREIGN KEY (classId) REFERENCES class(id) ON UPDATE CASCADE ON DELETE SET NULL
);

-- Create auto-increment counter tables
CREATE TABLE IF NOT EXISTS studentCounter (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dummy VARCHAR(1)
);

CREATE TABLE IF NOT EXISTS lecturerCounter (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dummy VARCHAR(1)
);

-- Create the Quiz table
CREATE TABLE IF NOT EXISTS quiz (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(225) NOT NULL,
    description TEXT NOT NULL,
    classId INT NOT NULL,
    noOfQuestion INT NOT NULL,
    startTime TIMESTAMP NOT NULL,
    endTime TIMESTAMP NOT NULL,
    durationMinutes INT NOT NULL,
    attempts INT DEFAULT 0,
    status ENUM('draft', 'published', 'closed') DEFAULT 'draft',
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    draftedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    lastEditedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (classId) REFERENCES class(id) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Create the Question table
CREATE TABLE IF NOT EXISTS question (
    id INT AUTO_INCREMENT PRIMARY KEY,
    quizId INT NOT NULL,
    questionText TEXT NOT NULL,
    questionPicture BLOB,
    FOREIGN KEY (quizId) REFERENCES quiz(id) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Create the Answer table
CREATE TABLE IF NOT EXISTS answer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    questionId INT NOT NULL,
    answerText TEXT NOT NULL,
    answerPicture BLOB,
    isCorrect TINYINT(1),
    FOREIGN KEY (questionId) REFERENCES question(id) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Create the QuizAttempt table
CREATE TABLE IF NOT EXISTS quizAttempt (
    id INT AUTO_INCREMENT PRIMARY KEY,
    studentId VARCHAR(10) NOT NULL,
    quizId INT NOT NULL,
    startTime TIMESTAMP NOT NULL,
    endTime TIMESTAMP,
    timeTakenMinutes INT,
    marks DECIMAL(5,2) NOT NULL DEFAULT 0,
    FOREIGN KEY (studentId) REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (quizId) REFERENCES quiz(id) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Create the UserAnswers table
CREATE TABLE IF NOT EXISTS userAnswers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    quizAttemptId INT NOT NULL,
    questionId INT NOT NULL,
    answerId INT NOT NULL,
    FOREIGN KEY (quizAttemptId) REFERENCES quizAttempt(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (questionId) REFERENCES question(id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (answerId) REFERENCES answer(id) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Create the RecentActivities table
CREATE TABLE IF NOT EXISTS recentActivities (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userId VARCHAR(10) NOT NULL,
    action VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Add indexes to frequently queried columns
CREATE INDEX idx_users_role ON users(role);
CREATE INDEX idx_quiz_classId ON quiz(classId);
CREATE INDEX idx_quizAttempt_studentId ON quizAttempt(studentId);
CREATE INDEX idx_quizAttempt_quizId ON quizAttempt(quizId);
CREATE INDEX idx_question_quizId ON question(quizId);

-- Add constraints for data integrity
ALTER TABLE quizAttempt
    ADD CONSTRAINT chk_quizAttempt_marks CHECK (marks >= 0 AND marks <= 100);

-- Update status to closed when its over the time
UPDATE quiz
	SET
		STATUS = IF(CURRENT_TIMESTAMP > endTime, "closed","draft" OR "published");

-- Create a trigger to generate UserID before inserting a new record
DELIMITER $$
CREATE TRIGGER before_insert_user
BEFORE INSERT ON users
FOR EACH ROW
BEGIN
    IF NEW.role = 'Student' THEN
        INSERT INTO studentCounter (dummy) VALUES ('x');
        SET NEW.id = CONCAT('S', LPAD(LAST_INSERT_ID(), 4, '0'));
    ELSEIF NEW.role = 'Lecturer' THEN
        INSERT INTO lecturerCounter (dummy) VALUES ('x');
        SET NEW.id = CONCAT('L', LPAD(LAST_INSERT_ID(), 4, '0'));
    END IF;
END$$
DELIMITER ;

-- Update the status of quiz every minute
CREATE EVENT update_quiz_status
ON SCHEDULE EVERY 1 MINUTE
DO
    UPDATE quiz
    SET status = IF(CURRENT_TIMESTAMP > endTime, "closed", status);

-- Sample data inserts
INSERT INTO class (name) VALUES
('K1'),
('K2');

INSERT INTO users (name, email, password, role, classId) VALUES
('Lecturer One', 'lecturer1@example.com', 'password1', 'Lecturer', 1),
('Lecturer Two', 'lecturer2@example.com', 'password2', 'Lecturer', 2),
('Student One', 'student1@example.com', 'password3', 'Student', 1),
('Student Two', 'student2@example.com', 'password4', 'Student', 2);

/*

INSERT INTO quiz (name, description, classId, noOfQuestion, startTime, endTime, durationMinutes, attempts, status, createdAt) VALUES
('Quiz 1', 'Description for Quiz 1', 1, 2, '2024-06-01 10:00:00', '2024-06-01 11:00:00', 60, 2, 'published', '2024-05-31 08:00:00'),
('Quiz 2', 'Description for Quiz 2', 2, 2, '2024-06-02 10:00:00', '2024-06-02 12:00:00', 120, 3, 'published', '2024-06-01 12:00:00');

INSERT INTO question (quizId, questionText) VALUES
(1, 'What is 2+2?'),
(1, 'What is the capital of France?'),
(2, 'What is 3+3?'),
(2, 'What is the capital of Germany?');

INSERT INTO answer (questionId, answerText) VALUES
('3'), ('4'), ('5'), ('6'),
('Berlin'), ('Madrid'), ('Paris'),
('5'), ('6'), ('7'), ('8'),
('Berlin'), ('Munich'), ('Frankfurt'), ('Hamburg');

INSERT INTO quizAttempt (studentId, quizId, startTime, endTime, marks) VALUES
('S0001', 1, '2024-06-01 10:05:00', '2024-06-01 10:30:00', 86.20),
('S0002', 1, '2024-06-01 10:10:00', '2024-06-01 10:40:00', 32.80),
('S0001', 2, '2024-06-02 10:05:00', '2024-06-02 10:30:00', 100.00),
('S0002', 2, '2024-06-02 10:10:00', '2024-06-02 10:40:00', 50.00);

INSERT INTO userAnswers (quizAttemptId, questionId, answerId) VALUES
(1, 1, 2), (1, 2, 7),  -- Correct answers for S0001 in Quiz 1
(2, 1, 1), (2, 2, 5),  -- Incorrect answers for S0002 in Quiz 1
(3, 3, 9), (3, 4, 12), -- Correct answers for S0001 in Quiz 2
(4, 3, 8), (4, 4, 12); -- Mixed answers for S0002 in Quiz 2

INSERT INTO recentActivities (userId, action) VALUES
('L0001', 'Created a new quiz titled "Java Basics"'),
('L0001', 'Updated the quiz "HTML & CSS"'),
('S0001', 'Attempted quiz "HTML & CSS"'),
('L0002', 'Deleted the quiz "Advanced JavaScript"');
*/

/*
SELECT * FROM users;
SELECT * FROM class;
SELECT * FROM quiz;
SELECT * FROM question;
SELECT * FROM useranswers;
SELECT * FROM answer;
SELECT * FROM quizattempt;
SELECT * FROM recentactivities;
*/
```

Run the project
Go to

```bash
localhos:8080/quizziz
```

Use lecturer2@example.com as email and password2 as password to log in as an lecturer!

## :compass: Roadmap

- [x] Manage Quiz
- [x] Attempt Quiz
- [ ] Prevent text copy paste while attempting quiz

## :grey_question: FAQ

- Do I need knowledge in java EE to understand this application
- No. This application is designed for users who does not have deep knowledge in JAVA EE

## :warning: License

Distributed under the no License. See LICENSE.txt for more information.

## :handshake: Contact

yong - - yongchunhao2003@gmail.com

Project Link: [Absolute-oreZ](Absolute-oreZ)
