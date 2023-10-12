package com.sugarcoat.support.server.domain;

import com.sugarcoat.protocol.common.SgcRunner;
import org.springframework.boot.ApplicationArguments;

/**
 * ServerRunner
 *
 * @author xxd
 * @since 2023/10/12 22:12
 */
public record ServerRunner(ApiRegister apiRegister) implements SgcRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        apiRegister.register();
    }

}
