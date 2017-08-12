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

import com.google.common.base.Preconditions;

import java.text.MessageFormat;
import java.util.*;

/**
 * Provides static utilities for Internationalisation of XYC messages.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2017-08-12 / 4.5.0
 */
public class XycI18n {
    private static Map<String, BundleCache> bundleCaches = new HashMap<>();

    static {
        BundleCache xycBundles = new BundleCache();
        xycBundles.setDefaultLoader(XycI18n.class.getClassLoader());
        registerBundles("li.l1t.common", xycBundles);
    }

    /**
     * Registers a bundle cache for a package key. This needs to be specified in all messages in order to correctly
     * attribute them to the bundle cache. All bundles are resolved relative to this package. <p><b>Example:</b> If the
     * package key is {@code li.l1t.common}, the bundle {@code api} will be looked for at {@code
     * li/l1t/common/api.properties} (and language-specific versions) in the bundle cache's default loader.</p>
     * <p><b>Note:</b> To simplify usage, a shorthand can be registered for the package key with {@link
     * MessagePath#registerPackageShorthand(String, String)}.</p>
     *
     * @param packageKey  the package key to register at
     * @param bundleCache the cache to associate with the key
     */
    public static void registerBundles(String packageKey, BundleCache bundleCache) {
        bundleCaches.put(packageKey, bundleCache);
    }

    public static String getMessage(Locale locale, String key, Object... params) {
        return getMessage(locale, Message.of(key, params));
    }

    public static String getMessage(Locale locale, Message message) {
        Preconditions.checkNotNull(locale, "locale");
        if (isDebugLocale(locale) || message.getKey() == null) {
            return message.toString();
        }
        MessagePath path = MessagePath.of(message.getKey());
        Optional<ResourceBundle> bundle = Optional.ofNullable(bundleCaches.get(path.packageKey()))
                .flatMap(cache -> cache.findBundleContaining(locale, path));
        if (bundle.isPresent()) {
            return doTranslation(locale, bundle.get(), path.key(), message.getArguments());
        } else {
            if (message.hasFallback()) {
                return getMessage(locale, message.getFallback());
            } else {
                return message.toString();
            }
        }
    }

    private static boolean isDebugLocale(Locale locale) {
        return "debug".equals(locale.getVariant());
    }

    private static String doTranslation(Locale locale, ResourceBundle bundle, String key, Object... args) {
        String pattern = bundle.getString(key);
        return new MessageFormat(pattern, locale)
                .format(localizeMessageArguments(locale, args));
    }

    private static Object[] localizeMessageArguments(Locale locale, Object[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof Message) {
                args[i] = getMessage(locale, (Message) args[i]);
            }
        }
        return args;
    }
}
