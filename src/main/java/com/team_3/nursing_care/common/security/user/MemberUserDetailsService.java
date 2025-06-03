package com.team_3.nursing_care.common.security.user;

import com.team_3.nursing_care.common.security.user.custom.CustomUserDetails;
import com.team_3.nursing_care.common.security.user.custom.CustomUserDetailsService;
import com.team_3.nursing_care.domain.member.entity.Member;
import com.team_3.nursing_care.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberUserDetailsService extends CustomUserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        // TODO: COMPANY 도 JOIN 해서 가져와야 함.
        Member member = memberRepository.findById(Long.valueOf(id)).orElseThrow(() -> new UsernameNotFoundException(id));
        return MemberUserDetails.of(member);
    }

    @Override
    public boolean checkCurrentTokenEquals(String token, CustomUserDetails customUserDetails) {
        // Compare Only RefreshToken
        MemberUserDetails memberUserDetails = (MemberUserDetails) customUserDetails;
        return token.equals(((MemberUserDetails) customUserDetails).getRefreshToken());
    }
}
