package com.xxd.orm.audit;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * SgcAuditorAware
 *
 * @author xxd
 * @since 2023/10/21 16:01
 */
public class SgcAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("1");
        // return Optional.ofNullable(SecurityHelper.currentUserId());
    }

}
