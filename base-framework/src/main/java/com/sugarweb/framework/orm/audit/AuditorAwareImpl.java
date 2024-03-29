package com.sugarweb.framework.orm.audit;

import com.sugarweb.framework.security.SecurityHelper;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * SgcAuditorAware
 *
 * @author xxd
 * @version 1.0
 */
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(SecurityHelper.getUserInfo().getId());
    }

}
