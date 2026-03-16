CREATE TABLE policy (
                        category VARCHAR(20) PRIMARY KEY,
                        max_loans INT NOT NULL CHECK (max_loans > 0),
                        loan_duration_days INT NOT NULL CHECK (loan_duration_days > 0),
                        max_renewals INT NOT NULL CHECK (max_renewals >= 0),
                        CONSTRAINT chk_policy_category
                            CHECK (category IN ('STUDENT', 'TEACHER'))
);

CREATE TABLE library_user  (
                              sso_id VARCHAR(100) PRIMARY KEY,
                              category VARCHAR(20) NOT NULL,
                              status VARCHAR(20) NOT NULL,
                              CONSTRAINT chk_user_category
                                  CHECK (category IN ('STUDENT', 'TEACHER')),
                              CONSTRAINT chk_user_status
                                  CHECK (status IN ('ACTIVE', 'BLOCKED', 'SUSPENDED')),
                              CONSTRAINT fk_user_policy_category
                                  FOREIGN KEY (category) REFERENCES policy(category)
);

INSERT INTO policy (category, max_loans, loan_duration_days, max_renewals)
VALUES
    ('STUDENT', 5, 21, 2),
    ('TEACHER', 20, 60, 3);

INSERT INTO library_user (sso_id, category, status)
VALUES
    ('student001', 'STUDENT', 'ACTIVE'),
    ('student002', 'STUDENT', 'ACTIVE'),
    ('teacher001', 'TEACHER', 'ACTIVE');