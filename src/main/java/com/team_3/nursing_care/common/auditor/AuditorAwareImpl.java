package com.team_3.nursing_care.common.auditor;

import com.team_3.nursing_care.common.security.user.custom.CustomUserDetails;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null == authentication || !authentication.isAuthenticated()) return Optional.empty();

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return Optional.ofNullable(userDetails.getMemberId());
    }
}
