package com.authsphere.authhub.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -1111310761L;

    public static final QMember member = new QMember("member1");

    public final com.authsphere.authhub.common.QBaseTimeEntity _super = new com.authsphere.authhub.common.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final SetPath<MemberGroup, QMemberGroup> groups = this.<MemberGroup, QMemberGroup>createSet("groups", MemberGroup.class, QMemberGroup.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath password = createString("password");

    public final SetPath<MemberRole, QMemberRole> roles = this.<MemberRole, QMemberRole>createSet("roles", MemberRole.class, QMemberRole.class, PathInits.DIRECT2);

    public final StringPath username = createString("username");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

