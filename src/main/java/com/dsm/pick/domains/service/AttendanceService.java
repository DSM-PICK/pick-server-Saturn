package com.dsm.pick.domains.service;

import com.dsm.pick.domains.domain.Activity;
import com.dsm.pick.domains.domain.Club;
import com.dsm.pick.domains.domain.SchoolClass;
import com.dsm.pick.domains.repository.ActivityRepository;
import com.dsm.pick.domains.repository.ClassRepository;
import com.dsm.pick.domains.repository.ClubRepository;
import com.dsm.pick.utils.exception.ActivityNotFoundException;
import com.dsm.pick.utils.exception.NonExistentActivityException;
import com.dsm.pick.utils.form.ClubAndClassInformationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Transactional
public class AttendanceService {

    private ClassRepository classRepository;
    private ClubRepository clubRepository;
    private ActivityRepository activityRepository;

    @Autowired
    public AttendanceService(ClassRepository classRepository, ClubRepository clubRepository, ActivityRepository activityRepository) {
        this.classRepository = classRepository;
        this.clubRepository = clubRepository;
        this.activityRepository = activityRepository;
    }

    public List<ClubAndClassInformationForm> getNavigationInformation(String activity, int floor) {

        List<ClubAndClassInformationForm> form = new ArrayList<>();

        if(activity.equals("club")) {
            List<Club> clubList = clubRepository.findByFloor(floor);
            AtomicBoolean isFirst = new AtomicBoolean(true);
            final Comparator<Club> comparator = (c1, c2) -> Integer.compare(c1.getLocation().getPriority(), c2.getLocation().getPriority());

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
}
