package com.dsm.pick.domains.repository;

import com.dsm.pick.domains.domain.Attendance;
import com.dsm.pick.domains.service.ServerTimeService;
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

    @Autowired
    ServerTimeService serverTimeService;

    @Override
    public List<Attendance> findByDateAndFloorAndPriorityWithClub(LocalDate date, int floor, int priority) {
        return entityManager.createQuery("SELECT a FROM Attendance a " +
                "WHERE a.student.club.location.floor = :floor " +
                "AND a.student.club.location.priority = :priority " +
                "AND a.activity.date = :date", Attendance.class)
                .setParameter("floor", floor)
                .setParameter("priority", priority)
                .setParameter("date", date)
                .getResultList();
    }

    @Override
    public List<Attendance> findByDateAndFloorAndPriorityWithClass(LocalDate date, int floor, int priority) {
        List<Attendance> result = null;

        if(floor == 1) {
            if(serverTimeService.getDayOfWeek(date).equals("월")) {
                result = entityManager.createQuery("SELECT a FROM Attendance a " +
                        "WHERE a.student.isMondaySelfStudy = 1 " +
                        "AND 0 = :priority " +
                        "AND a.activity.date = :date", Attendance.class)
                        .setParameter("priority", priority)
                        .setParameter("date", date)
                        .getResultList();
            } else if(serverTimeService.getDayOfWeek(date).equals("화")) {
                result = entityManager.createQuery("SELECT a FROM Attendance a " +
                        "WHERE a.student.isTuesdaySelfStudy = 1 " +
                        "AND 0 = :priority " +
                        "AND a.activity.date = :date", Attendance.class)
                        .setParameter("priority", priority)
                        .setParameter("date", date)
                        .getResultList();
            }
        } else {
            result = entityManager.createQuery("SELECT a FROM Attendance a " +
                    "WHERE a.student.schoolClass.floor = :floor " +
                    "AND a.student.schoolClass.priority = :priority " +
                    "AND a.activity.date = :date", Attendance.class)
                    .setParameter("floor", floor)
                    .setParameter("priority", priority)
                    .setParameter("date", date)
                    .getResultList();
        }
        return result;
    }

    @Override
    public Attendance findByDateAndNumberAndPeriod(LocalDate date, String number, int period) {
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
