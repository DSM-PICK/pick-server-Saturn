package com.dsm.pick.domains.service;

import com.dsm.pick.domains.repository.TeacherRepository;
import com.dsm.pick.utils.form.AttendanceNavigationResponseForm;
import com.dsm.pick.utils.form.ClubInformationForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AttendanceService {

    private TeacherRepository userRepository;

    public List<ClubInformationForm> getNavigationInformation(String activity, int floor) {

        List<ClubInformationForm> form = new ArrayList<>();



        return form;
    }
}
