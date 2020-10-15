package com.dsm.pick.domains.repository;

import com.dsm.pick.domains.domain.Attendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
@Transactional
public class AttendanceCustomRepositoryImpl implements AttendanceCustomRepository {

    @Autowired
    EntityManager entityManager;

    @Override
    public List<Attendance> findByDateAndFloorAndPriority(LocalDate date, int floor, int priority) {
        System.out.println("date : " + date);
        System.out.println("floor : " + floor);
        System.out.println("priority : " + priority);
        List<Attendance> result = entityManager.createQuery("SELECT a FROM Attendance a " +
                "WHERE a.student.club.location.floor = :floor " +
                "AND a.student.location.priority = :priority " +
                "AND a.activity.date = :date", Attendance.class)
                .setParameter("floor", floor)
                .setParameter("priority", priority)
                .setParameter("date", date)
                .getResultList();
        if(result.size() <= 0)
            throw new NoSuchElementException("일치하는 요소가 존재하지 않음");
        return result;
    }

    @Override
    public Attendance findByDateAndFloorAndPriorityAndNumberAndPeriod(LocalDate date, String number, int period) {
        return entityManager.createQuery("SELECT a FROM Attendance a " +
                "WHERE a.activity.date = :date " +
                "AND a.student.num = :number " +
                "AND a.period = :period", Attendance.class)
                .setParameter("date", date)
                .setParameter("number", number)
                .setParameter("period", period)
                .getResultList()
                .stream()
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("일치하는 요소가 존재하지 않음"));
    }
}
