DROP DATABASE SGAssociates;

CREATE DATABASE SGAssociates;

USE SGAssociates;

CREATE TABLE user(
    userId VARCHAR(6) primary key not null ,
    name VARCHAR(50) not null,
    password VARCHAR(20) not null
);

CREATE TABLE lawyer(
    lawyerId VARCHAR(6) primary key,
    name VARCHAR(100) not null,
    yrsOfExp INT(5),
    address VARCHAR(100),
    email VARCHAR(100),
    contact INT(10)
);

CREATE TABLE client(
    clientId VARCHAR(6) primary key,
    name VARCHAR(100) not null,
    address VARCHAR(100),
    email VARCHAR(100),
    contact INT(10),
    lawyerId VARCHAR(6),
    FOREIGN KEY (lawyerId) references lawyer(lawyerId) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE summon(
    summonId VARCHAR(6) primary key,
    description VARCHAR(100)not null,
    defendant VARCHAR(100),
    lawyerId VARCHAR(6),
    date DATE,
    FOREIGN KEY (lawyerId) references lawyer(lawyerId) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE deed(
    deedId VARCHAR(6) primary key,
    description VARCHAR(100) not null,
    type VARCHAR(20),
    date DATE,
    lawyerId VARCHAR(6),
    clientId VARCHAR(6),
    FOREIGN KEY (lawyerId) references lawyer(lawyerId) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (clientId) references client(clientId) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE court(
    courtId VARCHAR(6) primary key,
    location VARCHAR(20) not null
);

CREATE TABLE judge(
    judgeId VARCHAR(6) primary key,
    name VARCHAR(20) not null,
    courtId VARCHAR(6),
    yrsOfExp INT(5),
    FOREIGN KEY (courtId) references court(courtId) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE cases(
    caseId VARCHAR(6) primary key,
    description VARCHAR(100) not null,
    type VARCHAR(20),
    date DATE,
    clientId VARCHAR(6),
    FOREIGN KEY (clientId) references client(clientId) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE charge(
    chargeId VARCHAR(6) primary key,
    description VARCHAR(100)not null,
    amount DECIMAL(10,2)
);

CREATE TABLE payment(
    payId VARCHAR(6) primary key ,
    clientId VARCHAR(6),
    date DATE,
    amount DECIMAL,
    FOREIGN KEY (lawyerId) references lawyer(lawyerId) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE lawCase(
    lawyerId VARCHAR(6),
    caseId VARCHAR(6),
    date DATE,
    FOREIGN KEY (lawyerId) references lawyer(lawyerId) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (caseId) references cases(caseId) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE deedCharge (
    payId VARCHAR(6),
    deedId VARCHAR(6),
    lawyerId VARCHAR(6),
    chargeId VARCHAR(6),
    date DATE,
    amount DECIMAL(10, 2),
    clientId VARCHAR(6),
    FOREIGN KEY (payId) references payment(payId) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (lawyerId) references lawyer(lawyerId) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (deedId) REFERENCES deed(deedId) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (clientId) REFERENCES client(clientId) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (chargeId) REFERENCES charge(chargeId) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE caseCharge(
    payId VARCHAR(6),
    caseId VARCHAR(6),
    lawyerId VARCHAR(6),
    chargeId VARCHAR(6),
    date DATE,
    amount DECIMAL(10,2),
    clientId VARCHAR(6),
    FOREIGN KEY (payId) references payment(payId) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (caseId) references cases(caseId) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (lawyerId) references lawyer(lawyerId) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (clientId) references client(clientId) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (chargeId) references charge(chargeId) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE salary(
    lawyerId VARCHAR(6),
    monthlySalary DECIMAL,
    FOREIGN KEY (lawyerId) references lawyer(lawyerId) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO user VALUES ('U001', 'Nes', '1234');

INSERT INTO lawyer VALUES
('L001', 'Ranjith', 20, 'Moratuwa', 'abc@gmail.com', 0987656765),
('L002', 'John Doe', 10, 'New York', 'john.doe@example.com', 0876543456),
('L003', 'Jane Smith', 15, 'Los Angeles', 'jane.smith@example.com', 0987656787),
('L004', 'Michael Johnson', 5, 'Chicago', 'michael.j@example.com', 0987654554),
('L005', 'Emily Brown', 8, 'Houston', 'emily.b@example.com', 0765456553),
('L006', 'David Lee', 12, 'San Francisco', 'david.lee@example.com', 0876346531),
('L007', 'Sarah Wilson', 18, 'Boston', 'sarah.w@example.com', 0987898776),
('L008', 'Daniel Martinez', 7, 'Miami', 'daniel.m@example.com', 0912343556),
('L009', 'Amanda Taylor', 9, 'Seattle', 'amanda.t@example.com', 0954356778),
('L010', 'Christopher Garcia', 14, 'Dallas', 'chris.g@example.com', 0654345654);

INSERT INTO client VALUES
('C001', 'Sanjeew Fernando', 'Moratuwa', 'xyz@gmail.com', 0453625778, 'L001'),
('C002', 'Alice Johnson', 'New York', 'alice@example.com', 0987653456, 'L002'),
('C003', 'Bob Smith', 'Los Angeles', 'bob@example.com', 0964756382, 'L003'),
('C004', 'Emily Davis', 'Chicago', 'emily@example.com', 0456783629, 'L004'),
('C005', 'Jack Wilson', 'Houston', 'jack@example.com', 0976543212, 'L005'),
('C006', 'Sophia Brown', 'San Francisco', 'sophia@example.com', 0987654567, 'L006'),
('C007', 'Matthew Taylor', 'Boston', 'matthew@example.com', 0876543234, 'L007'),
('C008', 'Emma Martinez', 'Miami', 'emma@example.com', 0123456778, 'L008'),
('C009', 'Daniel Johnson', 'Seattle', 'daniel@example.com', 0234321778, 'L009'),
('C010', 'Olivia Garcia', 'Dallas', 'olivia@example.com', 0765437897, 'L010');

INSERT INTO summon VALUES ('S001', 'Property Dispute', 'John Smith', 'L001', '2024-04-01');
INSERT INTO summon VALUES ('S002', 'Contract Breach', 'Emily Johnson', 'L002', '2024-04-05');
INSERT INTO summon VALUES ('S003', 'Personal Injury', 'Michael Brown', 'L003', '2024-04-10');
INSERT INTO summon VALUES ('S004', 'Divorce Proceedings', 'Sarah Wilson', 'L004', '2024-04-15');
INSERT INTO summon VALUES ('S005', 'Tax Evasion', 'Daniel Martinez', 'L005', '2024-04-20');
INSERT INTO summon VALUES ('S006', 'Employment Dispute', 'Amanda Taylor', 'L006', '2024-04-25');
INSERT INTO summon VALUES ('S007', 'Intellectual Property Theft', 'Christopher Garcia', 'L007', '2024-04-30');
INSERT INTO summon VALUES ('S008', 'Fraud Case', 'Olivia Rodriguez', 'L008', '2024-05-01');
INSERT INTO summon VALUES ('S009', 'Wrongful Termination', 'David Lee', 'L009', '2024-05-05');
INSERT INTO summon VALUES ('S010', 'Product Liability', 'Jane Smith', 'L010', '2024-05-10');

INSERT INTO deed VALUES ('D001', 'Property Sale Agreement', 'Sale', '2024-04-01', 'L001', 'C001');
INSERT INTO deed VALUES ('D002', 'Lease Agreement', 'Lease', '2024-04-05', 'L002', 'C002');
INSERT INTO deed VALUES ('D003', 'Power of Attorney', 'Legal', '2024-04-10', 'L003', 'C003');
INSERT INTO deed VALUES ('D004', 'Will', 'Legal', '2024-04-15', 'L004', 'C004');
INSERT INTO deed VALUES ('D005', 'Loan Agreement', 'Financial', '2024-04-20', 'L005', 'C005');
INSERT INTO deed VALUES ('D006', 'Partnership Agreement', 'Business', '2024-04-25', 'L006', 'C006');
INSERT INTO deed VALUES ('D007', 'Intellectual Property Assignment', 'Legal', '2024-04-30', 'L007', 'C007');
INSERT INTO deed VALUES ('D008', 'Employment Contract', 'Legal', '2024-05-01', 'L008', 'C008');
INSERT INTO deed VALUES ('D009', 'Trust Deed', 'Legal', '2024-05-05', 'L009', 'C009');
INSERT INTO deed VALUES ('D010', 'Purchase Agreement', 'Sale', '2024-05-10', 'L010', 'C010');

INSERT INTO court VALUES ('CT001', 'Katubedda');
INSERT INTO court VALUES ('CT002', 'Rawathawatte ');
INSERT INTO court VALUES ('CT003', 'Kadalana');
INSERT INTO court VALUES ('CT004', 'Uyana');
INSERT INTO court VALUES ('CT005', 'Koralawella');
INSERT INTO court VALUES ('CT006', 'Panadura');
INSERT INTO court VALUES ('CT007', 'Ratmalana');
INSERT INTO court VALUES ('CT008', 'Pinwatta');
INSERT INTO court VALUES ('CT009', 'Angulana');
INSERT INTO court VALUES ('CT010', 'Moratuwa');

INSERT INTO judge VALUES ('J001', 'Samantha Perera', 'CT001', 15);
INSERT INTO judge VALUES ('J002', 'Chathurika Silva', 'CT002', 20);
INSERT INTO judge VALUES ('J003', 'Malith Fernando', 'CT003', 18);
INSERT INTO judge VALUES ('J004', 'Kasun Bandara', 'CT004', 22);
INSERT INTO judge VALUES ('J005', 'Dilanka Rajapakse', 'CT005', 17);
INSERT INTO judge VALUES ('J006', 'Kumari Gunathilaka', 'CT006', 21);
INSERT INTO judge VALUES ('J007', 'Chaminda Jayawardena', 'CT007', 19);
INSERT INTO judge VALUES ('J008', 'Anusha Perera', 'CT008', 16);
INSERT INTO judge VALUES ('J009', 'Tharindu Liyanage', 'CT009', 23);
INSERT INTO judge VALUES ('J010', 'Nadeesha Karunaratne', 'CT010', 24);

INSERT INTO cases VALUES ('CA001', 'Property Dispute', 'Civil', '2024-04-01', 'C001');
INSERT INTO cases VALUES ('CA002', 'Contract Breach', 'Civil', '2024-04-05', 'C002');
INSERT INTO cases VALUES ('CA003', 'Personal Injury', 'Civil', '2024-04-10', 'C003');
INSERT INTO cases VALUES ('CA004', 'Divorce Proceedings', 'Family', '2024-04-15', 'C004');
INSERT INTO cases VALUES ('CA005', 'Tax Evasion', 'Criminal', '2024-04-20', 'C005');
INSERT INTO cases VALUES ('CA006', 'Employment Dispute', 'Civil', '2024-04-25', 'C006');
INSERT INTO cases VALUES ('CA007', 'Intellectual Property Theft', 'Civil', '2024-04-30', 'C007');
INSERT INTO cases VALUES ('CA008', 'Fraud Case', 'Criminal', '2024-05-01', 'C008');
INSERT INTO cases VALUES ('CA009', 'Wrongful Termination', 'Civil', '2024-05-05', 'C009');
INSERT INTO cases VALUES ('CA010', 'Product Liability', 'Civil', '2024-05-10', 'C010');

INSERT INTO charge VALUES ('CH001', 'Consultation Fee', 1500.00);
INSERT INTO charge VALUES ('CH002', 'Legal Research', 1250.00);
INSERT INTO charge VALUES ('CH003', 'Court Filing Fee', 1100.00);
INSERT INTO charge VALUES ('CH004', 'Document Drafting', 1800.00);
INSERT INTO charge VALUES ('CH005', 'Witness Preparation', 1300.00);
INSERT INTO charge VALUES ('CH006', 'Motion Hearing', 1500.00);
INSERT INTO charge VALUES ('CH007', 'Trial Preparation', 1600.00);
INSERT INTO charge VALUES ('CH008', 'Deposition', 1750.00);
INSERT INTO charge VALUES ('CH009', 'Mediation', 2200.00);
INSERT INTO charge VALUES ('CH010', 'Arbitration', 2100.00);
INSERT INTO charge VALUES ('CH011', 'Settlement Negotiation', 3000.00);
INSERT INTO charge VALUES ('CH012', 'Expert Witness Fee', 2800.00);
INSERT INTO charge VALUES ('CH013', 'Appeal Filing Fee', 1900.00);
INSERT INTO charge VALUES ('CH014', 'Legal Representation Fee', 2600.00);

INSERT INTO payment VALUES ('P001', 'L001', '2024-04-08', 10000);
INSERT INTO payment VALUES ('P002', 'L002', '2024-04-09', 20000);
INSERT INTO payment VALUES ('P003', 'L003', '2024-04-10', 12000);


INSERT INTO lawCase VALUES ('L001', 'CA001', '2024-04-01');
INSERT INTO lawCase VALUES ('L002', 'CA002', '2024-04-02');
INSERT INTO lawCase VALUES ('L003', 'CA003', '2024-04-03');
INSERT INTO lawCase VALUES ('L004', 'CA004', '2024-04-04');
INSERT INTO lawCase VALUES ('L005', 'CA005', '2024-04-05');
INSERT INTO lawCase VALUES ('L006', 'CA006', '2024-04-06');
INSERT INTO lawCase VALUES ('L007', 'CA007', '2024-04-07');
INSERT INTO lawCase VALUES ('L008', 'CA008', '2024-04-08');
INSERT INTO lawCase VALUES ('L009', 'CA009', '2024-04-09');
INSERT INTO lawCase VALUES ('L010', 'CA010', '2024-04-10');

INSERT INTO deedCharge VALUES ('P003', 'D001', 'L001', 'CH001', '2024-04-01', 1500.00, 'C002');
INSERT INTO deedCharge VALUES ('P003', 'D002', 'L002', 'CH002', '2024-04-02', 1250.00, 'C002');
INSERT INTO deedCharge VALUES ('P003', 'D003', 'L003', 'CH003', '2024-04-03', 1100.00, 'C002');
INSERT INTO deedCharge VALUES ('P003', 'D004', 'L004', 'CH004', '2024-04-04', 1800.00, 'C002');

INSERT INTO caseCharge VALUES ('P001', 'CA001', 'L001', 'CH001', '2024-04-01', 3000.00, 'C001');
INSERT INTO caseCharge VALUES ('P001', 'CA001', 'L001', 'CH002', '2024-04-02', 2800.00, 'C001');
INSERT INTO caseCharge VALUES ('P001', 'CA001', 'L001', 'CH003', '2024-04-03', 1900.00, 'C001');
INSERT INTO caseCharge VALUES ('P001', 'CA001', 'L001', 'CH004', '2024-04-04', 2600.00, 'C001');
INSERT INTO caseCharge VALUES ('P002', 'CA005', 'L003', 'CH005', '2024-04-05', 950.00, 'C006');
INSERT INTO caseCharge VALUES ('P002', 'CA005', 'L005', 'CH006', '2024-04-06', 200.00, 'C006');
INSERT INTO caseCharge VALUES ('P002', 'CA005', 'L003', 'CH007', '2024-04-07', 300.00, 'C006');
INSERT INTO caseCharge VALUES ('P002', 'CA005', 'L004', 'CH008', '2024-04-08', 150.00, 'C006');
INSERT INTO caseCharge VALUES ('P002', 'CA005', 'L008', 'CH009', '2024-04-09', 250.00, 'C006');


INSERT INTO salary VALUES ('L001', 10000);
INSERT INTO salary VALUES ('L002', 10000);
INSERT INTO salary VALUES ('L003', 10000);
INSERT INTO salary VALUES ('L004', 10000);


