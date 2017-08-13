/*
 * MIT License
 *
 * Copyright (c) 2016-2017 Philipp Nowak (Literallie)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package li.l1t.common.i18n;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the unique path to a localised message with bundle and message key. <p>String representations look like
 * {@code package!core!some.key}, where {@code core} is the resource bundle name, and {@code some.key} is the message
 * key in that bundle. {@code package} can be either a full package name where the bundle is located, an empty string
 * for locale files located in the root of the class path, or a {@link #registerPackageShorthand(String, String)
 * registered shorthand}.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2017-08-12 / 4.5.0
 */
public class MessagePath {
    private final String packageKey;
    private final String bundle;
    private final String key;
    private static final Map<String, String> packageShorthands = new HashMap<>();

    static {
        packageShorthands.put("x", "li.l1t.common");
    }

    private MessagePath(String packageKey, String bundle, String key) {
        this.packageKey = packageKey;
        this.bundle = bundle;
        this.key = key;
    }

    /**
     * Registers a shorthand for a package name that will be replaced with provided name when encountered in the first
     * part of a message.
     *
     * @param shorthand   the shorthand to register
     * @param packageName the full package name to replace it with
     */
    public static void registerPackageShorthand(String shorthand, String packageName) {
        packageShorthands.put(shorthand, packageName);
    }

    public static MessagePath of(String path) {
        String[] parts = path.split("!");
        if (parts.length != 3 || parts[2].isEmpty() || parts[1].isEmpty()) {
            throw new IllegalArgumentException(String.format("Malformed message path: '%s'", path));
        }
        String packageName = packageShorthands.getOrDefault(parts[0], parts[0]);
        return new MessagePath(parts[0], packageName + "." + parts[0], parts[1]);
    }

    public String packageKey() {
        return packageKey;
    }

    /**
     * @return the fully qualified name of the bundle the message is located in, including the package key, followed by a
     * dot, and then the actual name of the bundle
     */
    public String bundle() {
        return bundle;
    }

    /**
     * @return the key of the message in the bundle, as defined in the properties file
     */
    public String key() {
        return key;
    }

    @Override
    public String toString() {
        return "message '" + key + "' in '" + bundle + "'";
    }
}
