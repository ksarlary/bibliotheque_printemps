package com.example.printemps.reporting.infrastructure.persistence;

import com.example.printemps.loan.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringJpaReportingRepository extends JpaRepository<Loan, Long> {

    @Query(value = """
            SELECT
                w.work_id,
                w.title,
                w.isbn,
                COUNT(l.technical_id) AS loan_count
            FROM loan l
            JOIN copy c ON c.copy_id = l.copy_id
            JOIN work w ON w.technical_id = c.work_id
            GROUP BY w.work_id, w.title, w.isbn
            ORDER BY COUNT(l.technical_id) DESC, w.title ASC
            """, nativeQuery = true)
    List<Object[]> findTopBorrowedWorks();
}