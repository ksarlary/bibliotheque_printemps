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

CREATE TABLE penalty (
                         technical_id BIGSERIAL PRIMARY KEY,
                         penalty_id VARCHAR(100) NOT NULL UNIQUE,
                         user_id VARCHAR(100) NOT NULL,
                         type VARCHAR(30) NOT NULL,
                         amount NUMERIC(10, 2) NOT NULL CHECK (amount >= 0),
                         reason TEXT NOT NULL,
                         status VARCHAR(20) NOT NULL,
                         created_at TIMESTAMP NOT NULL,
                         CONSTRAINT chk_penalty_type
                             CHECK (type IN ('LATE_RETURN', 'LOST_COPY', 'DAMAGED_COPY')),
                         CONSTRAINT chk_penalty_status
                             CHECK (status IN ('ACTIVE', 'PAID', 'CANCELLED')),
                         CONSTRAINT fk_penalty_user
                             FOREIGN KEY (user_id) REFERENCES library_user(sso_id)
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

INSERT INTO penalty (penalty_id, user_id, type, amount, reason, status, created_at)
VALUES
    ('penalty-001', 'student001', 'LATE_RETURN', 10.00, 'Late return', 'ACTIVE', NOW()),
    ('penalty-002', 'student002', 'DAMAGED_COPY', 25.00, 'Damaged copy', 'PAID', NOW());