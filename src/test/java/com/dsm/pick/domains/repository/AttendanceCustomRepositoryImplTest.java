package com.dsm.pick.domains.repository;

import com.dsm.pick.domains.domain.Attendance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AttendanceCustomRepositoryImplTest {

    @Autowired
    AttendanceRepository attendanceRepository;

    @Test
    void findByDateAndFloorAndPriority() {
        LocalDate date = LocalDate.now();
        int floor = 3;
        int priority = 4;
        List<Attendance> attendanceList = attendanceRepository.findByDateAndFloorAndPriority(date, floor, priority);
    }

    @Test
    void findByDateAndFloorAndPriorityAndNumberAndPeriod() {
        LocalDate date = LocalDate.now();
        int floor = 3;
        int priority = 4;
        String number = "2417";
        String period = "8";
        Attendance attendance =
                attendanceRepository.findByDateAndFloorAndPriorityAndNumberAndPeriod(
                        date,
                        floor,
                        priority,
                        number,
                        period
                );
    }
}