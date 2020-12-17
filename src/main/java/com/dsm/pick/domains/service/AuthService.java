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

@Service
public class AuthService {
    private static final String ALGORITHM = "SHA-512";
    private static final String ENCODING = "UTF-8";

    private static final String ID_PATTERN = "^[a-zA-Z0-9|-]*$";
    private static final String PASSWORD_PATTERN = "^[a-zA-Z0-9|*|!|@|^]*$";
    private static final String NAME_PATTERN = "^[a-zA-Zㄱ-ㅎ가-힣\\s]*$";
    private static final String OFFICE_PATTERN = "^[a-zA-Z0-9ㄱ-ㅎ가-힣\\s]*$";

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final TeacherRepository teacherRepository;

    @Autowired
    public AuthService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public String encodingPassword(String original) {
        String resultHex;
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            digest.reset();
            digest.update(original.getBytes(ENCODING));
            resultHex = String.format("%0128x", new BigInteger(1, digest.digest()));
        } catch(Exception e) {
            throw new NonExistEncodingOrCryptographicAlgorithmException();
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
            teacherRepository.findById(id)
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
        teacherRepository.save(teacher);
    }

    public void validatePassword(String password) {
        patternCheck(password, 4, 16, PASSWORD_PATTERN);
    }

    public void join(Teacher teacher) {
        String teacherId = teacher.getId();
        String password = teacher.getPw();
        String name = teacher.getName();
        String office = teacher.getOffice();

        if(alreadyExistID(teacherId))
            throw new AlreadyExistIdException();

        patternCheck(teacherId, 4, 16, ID_PATTERN);
        patternCheck(password, 4, 16, PASSWORD_PATTERN);
        patternCheck(name, 1, 12, NAME_PATTERN);
        patternCheck(office, 1, 12, OFFICE_PATTERN);

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