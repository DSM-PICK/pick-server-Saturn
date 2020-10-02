package com.dsm.pick.domains.repository;

import com.dsm.pick.domains.domain.Club;
import com.dsm.pick.utils.exception.ClubNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional
public class ClubCustomRepositoryImpl implements ClubCustomRepository {

    @Autowired
    EntityManager entityManager;

    @Override
    public List<Club> findByFloor(int floor) {
        return entityManager.createQuery("SELECT c FROM Club c INNER JOIN c.location l WHERE l.floor = :floor", Club.class)
                .setParameter("floor", floor)
                .getResultList();
    }

    @Override
    public Club findByFloorAndPriority(int floor, int priority) {
        return entityManager.createQuery("SELECT c FROM Club c INNER JOIN c.location l WHERE l.floor = :floor AND l.priority = :priority", Club.class)
                .setParameter("floor", floor)
                .setParameter("priority", priority)
                .getResultList()
                .stream()
                .findAny()
                .orElseThrow(() -> new ClubNotFoundException());
    }
}
