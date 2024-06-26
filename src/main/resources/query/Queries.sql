
//AssignedWorkDetailsReport
SELECT d.lawyerId, d.deedId, GROUP_CONCAT(lc.caseId) AS caseIds FROM deed d JOIN lawCase lc ON d.lawyerId = lc.lawyerId GROUP BY d.lawyerId, d.deedId;
SELECT d.lawyerId, GROUP_CONCAT(d.deedId) AS deedIds, GROUP_CONCAT(lc.caseId) AS caseIds FROM deed d JOIN lawCase lc ON d.lawyerId = lc.lawyerId GROUP BY d.lawyerId;

//get sum of amount of caseChrge group by caseId and clientId
SELECT caseId, clientId, SUM(amount) AS total_amount FROM caseCharge GROUP BY caseId, clientId;

//bonus calculating
SELECT lawyerId, SUM(amount) total, ROUND(0.5 * SUM(amount)) FROM caseCharge GROUP BY  lawyerId;

//total earning of lawyer
SELECT l.lawyerId, l.name, SUM(dc.amount) AS total_earnings
FROM lawyer l
         LEFT JOIN deedCharge dc ON l.lawyerId = dc.lawyerId
         LEFT JOIN caseCharge cc ON l.lawyerId = cc.lawyerId
GROUP BY l.lawyerId, l.name;



//Calculate the bonus
UPDATE salary AS s
SET s.bonus = (
    SELECT ROUND(0.1 * SUM(dc.amount), 2)
    FROM deedCharge AS dc
    WHERE dc.lawyerId = s.lawyerId
)
WHERE s.lawyerId IN (
    SELECT DISTINCT lawyerId
    FROM deedCharge
);


UPDATE salary AS s
SET s.bonus = (
    SELECT ROUND(0.1 * SUM(cc.amount), 2)
    FROM caseCharge AS cc
    WHERE cc.lawyerId = s.lawyerId
)
WHERE s.lawyerId IN (
    SELECT DISTINCT lawyerId
    FROM caseCharge
);


SELECT
    cl.clientId,
    cl.name AS clientName,
    l.lawyerId,
    l.name AS lawyerName,
    ca.caseId,
    ca.description AS caseDescription,
    ca.type AS caseType,
    ca.date AS caseDate,
    d.deedId,
    d.description AS deedDescription,
    d.type AS deedType,
    d.date AS deedDate
FROM
    client cl
        LEFT JOIN
    lawyer l ON cl.lawyerId = l.lawyerId
        LEFT JOIN
    cases ca ON cl.clientId = ca.clientId
        LEFT JOIN
    deed d ON cl.clientId = d.clientId
WHERE
    cl.clientId = 'C001'
ORDER BY
    ca.date, d.date;




DELIMITER //
CREATE TRIGGER trg_generate_salary_id
    BEFORE INSERT ON salary
    FOR EACH ROW
BEGIN
    DECLARE currentId INT;
    DECLARE nextId VARCHAR(10);

    SELECT MAX(CAST(SUBSTRING(SalaryId, 3) AS UNSIGNED)) INTO currentId FROM salary;

    IF currentId IS NULL THEN
        SET currentId = 0;
END IF;

SET nextId = CONCAT('SA', LPAD(currentId + 1, 3, '0'));

    SET NEW.salaryId = nextId;
END;
//
DELIMITER ;


DELIMITER //
CREATE TRIGGER trg_generate_lawyer_id
    BEFORE INSERT ON lawyer
    FOR EACH ROW
BEGIN
    DECLARE currentId INT;
    DECLARE nextId VARCHAR(10);

    SELECT MAX(CAST(SUBSTRING(salary.lawyerId, 3) AS UNSIGNED)) INTO currentId FROM lawyer;

    IF currentId IS NULL THEN
        SET currentId = 0;
END IF;

SET nextId = CONCAT('L', LPAD(currentId + 1, 3, '0'));

    SET NEW.lawyerId = nextId;
END;
//
DELIMITER ;


// API for AI
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