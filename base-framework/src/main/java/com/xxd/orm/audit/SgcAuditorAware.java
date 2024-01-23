package com.xxd.orm.audit;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * SgcAuditorAware
 *
 * @author xxd
 * @version 1.0
 */
public class SgcAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("1");
        // return Optional.ofNullable(SecurityHelper.currentUserId());
    }

}
