package com.dsm.pick.domains.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSchoolClass is a Querydsl query type for SchoolClass
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSchoolClass extends EntityPathBase<SchoolClass> {

    private static final long serialVersionUID = 1396109321L;

    public static final QSchoolClass schoolClass = new QSchoolClass("schoolClass");

    public final NumberPath<Integer> floor = createNumber("floor", Integer.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> priority = createNumber("priority", Integer.class);

    public final ListPath<Student, QStudent> students = this.<Student, QStudent>createList("students", Student.class, QStudent.class, PathInits.DIRECT2);

    public QSchoolClass(String variable) {
        super(SchoolClass.class, forVariable(variable));
    }

    public QSchoolClass(Path<? extends SchoolClass> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSchoolClass(PathMetadata metadata) {
        super(SchoolClass.class, metadata);
    }

}

