package com.authsphere.authhub.service.domain;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.authsphere.authhub.domain.Member;
import com.authsphere.authhub.domain.oauth2.OAuth2UserInfo;
import com.authsphere.authhub.dto.LoginRequest;
import com.authsphere.authhub.exception.commonexception.NotFoundException;
import com.authsphere.authhub.exception.domain.ExceptionType;
import com.authsphere.authhub.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member getOrElseThrow(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new NotFoundException(ExceptionType.MEMBER_NOT_FOUND));
    }

    public Member findMemberByUsername(String username) {
        return memberRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundException(ExceptionType.MEMBER_NOT_FOUND));
    }

    public Optional<Member> findByUsernameWithRolesAndGroups(String username) {
        return memberRepository.findByUsernameWithRolesAndGroups(username);
    }

    public Member registerSocialMember(OAuth2UserInfo oAuth2UserInfo) {
        Member member = Member.fromUserInfo(oAuth2UserInfo);
        return memberRepository.save(member);
    }

    public Member registerMember(LoginRequest loginRequest) {
        Member member = findMemberByUsername(loginRequest.username());
        String encodedPassword = passwordEncoder.encode(loginRequest.password());
        member.setPassword(encodedPassword);
        return memberRepository.save(member);
    }

    public void deleteMember(Member member) {
        memberRepository.delete(member);
    }
}
