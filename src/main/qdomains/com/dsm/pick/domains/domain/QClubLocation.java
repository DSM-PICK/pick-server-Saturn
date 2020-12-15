package com.dsm.pick.domains.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClubLocation is a Querydsl query type for ClubLocation
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QClubLocation extends EntityPathBase<ClubLocation> {

    private static final long serialVersionUID = 1439317382L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClubLocation clubLocation = new QClubLocation("clubLocation");

    public final QClub club;

    public final NumberPath<Integer> floor = createNumber("floor", Integer.class);

    public final StringPath location = createString("location");

    public final NumberPath<Integer> priority = createNumber("priority", Integer.class);

    public QClubLocation(String variable) {
        this(ClubLocation.class, forVariable(variable), INITS);
    }

    public QClubLocation(Path<? extends ClubLocation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClubLocation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClubLocation(PathMetadata metadata, PathInits inits) {
        this(ClubLocation.class, metadata, inits);
    }

    public QClubLocation(Class<? extends ClubLocation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.club = inits.isInitialized("club") ? new QClub(forProperty("club"), inits.get("club")) : null;
    }

}

