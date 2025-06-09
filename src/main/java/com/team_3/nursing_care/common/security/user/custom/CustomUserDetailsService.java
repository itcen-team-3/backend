package com.team_3.nursing_care.common.security.user.custom;

import org.springframework.security.core.userdetails.UserDetailsService;

public abstract class CustomUserDetailsService implements UserDetailsService {

    public abstract boolean checkCurrentTokenEquals(String token, CustomUserDetails customUserDetails);

}
