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

import java.util.*;

/**
 * Caches resource bundles, forwarding to a loader for cache misses.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2017-08-12 / 4.5.0
 */
class BundleCache {
    private final Map<String, Optional<CachedBundle>> customBundles = new HashMap<>();
    private final Map<String, Optional<CachedBundle>> defaultBundles = new HashMap<>();
    private ClassLoader defaultLoader;
    private ClassLoader customLoader;

    public void setDefaultLoader(ClassLoader defaultLoader) {
        Preconditions.checkNotNull(defaultLoader, "defaultLoader");
        this.defaultLoader = defaultLoader;
    }

    public void setCustomLoader(ClassLoader customLoader) {
        Preconditions.checkNotNull(customLoader, "customLoader");
        this.customLoader = customLoader;
    }

    public Optional<ResourceBundle> findBundleContaining(Locale locale, MessagePath path) {
        return findCustomBundleContaining(locale, path)
                .map(Optional::of)
                .orElseGet(() -> findDefaultsBundleContaining(locale, path));
    }

    private Optional<ResourceBundle> findCustomBundleContaining(Locale locale, MessagePath path) {
        return getCustom(path.bundle())
                .map(bundle -> bundle.getBundleFor(locale))
                .filter(bundle -> bundle.containsKey(path.key()));
    }

    private Optional<ResourceBundle> findDefaultsBundleContaining(Locale locale, MessagePath path) {
        return getFromDefaults(path.bundle())
                .map(bundle -> bundle.getBundleFor(locale))
                .filter(bundle -> bundle.containsKey(path.key()));
    }

    private Optional<CachedBundle> getCustom(String baseName) {
        if (customLoader == null) {
            return Optional.empty();
        }
        return customBundles.computeIfAbsent(baseName, this::customBundleFor);
    }

    private Optional<CachedBundle> customBundleFor(String baseName) {
        return bundleFor(baseName, customLoader, "custom file");
    }

    private Optional<CachedBundle> bundleFor(String baseName, ClassLoader classLoader, String bundleTypeDesc) {
        try {
            return Optional.of(new CachedBundle(baseName, classLoader));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private Optional<CachedBundle> getFromDefaults(String baseName) {
        return defaultBundles.computeIfAbsent(baseName, this::defaultsBundleFor);
    }

    private Optional<CachedBundle> defaultsBundleFor(String baseName) {
        return bundleFor(baseName, defaultLoader, "default");
    }

    public void clear() {
        customBundles.clear();
        defaultBundles.clear();
    }
}
