package com.authsphere.authhub.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberGroup is a Querydsl query type for MemberGroup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberGroup extends EntityPathBase<MemberGroup> {

    private static final long serialVersionUID = 1557636712L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberGroup memberGroup = new QMemberGroup("memberGroup");

    public final com.authsphere.authhub.common.QBaseTimeEntity _super = new com.authsphere.authhub.common.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final QGroup group;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final QMember member;

    public QMemberGroup(String variable) {
        this(MemberGroup.class, forVariable(variable), INITS);
    }

    public QMemberGroup(Path<? extends MemberGroup> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberGroup(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberGroup(PathMetadata metadata, PathInits inits) {
        this(MemberGroup.class, metadata, inits);
    }

    public QMemberGroup(Class<? extends MemberGroup> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.group = inits.isInitialized("group") ? new QGroup(forProperty("group")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

