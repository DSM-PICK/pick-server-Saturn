package com.dsm.pick.domains.repository;

import com.dsm.pick.SpringConfiguration;
import com.dsm.pick.domains.domain.SchoolClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.swing.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
//@DataJpaTest
@SpringBootTest
//@TestPropertySource(properties = {
//        "spring.jpa.database-platform=org.hibernate.dialect.MySQL5InnoDBDialect",
//        "spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver",
//        "spring.datasource.url=jdbc:mysql://localhost:3306/testdb_saturn?useUnicode=true&characterEncoding=utf8&allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
//        "spring.datasource.username=root",
//        "spring.datasource.password=1111"
//})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ClassCustomRepositoryImplTest {

    @Autowired
    ClassRepository classRepository;

    @Test
    void findByFloor() {
        List<SchoolClass> floorList = classRepository.findByFloor(3);
//        floorList.stream()
//                .forEach(e -> {
//                    System.out.println("name : " + e.getName());
//                    System.out.println("floor : " + e.getFloor());
//                    System.out.println("priority : " + e.getPriority());
//                });
    }
}