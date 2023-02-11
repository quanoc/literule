package com.yart.literule.support.text.util;

import java.io.BufferedInputStream;
import java.util.Objects;

/**
 * Helper class for file resources.
 *
 * @author zhangquanquan@kanzhun.com
 */
class ResourceHelper {
    /**
     * getResourceInputStream.
     * @param resourceName resourceName
     * @return resource (mainly file in file system or file in compressed
     * package) as BufferedInputStream
     */
    static BufferedInputStream getResourceInputStream(String resourceName) {
        return new BufferedInputStream(Objects.requireNonNull(ResourceHelper.class.getResourceAsStream(resourceName)));
    }
}
