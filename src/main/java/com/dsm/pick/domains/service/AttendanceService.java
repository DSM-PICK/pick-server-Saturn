package com.dsm.pick.domains.service;

import com.dsm.pick.domains.domain.Activity;
import com.dsm.pick.domains.domain.Attendance;
import com.dsm.pick.domains.domain.Club;
import com.dsm.pick.domains.domain.SchoolClass;
import com.dsm.pick.domains.repository.ActivityRepository;
import com.dsm.pick.domains.repository.AttendanceRepository;
import com.dsm.pick.domains.repository.ClassRepository;
import com.dsm.pick.domains.repository.ClubRepository;
import com.dsm.pick.utils.exception.ActivityNotFoundException;
import com.dsm.pick.utils.exception.NonExistFloorException;
import com.dsm.pick.utils.exception.NotClubAndSelfStudyException;
import com.dsm.pick.utils.form.AttendanceListForm;
import com.dsm.pick.utils.form.AttendanceStateForm;
import com.dsm.pick.utils.form.ClubAndClassInformationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Transactional
public class AttendanceService {

    private final ClassRepository classRepository;
    private final ClubRepository clubRepository;
    private final ActivityRepository activityRepository;
    private final AttendanceRepository attendanceRepository;

    @Autowired
    public AttendanceService(ClassRepository classRepository, ClubRepository clubRepository, ActivityRepository activityRepository, AttendanceRepository attendanceRepository) {
        this.classRepository = classRepository;
        this.clubRepository = clubRepository;
        this.activityRepository = activityRepository;
        this.attendanceRepository = attendanceRepository;
    }

    public List<ClubAndClassInformationForm> getNavigationInformation(int floor) {

        List<ClubAndClassInformationForm> form = new ArrayList<>();

        String schedule = getTodaySchedule();
        if(schedule.equals("club")) {

            if(!(1 <= floor && floor <= 4)) {
                throw new NonExistFloorException("1, 2, 3, 4층이 아닙니다.");
            }

            List<Club> clubList = clubRepository.findByFloor(floor);
            AtomicBoolean isFirst = new AtomicBoolean(true);
            final Comparator<Club> comparator =
                    Comparator.comparingInt(c -> c.getLocation().getPriority());

            clubList.stream()
                    .sorted(comparator)
                    .forEach(c -> {
                        ClubAndClassInformationForm element = new ClubAndClassInformationForm();

                        element.setName(c.getName());
                        element.setLocation(c.getLocation().getLocation());
                        if(isFirst.get()) {
                            element.setDone("done");
                            isFirst.set(false);
                        } else {
                            element.setDone("none");
                        }
                        element.setPriority(String.valueOf(c.getLocation().getPriority()));

                        form.add(element);
                    });

        } else if(schedule.equals("self-study")) {

            if(!(2 <= floor && floor <= 4)) {
                throw new NonExistFloorException("2, 3, 4층이 아닙니다.");
            }

            List<SchoolClass> classList = classRepository.findByFloor(floor);
            AtomicBoolean isFirst = new AtomicBoolean(true);

            classList.stream()
                    .sorted(Comparator.comparing(SchoolClass::getPriority))
                    .forEach(c -> {
                        ClubAndClassInformationForm element = new ClubAndClassInformationForm();

                        element.setName(c.getName());
                        element.setLocation(c.getName());
                        if(isFirst.get()) {
                            element.setDone("done");
                            isFirst.set(false);
                        } else {
                            element.setDone("none");
                        }
                        element.setPriority(String.valueOf(c.getPriority()));

                        form.add(element);
                    });
        } else {
            throw new NotClubAndSelfStudyException("schedule 이 club 또는 self-study 가 아닙니다.");
        }

        return form;
    }

    public String getTodayTeacherName(String date, int floor) {
        int todayYear = LocalDate.now().getYear();
        int todayMonth = Integer.valueOf(date.substring(0, 2)).intValue();
        int todayDayOfMonth = Integer.valueOf(date.substring(2, 4)).intValue();

        LocalDate id = LocalDate.of(todayYear, todayMonth, todayDayOfMonth);

        Optional<Activity> activity = activityRepository.findById(id);
        String teacherName;
        if(floor == 1) {
            teacherName = null;
        } else if(floor == 2) {
            teacherName = activity.map(a -> a.getSecondFloorTeacher().getName())
                    .orElseThrow(ActivityNotFoundException::new);
        } else if(floor == 3) {
            teacherName = activity.map(a -> a.getThirdFloorTeacher().getName())
                    .orElseThrow(ActivityNotFoundException::new);
        } else if(floor == 4) {
            teacherName = activity.map(a -> a.getForthFloorTeacher().getName())
                    .orElseThrow(ActivityNotFoundException::new);
        } else {
            throw new NonExistFloorException("floor 가 2, 3, 4가 아님");
        }

        return teacherName;
    }

    public Club getClubHeadAndName(int floor, int priority) {
        return clubRepository.findByFloorAndPriority(floor, priority);
    }

    public SchoolClass getClassName(int floor, int priority) {
        return classRepository.findByFloorAndPriority(floor, priority);
    }

    public List<AttendanceListForm> getAttendanceList(LocalDate date, int floor, int priority) {
        List<AttendanceListForm> form = new ArrayList<>();
        final Comparator<Attendance> comparator =
                Comparator.comparing(c -> c.getStudent().getNum());

        List<Attendance> attendanceList;
        String schedule = getTodaySchedule();
        if(schedule.equals("club")) {
            attendanceList = attendanceRepository.findByDateAndFloorAndPriorityWithClub(date, floor, priority);
        } else if(schedule.equals("self-study")) {
            attendanceList = attendanceRepository.findByDateAndFloorAndPriorityWithClass(date, floor, priority);
        } else {
            throw new NotClubAndSelfStudyException("schedule is not club or self-study");
        }

        attendanceList.stream()
                .sorted(comparator);

        AttendanceListForm attendanceListForm = null;
        for(Attendance a : attendanceList) {
            if(attendanceListForm == null) {
                attendanceListForm = new AttendanceListForm();
            } else if(!(attendanceListForm.getGradeClassNumber().equals(a.getStudent().getNum()))) {
                form.add(attendanceListForm);
                attendanceListForm = new AttendanceListForm();
            }

            attendanceListForm.setGradeClassNumber(a.getStudent().getNum());
            attendanceListForm.setName(a.getStudent().getName());


            if(attendanceListForm.getState() == null)
                attendanceListForm.setState(new AttendanceStateForm());

            int period = a.getPeriod();
            if(period == 7)
                attendanceListForm.getState().setSeven(a.getState());
            else if(period == 8)
                attendanceListForm.getState().setEight(a.getState());
            else if(period == 9)
                attendanceListForm.getState().setNine(a.getState());
            else if(period == 10)
                attendanceListForm.getState().setTen(a.getState());
        }
        if(attendanceListForm != null)
            form.add(attendanceListForm);

        return form;
    }

    public void updateAttendance(LocalDate date, String number, int period, String state) {
        Attendance attendance = attendanceRepository.findByDateAndNumberAndPeriod(
                date,
                number,
                period
        );
        attendance.setState(state);
    }

    public String getTodaySchedule() {
        Activity activity = activityRepository.findById(LocalDate.now())
                .orElseThrow(ActivityNotFoundException::new);
        return activity.getSchedule();
    }
}
