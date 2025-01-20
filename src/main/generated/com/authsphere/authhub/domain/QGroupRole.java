package com.authsphere.authhub.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGroupRole is a Querydsl query type for GroupRole
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGroupRole extends EntityPathBase<GroupRole> {

    private static final long serialVersionUID = 18836152L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGroupRole groupRole = new QGroupRole("groupRole");

    public final com.authsphere.authhub.common.QBaseTimeEntity _super = new com.authsphere.authhub.common.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final QGroup group;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final QRole role;

    public QGroupRole(String variable) {
        this(GroupRole.class, forVariable(variable), INITS);
    }

    public QGroupRole(Path<? extends GroupRole> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGroupRole(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGroupRole(PathMetadata metadata, PathInits inits) {
        this(GroupRole.class, metadata, inits);
    }

    public QGroupRole(Class<? extends GroupRole> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.group = inits.isInitialized("group") ? new QGroup(forProperty("group")) : null;
        this.role = inits.isInitialized("role") ? new QRole(forProperty("role")) : null;
    }

}

