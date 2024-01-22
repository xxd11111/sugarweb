package com.sugarcoat.support.server.domain;

import com.sugarcoat.support.server.auto.ApiRegister;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * ServerRunner
 *
 * @author xxd
 * @since 2023/10/12 22:12
 */
public record ServerRunner(ApiRegister apiRegister) implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        apiRegister.register();
    }

}
