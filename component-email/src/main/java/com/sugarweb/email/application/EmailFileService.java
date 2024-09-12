package com.sugarweb.email.application;

import java.io.InputStream;

/**
 * EmailFileService
 *
 * @author 许向东
 * @version 1.0
 */
public interface EmailFileService {

    InputStream getContent(String fileId);

}
