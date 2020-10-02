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
import com.dsm.pick.utils.exception.NonExistentActivityException;
import com.dsm.pick.utils.form.AttendanceListForm;
import com.dsm.pick.utils.form.AttendanceStateForm;
import com.dsm.pick.utils.form.ClubAndClassInformationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Transactional
public class AttendanceService {

    private ClassRepository classRepository;
    private ClubRepository clubRepository;
    private ActivityRepository activityRepository;
    private AttendanceRepository attendanceRepository;

    @Autowired
    public AttendanceService(ClassRepository classRepository, ClubRepository clubRepository, ActivityRepository activityRepository, AttendanceRepository attendanceRepository) {
        this.classRepository = classRepository;
        this.clubRepository = clubRepository;
        this.activityRepository = activityRepository;
        this.attendanceRepository = attendanceRepository;
    }

    public List<ClubAndClassInformationForm> getNavigationInformation(String activity, int floor) {

        List<ClubAndClassInformationForm> form = new ArrayList<>();

        if(activity.equals("club")) {
            List<Club> clubList = clubRepository.findByFloor(floor);
            AtomicBoolean isFirst = new AtomicBoolean(true);
            final Comparator<Club> comparator =
                    (c1, c2) -> Integer.compare(c1.getLocation().getPriority(), c2.getLocation().getPriority());

            clubList.stream()
                    .sorted(comparator)
                    .forEach(c -> {
                        ClubAndClassInformationForm element = new ClubAndClassInformationForm();

                        element.setName(c.getName());
                        element.setLocation(c.getLocation().getLocation());
                        if(isFirst.get()) {
                            element.setDone(true);
                            isFirst.set(false);
                        } else {
                            element.setDone(false);
                        }
                        element.setPriority(c.getLocation().getPriority());

                        form.add(element);
                    });

        } else if(activity.equals("self-study")) {
            List<SchoolClass> classList = classRepository.findByFloor(floor);
            AtomicBoolean isFirst = new AtomicBoolean(true);

            classList.stream()
                    .sorted(Comparator.comparing(SchoolClass::getPriority))
                    .forEach(c -> {
                        ClubAndClassInformationForm element = new ClubAndClassInformationForm();

                        element.setName(c.getName());
                        element.setLocation(c.getName());
                        if(isFirst.get()) {
                            element.setDone(true);
                            isFirst.set(false);
                        } else {
                            element.setDone(false);
                        }
                        element.setPriority(c.getPriority());

                        form.add(element);
                    });
        } else {
            throw new NonExistentActivityException();
        }

        return form;
    }

    public String getTodayTeacherName(String date, int floor) {
        int todayYear = LocalDate.now().getYear();
        int todayMonth = Integer.valueOf(date.substring(0, 2));
        int todayDayOfMonth = Integer.valueOf(date.substring(2, 4));

        LocalDate id = LocalDate.of(todayYear, todayMonth, todayDayOfMonth);

        Optional<Activity> activity = activityRepository.findById(id);
        String teacherName;
        if(floor == 2) {
            teacherName = activity.map(a -> a.getSecondFloorTeacher().getName())
                    .orElseThrow(() -> new ActivityNotFoundException());
        } else if(floor == 3) {
            teacherName = activity.map(a -> a.getThirdFloorTeacher().getName())
                    .orElseThrow(() -> new ActivityNotFoundException());
        } else if(floor == 4) {
            teacherName = activity.map(a -> a.getForthFloorTeacher().getName())
                    .orElseThrow(() -> new ActivityNotFoundException());
        } else {
            throw new ActivityNotFoundException();
        }

        return teacherName;
    }

    public Club getClubHeadAndName(String activity, int floor, int priority) {
        Club club;

        if(activity.equals("club")) {
            club = clubRepository.findByFloorAndPriority(floor, priority);
        } else if(activity.equals("self-study")) {
            club = null;
        } else {
            throw new ActivityNotFoundException();
        }

        return club;
    }

    public List<AttendanceListForm> getAttendanceList(LocalDate date, int floor, int priority) {
        List<AttendanceListForm> form = new ArrayList<>();
        final Comparator<Attendance> comparator =
                (c1, c2) -> c1.getStudent().getNum().compareTo(c2.getStudent().getNum());

        List<Attendance> attendanceList = attendanceRepository.findByDateAndFloorAndPriority(date, floor, priority);
        attendanceList.stream().sorted(comparator);

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

            if(a.getPeriod().equals("7"))
                attendanceListForm.getState().setSeven(a.getState());
            else if(a.getPeriod().equals("8"))
                attendanceListForm.getState().setEight(a.getState());
            else if(a.getPeriod().equals("9"))
                attendanceListForm.getState().setNine(a.getState());
            else if(a.getPeriod().equals("10"))
                attendanceListForm.getState().setTen(a.getState());
        }
        if(attendanceListForm != null)
            form.add(attendanceListForm);

        return form;
    }

    public void updateAttendance(LocalDate date, int floor, int priority, String number, String period, String state) {
        Attendance attendance = attendanceRepository.findByDateAndFloorAndPriorityAndNumberAndPeriod(
                date,
                floor,
                priority,
                number,
                period
        );
        attendance.setState(state);
    }
}
