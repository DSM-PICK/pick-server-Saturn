package com.dsm.pick.domains.service;

import com.dsm.pick.domains.domain.*;
import com.dsm.pick.domains.repository.*;
import com.dsm.pick.utils.exception.ActivityNotFoundException;
import com.dsm.pick.utils.exception.NonExistFloorException;
import com.dsm.pick.utils.exception.NotClubAndSelfStudyException;
import com.dsm.pick.utils.form.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@Transactional
public class AttendanceService {

    private final ClassRepository classRepository;
    private final ClubRepository clubRepository;
    private final ActivityRepository activityRepository;
    private final AttendanceRepository attendanceRepository;

    private final ServerTimeService serverTimeService;

    @Autowired
    public AttendanceService(ClassRepository classRepository, ClubRepository clubRepository, ActivityRepository activityRepository, AttendanceRepository attendanceRepository, ServerTimeService serverTimeService) {
        this.classRepository = classRepository;
        this.clubRepository = clubRepository;
        this.activityRepository = activityRepository;
        this.attendanceRepository = attendanceRepository;
        this.serverTimeService = serverTimeService;
    }

    public StatisticsNavigationResponseForm getStatisticsNavigation(LocalDate date, String schedule, int floor) {
        if(!(schedule.equals("club")
                || schedule.equals("self-study")
                || schedule.equals("after-school")))
            throw new NotClubAndSelfStudyException();

        List<StatisticsClubAndClassInformationForm> statisticsClubAndClassInformationForms =
                getStatisticsNavigationInformation(schedule, floor);
        String monthAndDate =
                serverTimeService.getMonthAndDate(date);
        String dayOfWeek =
                serverTimeService.getDayOfWeek(date);

        return new StatisticsNavigationResponseForm(
                monthAndDate, dayOfWeek, schedule, statisticsClubAndClassInformationForms);
    }

    public StatisticsListResponseForm getStatistics(LocalDate date, String schedule, int floor, int priority) {
        if(!(schedule.equals("club")
                || schedule.equals("self-study")
                || schedule.equals("after-school")))
            throw new NotClubAndSelfStudyException();

        List<AttendanceListForm> attendanceList =
                getAttendanceList(schedule, date, floor, priority);

        if(schedule.equals("club")) {
            Club club = getClubHeadAndName(floor, priority);
            return new StatisticsListResponseForm(club.getName(), club.getHead(), club.getTeacher(), attendanceList);
        } else if(schedule.equals("self-study")) {
            SchoolClass schoolClass = getClassName(floor, priority);
            return new StatisticsListResponseForm(schoolClass.getName(), null, "홍정교", attendanceList);
        } else {
            throw new NotClubAndSelfStudyException();
        }
    }

    public List<ClubAndClassInformationForm> getNavigationInformation(String schedule, int floor) {

        List<ClubAndClassInformationForm> form = new ArrayList<>();

        if(schedule.equals("club")) {

            if(!(1 <= floor && floor <= 4)) {
                throw new NonExistFloorException();
            }

            List<Club> clubList = clubRepository.findByFloor(floor);
            AtomicBoolean isFirst = new AtomicBoolean(true);
            final Comparator<Club> comparator =
                    Comparator.comparingInt(c -> c.getLocation().getPriority());

            clubList.stream()
                    .sorted(comparator)
                    .filter(c -> c.getStudents().size() >= 1)
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

        } else if(schedule.equals("self-study") || schedule.equals("after-school")) {

            if(!(1 <= floor && floor <= 4)) {
                throw new NonExistFloorException();
            }

            List<SchoolClass> classList = classRepository.findByFloor(floor);
            AtomicBoolean isFirst = new AtomicBoolean(true);

            classList.stream()
                    .sorted(Comparator.comparing(SchoolClass::getPriority))
//                    .filter(c -> c.getStudents().size() >= 1)
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
            throw new NotClubAndSelfStudyException();
        }

        return form;
    }

    public List<StatisticsClubAndClassInformationForm> getStatisticsNavigationInformation(
            String schedule, int floor) {
        return this.getNavigationInformation(schedule, floor)
                .stream()
                .map(i -> new StatisticsClubAndClassInformationForm(i.getLocation(), i.getName(), i.getPriority()))
                .collect(Collectors.toList());
    }

    public String getTodayTeacherName(String date, int floor) {
        int todayYear = LocalDate.now().getYear();
        int todayMonth = Integer.parseInt(date.substring(0, 2));
        int todayDayOfMonth = Integer.parseInt(date.substring(2, 4));

        LocalDate id = LocalDate.of(todayYear, todayMonth, todayDayOfMonth);

        Optional<Activity> activity = activityRepository.findById(id);
        String teacherName;
        if(floor == 1 || activity.get().getSchedule().equals("after-school")) {
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
            throw new NonExistFloorException();
        }

        return teacherName;
    }

    public Club getClubHeadAndName(int floor, int priority) {
        return clubRepository.findByFloorAndPriority(floor, priority);
    }

    public SchoolClass getClassName(int floor, int priority) {
        return classRepository.findByFloorAndPriority(floor, priority);
    }

    public List<AttendanceListForm> getAttendanceList(String schedule, LocalDate date, int floor, int priority) {
        List<AttendanceListForm> form = new ArrayList<>();

        List<Attendance> attendanceList;
        if(schedule.equals("club")) {
            attendanceList = attendanceRepository.findByDateAndFloorAndPriorityWithClub(date, floor, priority);
        } else if(schedule.equals("self-study") || schedule.equals("after-school")) {
            attendanceList = attendanceRepository.findByDateAndFloorAndPriorityWithClass(date, floor, priority);
        } else {
            throw new NotClubAndSelfStudyException();
        }

        AttendanceListForm attendanceListForm = null;
        if(attendanceList == null) {
            return form;
        }
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
}
