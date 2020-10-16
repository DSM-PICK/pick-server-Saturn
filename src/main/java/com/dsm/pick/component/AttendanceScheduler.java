package com.dsm.pick.component;

import com.dsm.pick.domains.domain.Activity;
import com.dsm.pick.domains.domain.Attendance;
import com.dsm.pick.domains.domain.PreAbsence;
import com.dsm.pick.domains.domain.Student;
import com.dsm.pick.domains.repository.*;
import com.dsm.pick.utils.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
public class AttendanceScheduler {

    private final static Logger logger = LoggerFactory.getLogger(AttendanceScheduler.class);

    private final static int FIRST_PERIOD = 1;
    private final static int LAST_PERIOD = 10;

    private final AttendanceRepository attendanceRepository;
    private final ActivityRepository activityRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final PreAbsenceRepository preAbsenceRepository;

    @Autowired
    public AttendanceScheduler(AttendanceRepository attendanceRepository, ActivityRepository activityRepository, StudentRepository studentRepository, TeacherRepository teacherRepository, PreAbsenceRepository preAbsenceRepository) {
        this.attendanceRepository = attendanceRepository;
        this.activityRepository = activityRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.preAbsenceRepository = preAbsenceRepository;
    }

//    @Scheduled(cron = "0/1 * * * * *")              // TEST
//    @Scheduled(cron = "1 0 0 * * MON-FRI")          // REAL
    @Scheduled(cron = "0 50 10 * * *")        // TEST
    public void createTodayAttendance() {
        final LocalDate date = LocalDate.now();
        final Activity activity = activityRepository.findById(date)
                .orElseThrow(ActivityNotFoundException::new);
        final List<Student> students = studentRepository.findAll();
        final List<PreAbsence> preAbsencePeriods = preAbsenceRepository.findByCurrentDate();

        final int todayStartPeriod = todayStartPeriod(date);

        students.stream()
                .forEach(s -> {
                    Attendance attendance1 = new Attendance();
                    Attendance attendance2 = new Attendance();
                    Attendance attendance3 = new Attendance();
                    Attendance attendance4 = new Attendance();

                    attendance1.setActivity(activity);
                    attendance2.setActivity(activity);
                    attendance3.setActivity(activity);
                    attendance4.setActivity(activity);

                    attendance1.setStudent(s);
                    attendance2.setStudent(s);
                    attendance3.setStudent(s);
                    attendance4.setStudent(s);

                    attendance1.setPeriod(7);
                    attendance2.setPeriod(8);
                    attendance3.setPeriod(9);
                    attendance4.setPeriod(10);

                    String schedule = activity.getSchedule();
                    int floor = 0;
                    if(schedule.equals("club")) {
                        floor = s.getClub().getLocation().getFloor();
                    } else if(schedule.equals("self-study")) {
                        floor = s.getSchoolClass().getFloor();
                    } else {
                        throw new NotClubAndSelfStudyException("schedule 이 club 또는 self-study 가 아닙니다.");
                    }
                    attendanceTeacherSetting(attendance1, floor, activity);
                    attendanceTeacherSetting(attendance2, floor, activity);
                    attendanceTeacherSetting(attendance3, floor, activity);
                    attendanceTeacherSetting(attendance4, floor, activity);

                    preAbsencePeriods.stream()
                            .filter(p -> p.getStudent().getNum().equals(s.getNum()))
                            .forEach(p -> {
                                if(p.getStartDate().isEqual(date) && p.getEndDate().isEqual(date)) {
                                    for(int i = p.getStartPeriod() ; i <= p.getEndPeriod() ; i++) {
                                        if(i == 7 && todayStartPeriod == 7) {
                                            attendance1.setState(p.getState());
                                        } else if(i == 8) {
                                            attendance2.setState(p.getState());
                                        } else if(i == 9) {
                                            attendance3.setState(p.getState());
                                        } else if(i == 10) {
                                            attendance4.setState(p.getState());
                                        }
                                    }
                                } else if(p.getStartDate().isBefore(date) && p.getEndDate().isEqual(date)) {
                                    for(int i = FIRST_PERIOD ; i <= p.getEndPeriod() ; i++) {
                                        if(i == 7 && todayStartPeriod == 7) {
                                            attendance1.setState(p.getState());
                                        } else if(i == 8) {
                                            attendance2.setState(p.getState());
                                        } else if(i == 9) {
                                            attendance3.setState(p.getState());
                                        } else if(i == 10) {
                                            attendance4.setState(p.getState());
                                        }
                                    }
                                } else if(p.getStartDate().isEqual(date) && p.getEndDate().isAfter(date)) {
                                    for(int i = p.getStartPeriod() ; i <= LAST_PERIOD ; i++) {
                                        if(i == 7 && todayStartPeriod == 7) {
                                            attendance1.setState(p.getState());
                                        } else if(i == 8) {
                                            attendance2.setState(p.getState());
                                        } else if(i == 9) {
                                            attendance3.setState(p.getState());
                                        } else if(i == 10) {
                                            attendance4.setState(p.getState());
                                        }
                                    }
                                } else if(p.getStartDate().isBefore(date) && p.getEndDate().isAfter(date)) {
                                    for(int i = FIRST_PERIOD ; i <= LAST_PERIOD ; i++) {
                                        if(i == 7 && todayStartPeriod == 7) {
                                            attendance1.setState(p.getState());
                                        } else if(i == 8) {
                                            attendance2.setState(p.getState());
                                        } else if(i == 9) {
                                            attendance3.setState(p.getState());
                                        } else if(i == 10) {
                                            attendance4.setState(p.getState());
                                        }
                                    }
                                } else {
                                    throw new InvalidTimeException("잘못된 시간이 저장되어 있음");
                                }
                            });

                    if(attendance1.getState() == null && todayStartPeriod == 7) {
                        attendance1.setState("출석");
                    }
                    if(attendance2.getState() == null) {
                        attendance2.setState("출석");
                    }
                    if(attendance3.getState() == null) {
                        attendance3.setState("출석");
                    }
                    if(attendance4.getState() == null) {
                        attendance4.setState("출석");
                    }

                    if(todayStartPeriod == 7)
                        attendanceRepository.save(attendance1);
                    attendanceRepository.save(attendance2);
                    attendanceRepository.save(attendance3);
                    attendanceRepository.save(attendance4);
                });
    }

    private int todayStartPeriod(LocalDate date) {
        int dayOfWeek = date.getDayOfWeek().getValue();

        if(1 <= dayOfWeek && dayOfWeek <= 4) {
            return 8;
        } else if(dayOfWeek == 5) {
            return 7;
        } else {
            throw new WeekendException("오늘이 주말이라니!!!");
        }
    }

    private void attendanceTeacherSetting(Attendance attendance, int floor, Activity activity) {

        if(floor == 1) {
            attendance.setTeacher(null);
        } else if(floor == 2) {
            attendance.setTeacher(
                    teacherRepository.findById(activity.getSecondFloorTeacher().getId())
                            .orElseThrow(() -> new TeacherNotFoundException("2층 선생님이 존재하지 않음"))
            );
        } else if(floor == 3) {
            attendance.setTeacher(
                    teacherRepository.findById(activity.getThirdFloorTeacher().getId())
                            .orElseThrow(() -> new TeacherNotFoundException("3층 선생님이 존재하지 않음"))
            );
        } else if(floor == 4) {
            attendance.setTeacher(
                    teacherRepository.findById(activity.getForthFloorTeacher().getId())
                            .orElseThrow(() -> new TeacherNotFoundException("4층 선생님이 존재하지 않음"))
            );
        } else {
            throw new NonExistFloorException("floor 가 1, 2, 3, 4 가 아님");
        }
    }
}
