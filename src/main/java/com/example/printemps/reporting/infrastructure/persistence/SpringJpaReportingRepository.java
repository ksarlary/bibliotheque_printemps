package com.example.printemps.reporting.infrastructure.persistence;

import com.example.printemps.loan.domain.Loan;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringJpaReportingRepository extends JpaRepository<Loan, Long> {

    @Query(value = """
            select w.work_id as workId, w.title as title, w.isbn as isbn,
                count(l.technical_id) as loanCount
            from loan l
            join copy c on c.copy_id = l.copy_id
            join work w on w.technical_id = c.work_id
            group by w.work_id, w.title, w.isbn
            order by count(l.technical_id) desc, w.title asc
            """, nativeQuery = true)
    List<TopBorrowedWorkProjection> findTopBorrowedWorks(Pageable pageable);

    @Query(value = """
            select w.work_id as workId, w.title as title, w.isbn as isbn,
            count(distinct c.technical_id) as copyCount,
            count(distinct l.technical_id) as loanCount,
            (count(distinct l.technical_id) * 1.0) / count(distinct c.technical_id) as rotationRate
        from work w
        join copy c on c.work_id = w.technical_id
        left join loan l on l.copy_id = c.copy_id
        group by w.work_id, w.title, w.isbn
        order by rotationRate desc, loanCount desc, w.title asc
        """, nativeQuery = true)
    List<RotationRateProjection> findRotationRates(Pageable pageable);
}