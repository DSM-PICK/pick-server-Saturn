package com.dsm.pick.domains.repository;

import com.dsm.pick.domains.domain.Club;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.database-platform=org.hibernate.dialect.MySQL5InnoDBDialect",
        "spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver",
        "spring.datasource.url=jdbc:mysql://localhost:3306/testdb_saturn?useUnicode=true&characterEncoding=utf8&allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
        "spring.datasource.username=root",
        "spring.datasource.password=1111"
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ClubCustomRepositoryImplTest {

    @Autowired
    ClubRepository clubRepository;

    @Test
    void findByFloor() {
        int floor = 3;
        List<Club> clubList = clubRepository.findByFloor(floor);
//        clubList.stream()
//                .forEach(c -> {
//                    System.out.println("name : " + c.getName());
//                    System.out.println("teacher : " + c.getTeacher());
//                    System.out.println("location : " + c.getLocation().getLocation());
//                    System.out.println("floor : " + c.getLocation().getFloor());
//                    System.out.println("priority : " + c.getLocation().getPriority());
//                });
    }
}