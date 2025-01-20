package com.authsphere.authhub.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPermission is a Querydsl query type for Permission
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPermission extends EntityPathBase<Permission> {

    private static final long serialVersionUID = -1204038900L;

    public static final QPermission permission = new QPermission("permission");

    public final com.authsphere.authhub.common.QBaseTimeEntity _super = new com.authsphere.authhub.common.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath permissionName = createString("permissionName");

    public final SetPath<RolePermission, QRolePermission> rolePermissions = this.<RolePermission, QRolePermission>createSet("rolePermissions", RolePermission.class, QRolePermission.class, PathInits.DIRECT2);

    public QPermission(String variable) {
        super(Permission.class, forVariable(variable));
    }

    public QPermission(Path<? extends Permission> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPermission(PathMetadata metadata) {
        super(Permission.class, metadata);
    }

}

