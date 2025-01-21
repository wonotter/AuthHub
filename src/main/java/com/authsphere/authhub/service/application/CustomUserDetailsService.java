package com.authsphere.authhub.service.application;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.authsphere.authhub.domain.Member;
import com.authsphere.authhub.domain.oauth2.DefaultMemberUserInfo;
import com.authsphere.authhub.domain.oauth2.OAuth2UserInfo;
import com.authsphere.authhub.domain.oauth2.OAuth2UserPrincipal;
import com.authsphere.authhub.exception.commonexception.NotFoundException;
import com.authsphere.authhub.exception.domain.ExceptionType;
import com.authsphere.authhub.service.domain.MemberService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberService.findByUsernameWithRolesAndGroups(username)
            .orElseThrow(() -> new NotFoundException(ExceptionType.MEMBER_NOT_FOUND));
        
        OAuth2UserInfo userInfo = new DefaultMemberUserInfo(member);

        return new OAuth2UserPrincipal(userInfo, member.getAuthorities());
    }
}
