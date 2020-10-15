//package com.dsm.pick.domains.repository;
//
//import com.dsm.pick.domains.domain.Attendance;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.NoSuchElementException;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class AttendanceCustomRepositoryImplTest {
//
//    @Autowired
//    AttendanceRepository attendanceRepository;
//
//    @Test
//    void findByDateAndFloorAndPriority() {
//        LocalDate date = LocalDate.of(2003, 8, 16);
//        int floor = 0;
//        int priority = 0;
//        assertThrows(NoSuchElementException.class,
//                () -> attendanceRepository.findByDateAndFloorAndPriority(
//                        date,
//                        floor,
//                        priority
//                )
//        );
//    }
//
//    @Test
//    void findByDateAndFloorAndPriorityAndNumberAndPeriod() {
//        LocalDate date = LocalDate.of(2003, 8, 16);
//        String number = "2417";
//        int period = 8;
//        assertThrows(NoSuchElementException.class,
//                () -> attendanceRepository.findByDateAndNumberAndPeriod(
//                        date,
//                        number,
//                        period
//                )
//        );
//    }
//}