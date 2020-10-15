package com.dsm.pick.domains.repository;

import com.dsm.pick.domains.domain.SchoolClass;
import com.dsm.pick.utils.exception.SchoolClassNotFoundException;
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
        return entityManager.createQuery("SELECT c FROM SchoolClass c " +
                "WHERE c.floor = :floor", SchoolClass.class)
                .setParameter("floor", floor)
                .getResultList();
    }

    @Override
    public SchoolClass findByFloorAndPriority(int floor, int priority) {
        return entityManager.createQuery("SELECT c FROM SchoolClass c " +
                "WHERE c.floor = :floor " +
                "AND c.priority = :priority", SchoolClass.class)
                .setParameter("floor", floor)
                .setParameter("priority", priority)
                .getResultList()
                .stream()
                .findAny()
                .orElseThrow(SchoolClassNotFoundException::new);
    }
}
