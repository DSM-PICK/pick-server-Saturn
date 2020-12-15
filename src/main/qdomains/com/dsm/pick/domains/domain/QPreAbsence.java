package com.dsm.pick.domains.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPreAbsence is a Querydsl query type for PreAbsence
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPreAbsence extends EntityPathBase<PreAbsence> {

    private static final long serialVersionUID = 1365829589L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPreAbsence preAbsence = new QPreAbsence("preAbsence");

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final NumberPath<Integer> endPeriod = createNumber("endPeriod", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public final NumberPath<Integer> startPeriod = createNumber("startPeriod", Integer.class);

    public final StringPath state = createString("state");

    public final QStudent student;

    public final QTeacher teacher;

    public QPreAbsence(String variable) {
        this(PreAbsence.class, forVariable(variable), INITS);
    }

    public QPreAbsence(Path<? extends PreAbsence> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPreAbsence(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPreAbsence(PathMetadata metadata, PathInits inits) {
        this(PreAbsence.class, metadata, inits);
    }

    public QPreAbsence(Class<? extends PreAbsence> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.student = inits.isInitialized("student") ? new QStudent(forProperty("student"), inits.get("student")) : null;
        this.teacher = inits.isInitialized("teacher") ? new QTeacher(forProperty("teacher")) : null;
    }

}

