package com.dsm.pick.domains.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QActivity is a Querydsl query type for Activity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QActivity extends EntityPathBase<Activity> {

    private static final long serialVersionUID = 1894077322L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QActivity activity = new QActivity("activity");

    public final DatePath<java.time.LocalDate> date = createDate("date", java.time.LocalDate.class);

    public final QTeacher forthFloorTeacher;

    public final StringPath schedule = createString("schedule");

    public final QTeacher secondFloorTeacher;

    public final QTeacher thirdFloorTeacher;

    public QActivity(String variable) {
        this(Activity.class, forVariable(variable), INITS);
    }

    public QActivity(Path<? extends Activity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QActivity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QActivity(PathMetadata metadata, PathInits inits) {
        this(Activity.class, metadata, inits);
    }

    public QActivity(Class<? extends Activity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.forthFloorTeacher = inits.isInitialized("forthFloorTeacher") ? new QTeacher(forProperty("forthFloorTeacher")) : null;
        this.secondFloorTeacher = inits.isInitialized("secondFloorTeacher") ? new QTeacher(forProperty("secondFloorTeacher")) : null;
        this.thirdFloorTeacher = inits.isInitialized("thirdFloorTeacher") ? new QTeacher(forProperty("thirdFloorTeacher")) : null;
    }

}

