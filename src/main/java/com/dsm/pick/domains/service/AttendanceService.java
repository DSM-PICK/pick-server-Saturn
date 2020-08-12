package com.dsm.pick.domains.service;

import com.dsm.pick.domains.repository.TeacherRepository;
import com.dsm.pick.utils.form.AttendanceNavigationResponseForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AttendanceService {

    private TeacherRepository userRepository;

    public AttendanceNavigationResponseForm getNavigationInfomation(String activity, int floor) {



        return new AttendanceNavigationResponseForm();
    }
}
