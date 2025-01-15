package com.authhub.domain.interfaces.authorization;

import java.util.Set;

/**
 * 그룹(Group) 인터페이스
 * - 여러 사용자, 여러 역할을 묶을 수 있는 상위 개념
 */
public interface Group {
    Long getId();
    String getGroupName();
    Set<? extends MemberGroup> getMemberGroups();
    Set<? extends GroupRole> getGroupRoles();
}
