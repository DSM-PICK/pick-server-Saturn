package com.dsm.pick.domains.repository;

import com.dsm.pick.domains.domain.SchoolClass;
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
    public List<SchoolClass> findByFloor(int floor) {
        return null;
    }
}
