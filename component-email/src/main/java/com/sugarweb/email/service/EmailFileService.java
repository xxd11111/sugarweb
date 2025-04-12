package com.sugarweb.email.service;

import java.io.InputStream;

/**
 * EmailFileService
 *
 * @author xxd
 * @version 1.0
 */
public interface EmailFileService {

    InputStream getContent(String fileId);

}
