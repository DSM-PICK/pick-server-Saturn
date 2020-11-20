package com.dsm.pick.domains.service;

import com.dsm.pick.domains.domain.Club;
import com.dsm.pick.domains.domain.Student;
import com.dsm.pick.domains.repository.ClubRepository;
import com.dsm.pick.utils.exception.NonExistFloorException;
import com.dsm.pick.utils.form.StatisticsClubForm;
import com.dsm.pick.utils.form.StatisticsResponseForm;
import com.dsm.pick.utils.form.StatisticsStudentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsService {

    private ClubRepository clubRepository;

    @Autowired
    public StatisticsService(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    public List<StatisticsClubForm> getTodayStatistics(int floor) {

        if(!(1 <= floor && floor <= 3)) {
            throw new NonExistFloorException("1, 2, 3, 4층이 아닙니다.");
        }

        List<StatisticsClubForm> statisticsClubForms = new ArrayList<>();

        List<Club> clubs = clubRepository.findByFloor(floor);

//        clubs.stream()
//                .forEach(c -> {
//                    StatisticsClubForm form = new StatisticsClubForm();
//                    form.setClubName(c.getName());
//                    List<Student> students = c.getStudents();
//                    students.stream()
//                            .map(s -> {
//                                new StatisticsStudentForm(s.getNum() + " " + s.getName(), s.get)
//                            })
//                });

        return new ArrayList<>();
    }

    public String convertToText(int floor) {
        return floor + "층";
    }
}
