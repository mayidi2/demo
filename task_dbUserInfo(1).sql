Create database dbUserInfo;

use dbUserInfo;

CREATE TABLE UserDetails (
    LoginID VARCHAR(20) PRIMARY KEY,
    Password VARCHAR(100) NOT NULL,
    Email VARCHAR(100) NOT NULL UNIQUE,
    PhoneNum CHAR(10) NOT NULL UNIQUE,
    UserName VARCHAR(50) NOT NULL
);

INSERT INTO UserDetails
VALUES
('JOHN001','John@123','john.smith@gmail.com','9123456780','John Smith'),
('EMMA002','Emma@456','emma.wilson@gmail.com','9123456781','Emma Wilson'),
('DAVID003','David@789','david.brown@outlook.com','9123456782','David Brown'),
('SOPHIA004','Sophia@111','sophia.taylor@gmail.com','9123456783','Sophia Taylor'),
('MICHAEL005','Michael@222','michael.jones@outlook.com','9123456784','Michael Jones');

CREATE TABLE UserProfile (
    ProfileID INT PRIMARY KEY,
    LoginID VARCHAR(20) NOT NULL,
    DateOfBirth DATE,
    Gender CHAR(1) CHECK (Gender IN ('M', 'F', 'O')),
    City VARCHAR(50),
    Country VARCHAR(50),
    RegistrationDate DATE DEFAULT CURRENT_DATE,
    AccountStatus VARCHAR(10) DEFAULT 'Active'
        CHECK (AccountStatus IN ('Active', 'Inactive', 'Blocked')),
    FOREIGN KEY (LoginID) REFERENCES UserDetails(LoginID)
);

INSERT INTO UserProfile
VALUES
(1,'JOHN001','1994-03-15','M','London','United Kingdom','2024-01-10','Active'),
(2,'EMMA002','1997-08-22','F','Manchester','United Kingdom','2024-02-18','Active'),
(3,'DAVID003','1992-11-09','M','New York','United States','2024-03-25','Inactive'),
(4,'SOPHIA004','1999-05-30','F','Sydney','Australia','2024-04-12','Blocked'),
(5,'MICHAEL005','1995-01-18','M','Toronto','Canada','2024-05-08','Active');

----------- TASK ------------
-- 1. Display only username and email from the UserDetails table

-- 2. Display unique countries from the UserProfile table

-- 3. Find users from the United Kingdom from the UserProfile table

-- 4. Display users whose account status is 'Active' from the UserProfile table

-- 5. Display Users born after 1995 from the UserProfile table

-- 6. Find Users from the UK AND Active from the UserProfile table

-- 7. Find Users NOT in the UK from the UserProfile table

-- 8. Display users whose email contains 'gmail' from the UserDetails table

-- 9. Find users born between 1995 and 1998 from the UserProfile table

-- 10. Find Username starts with 'J' from the UserDetails table

-- 11. Sort the data from the UserDetails table by Username in ascending order

-- 12. Sort by date of birth (youngest first) from the UserProfile table

-- 13. Display first three users from the UserDetails table

-- 14. Display the total number of users in the UserDetails table

-- 15. Count active users from the UserProfile table

-- 16. Total number of users from each country in the UserProfile table

-- 17. Countries having more than one user from the UserProfile table

-- 18. Join both tables

-- 19. Find Active users from the UK

-- 20. Display users with their age not the date of birth from the UserProfile table