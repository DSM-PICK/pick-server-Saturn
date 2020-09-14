package com.dsm.pick.domains.service;

import com.dsm.pick.domains.domain.Activity;
import com.dsm.pick.domains.repository.ActivityRepository;
import com.dsm.pick.domains.repository.ClassRepository;
import com.dsm.pick.utils.exception.ActivityNotFoundException;
import com.dsm.pick.utils.exception.NonExistentActivityException;
import com.dsm.pick.utils.form.ClubInformationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AttendanceService {

    private ClassRepository classRepository;
    private ActivityRepository activityRepository;

    @Autowired
    public AttendanceService(ClassRepository classRepository, ActivityRepository activityRepository) {
        this.classRepository = classRepository;
        this.activityRepository = activityRepository;
    }

    public List<ClubInformationForm> getNavigationInformation(String activity, int floor) {

        List<ClubInformationForm> form = new ArrayList<>();

        if(activity.equals("club")) {
            classRepository.findByFloor(floor);
        } else if(activity.equals("self-study")) {
            
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
