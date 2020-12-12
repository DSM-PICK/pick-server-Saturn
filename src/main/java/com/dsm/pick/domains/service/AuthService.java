package com.dsm.pick.domains.service;

import com.dsm.pick.domains.domain.Teacher;
import com.dsm.pick.domains.repository.TeacherRepository;
import com.dsm.pick.utils.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Optional;

@Service
public class AuthService {
    private static final String ALGORITHM = "SHA-512";
    private static final String ENCODING = "UTF-8";

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private TeacherRepository teacherRepository;

    @Autowired
    public AuthService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public String encodingPassword(String original) {
        String resultHex = null;
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            digest.reset();
            digest.update(original.getBytes(ENCODING));
            resultHex = String.format("%0128x", new BigInteger(1, digest.digest()));
        } catch(Exception e) {
            throw new NonExistEncodingOrCryptographicAlgorithmException("현재 인코딩 방식 또는 암호화 알고리즘이 잘못되었습니다.");
        }
        return resultHex;
    }

    public String getTeacherName(String teacherId) {
        return teacherRepository.findById(teacherId)
                .orElseThrow(TeacherNameNotFoundException::new)
                .getName();
    }

    public boolean checkId(String id) {
        if(id == null)
            throw new IdOrPasswordMismatchException();
        try {
            Teacher teacher = teacherRepository.findById(id)
                    .orElseThrow(TeacherNotFoundException::new);
            return true;
        } catch(Exception e) {
            log.error("error " + e.getMessage() + "[500]");
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkIdAndPassword(Teacher teacher) {
        String userId = teacher.getId();

        Teacher findTeacher = teacherRepository.findById(userId)
                .orElseThrow(IdOrPasswordMismatchException::new);

        String userPw = teacher.getPw();
        String findUserPw = findTeacher.getPw();

        if (!(userPw.equals(findUserPw)))
            throw new IdOrPasswordMismatchException();

        return true;
    }

    public void samePassword(String password, String confirmPassword) {
        if(!password.equals(confirmPassword))
            throw new IdOrPasswordMismatchException();
    }

    public void updatePassword(String id, String newPassword) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(IdOrPasswordMismatchException::new);
        teacher.setPw(encodingPassword(newPassword));
    }

    public void join(Teacher teacher) {
        String teacherId = teacher.getId();
        String password = teacher.getPw();
        String name = teacher.getName();
        String office = teacher.getOffice();

        if(alreadyExistID(teacherId))
            throw new AlreadyExistIdException();

        patternCheck(teacherId, 4, 16, "^[a-zA-Z|-]*$");
        patternCheck(password, 4, 16, "^[a-zA-Z0-9|*|!|@|^]*$");
        patternCheck(name, 1, 12, "^[a-zA-Zㄱ-ㅎ가-힣\\s]*$");
        patternCheck(office, 1, 12, "^[a-zA-Z0-9ㄱ-ㅎ가-힣\\s]*$");

        teacher.setPw(encodingPassword(password));
        teacherRepository.save(teacher);
    }

    public boolean alreadyExistID(String teacherId) {
        try {
            teacherRepository.findById(teacherId).get();
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    private void patternCheck(String target, int minimumLength, int maximumLength, String pattern) {
        boolean isNormal = target.length() >= minimumLength && target.length() <= maximumLength && target.matches(pattern);
        if(!isNormal) {
            System.out.println("target : " + target);
            System.out.println("length : " + target.length());
            throw new RuleViolationInformationException();
        }
    }
}