package com.sugarcoat.api.oss;

import lombok.Data;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件对象
 *
 * @author xxd
 * @since 2023/8/2 22:57
 */

@Data
public class FileObject implements Closeable {

    private String contentType;

    private long contentLength;

    private InputStream content;

    @Override
    public void close() throws IOException {
        InputStream is = getContent();
        if (is != null)
            is.close();
    }

}
