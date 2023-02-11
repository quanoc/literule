package com.yart.literule.support.text.util;

import com.yart.literule.core.internal.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * FileUtil.
 *
 * @author zhangquanquan 2020.08.09
 */
public class FileUtil {
    static Logger log = LoggerFactory.getLogger(FileUtil.class);

    public static List<String> readFromFile(String path) {
        return readFromFile(
                ResourceHelper.getResourceInputStream(path)
        ).stream().filter(StringUtil::isNotBlank).collect(Collectors.toList());
    }

    public static List<String> readFromFile(BufferedInputStream inputStream) {
        List<String> list = new ArrayList<>();
        readLineFromFile(
                inputStream,
                Charset.defaultCharset(),
                list::add
        );
        return list;
    }

    public static void readLineFromFileWithException(String path, Consumer<String> consumer) {
        readLineFromFile(
                ResourceHelper.getResourceInputStream(path),
                Charset.defaultCharset(), s -> {
                    if (StringUtil.isNotEmpty(s)) {
                        try {
                            // 忽略备注
                            if (!s.startsWith("###")) {
                                consumer.accept(s);
                            }
                        } catch (Exception e) {
                            log.error("accept exception. s:{}", s, e);
                            e.printStackTrace();
                        }
                    }
                }
        );
    }

    public static void readLineFromFile(BufferedInputStream inputStream, Charset charset, Consumer<String> consumer) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream, charset));
            while (reader.ready()) {
                consumer.accept(reader.readLine());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            //关闭 io
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                log.error("close io fail. e", ex);
            }
        }
    }
}
