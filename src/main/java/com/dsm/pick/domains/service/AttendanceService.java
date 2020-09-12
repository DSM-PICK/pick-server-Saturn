package com.dsm.pick.domains.service;

import com.dsm.pick.domains.repository.ClassRepository;
import com.dsm.pick.utils.exception.NonExistentActivityException;
import com.dsm.pick.utils.form.ClubInformationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AttendanceService {

    private ClassRepository classRepository;

    @Autowired
    public AttendanceService(ClassRepository classRepository) {
        this.classRepository = classRepository;
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
}
