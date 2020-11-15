package com.dsm.pick.domains.repository;

import com.dsm.pick.domains.domain.Notice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
public class NoticeCustomRepositoryImpl implements NoticeCustomRepository {

    @Autowired
    EntityManager entityManager;

    @Override
    public List<String> findByDate(LocalDateTime endDate, String category) {

        int year = endDate.getYear();
        int month = endDate.getMonthValue();
        int dayOfMonth = endDate.getDayOfMonth();

        int[] dayOfMonths = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        if((year % 400 == 0) || (year % 4 == 0 && year % 100 != 0)) {
            dayOfMonths[1] = 29;
        }

        if(dayOfMonth - 14 >= 1) {
            dayOfMonth -= 14;
        } else {
            dayOfMonth = dayOfMonths[month - 1] - (14 - dayOfMonth);
        }

        LocalDateTime startDate = LocalDateTime.of(
                year,
                month,
                dayOfMonth,
                endDate.getHour(),
                endDate.getMinute(),
                endDate.getSecond(),
                endDate.getNano()
        );

        List<String> result = entityManager.createQuery("SELECT n FROM Notice n " +
                "WHERE n.date <= :endDate " +
                "AND n.date >= :startDate " +
                "AND n.category = :category", Notice.class)
                .setParameter("endDate", Timestamp.valueOf(endDate))
                .setParameter("startDate", Timestamp.valueOf(startDate))
                .setParameter("category", category)
                .getResultStream()
                .sorted((c1, c2) -> c2.getDate().compareTo(c1.getDate()))
                .map(n -> n.getContent())
                .collect(Collectors.toList());
        if(result.size() <= 0)
            return new ArrayList<>();
        return result;
    }
}
