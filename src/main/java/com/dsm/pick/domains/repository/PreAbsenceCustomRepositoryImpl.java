package com.dsm.pick.domains.repository;

import com.dsm.pick.domains.domain.PreAbsence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional
public class PreAbsenceCustomRepositoryImpl implements PreAbsenceCustomRepository {

    @Autowired
    EntityManager entityManager;

    @Override
    public List<PreAbsence> findByCurrentDate() {
        return entityManager.createQuery("SELECT p FROM PreAbsence p " +
                "WHERE p.startDate <= CURDATE()" +
                "AND p.endDate >= CURDATE()", PreAbsence.class)
                .getResultList();
    }
}
