package com.sugarweb.server.domain;

import com.sugarweb.server.auto.ApiRegister;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * ServerRunner
 *
 * @author xxd
 * @version 1.0
 */
public record ServerRunner(ApiRegister apiRegister) implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        apiRegister.register();
    }

}
