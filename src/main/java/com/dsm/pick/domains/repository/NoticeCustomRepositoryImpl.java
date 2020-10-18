package com.dsm.pick.domains.repository;

import com.dsm.pick.domains.domain.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Repository
@Transactional
public class NoticeCustomRepositoryImpl implements NoticeCustomRepository {

    @Autowired
    EntityManager entityManager;

    @Override
    public List<String> findByDate(LocalDateTime date, String category) {
        List<String> result = entityManager.createQuery("SELECT n FROM Notice n " +
                "WHERE n.date <= :date " +
                "AND n.date >= DATE_SUB(:date, INTERVAL 14 DAY) " +
                "AND n.category = :category", Notice.class)
                .setParameter("date", date)
                .setParameter("category", category)
                .getResultStream()
                .sorted((c1, c2) -> {
                    boolean isAfter = c1.getDate().isAfter(c2.getDate());
                    if (isAfter)
                        return 1;
                    return 0;
                })
                .map(n -> n.getContent())
                .collect(Collectors.toList());
        if(result.size() <= 0)
            return new ArrayList<>();
        return result;
    }
}
