package com.dsm.pick.domains.repository;

import com.dsm.pick.domains.domain.SchoolClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional
public class ClassCustomRepositoryImpl implements ClassCustomRepository {

    @Autowired
    EntityManager entityManager;

    @Override
    public List<SchoolClass> findByFloor(int floor) {
        return entityManager.createQuery("SELECT c FROM SchoolClass c WHERE c.floor = :floor", SchoolClass.class)
                .setParameter("floor", floor)
                .getResultList();
    }
}
