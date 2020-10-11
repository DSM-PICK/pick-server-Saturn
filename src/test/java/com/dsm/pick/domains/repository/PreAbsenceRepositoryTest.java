package com.dsm.pick.domains.repository;

import com.dsm.pick.domains.domain.PreAbsence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PreAbsenceRepositoryTest {

    @Autowired
    PreAbsenceRepository preAbsenceRepository;

    @Test
    void findByCurrentDate() {
        List<PreAbsence> byCurrentDate = preAbsenceRepository.findByCurrentDate();
    }
}