CREATE TABLE lawyer(
    lawyerId VARCHAR(5) primary key,
    name VARCHAR(100) not null,
    yrsOfExp INT(5),
    address VARCHAR(100),
    email VARCHAR(100),
    contact INT(10)
);


CREATE TABLE client(
    clientId VARCHAR(5) primary key,
    name VARCHAR(100) not null,
    address VARCHAR(100),
    email VARCHAR(100),
    contact INT(10),
    lawyerId VARCHAR(5),
    FOREIGN KEY (lawyerId) references lawyer(lawyerId) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE summon(
    summonId VARCHAR(5) primary key,
    description VARCHAR(100)not null,
    defendant VARCHAR(100),
    lawyerId VARCHAR(5),
    FOREIGN KEY (lawyerId) references lawyer(lawyerId) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE deed(
    deedId VARCHAR(5) primary key,
    description VARCHAR(100) not null,
    date DATE,
    lawyerId VARCHAR(5),
    clientId VARCHAR(5),
    FOREIGN KEY (lawyerId) references lawyer(lawyerId) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (clientId) references client(clientId) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE court(
    courtId VARCHAR(5) primary key,
    location VARCHAR(20) not null
);


CREATE TABLE judge(
    judgeId VARCHAR(5) primary key,
    name VARCHAR(20) not null,
    courtId VARCHAR(5),
    yrsOfExp INT(5),
    FOREIGN KEY (courtId) references court(courtId) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE cases(
    caseId VARCHAR(5) primary key,
    description VARCHAR(100) not null,
    date DATE,
    lawyerId VARCHAR(5),
    clientId VARCHAR(5),
    FOREIGN KEY (lawyerId) references lawyer(lawyerId) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (clientId) references client(clientId) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE charge(
    chargeId VARCHAR(5) primary key,
    description VARCHAR(100)not null,
    amount DECIMAL(10,2)
);


CREATE TABLE lawCase(
    lawyerId VARCHAR(5),
    caseId VARCHAR(5),
    date DATE,
    FOREIGN KEY (lawyerId) references lawyer(lawyerId) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (caseId) references cases(caseId) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE deedCharge(
    deedId VARCHAR(5),
    chargeId VARCHAR(5),
    date DATE,
    FOREIGN KEY (deedId) references deed(deedId) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (chargeId) references charge(chargeId) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE caseCharge(
    caseId VARCHAR(5),
    chargeId VARCHAR(5),
    date DATE,
    FOREIGN KEY (caseId) references cases(caseId) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (chargeId) references charge(chargeId) ON DELETE CASCADE ON UPDATE CASCADE
);

