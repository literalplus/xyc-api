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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Caches a resource bundle for a specific base name from a specific class loader.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2017-08-12 / 4.5.0
 */
class CachedBundle {
    private Map<Locale, ResourceBundle> bundleMap = new HashMap<>();
    private final String baseName;
    private final ClassLoader loader;

    public CachedBundle(String baseName, ClassLoader loader) {
        this.baseName = baseName;
        this.loader = loader;
    }

    private ResourceBundle loadBundle(Locale locale) {
        return ResourceBundle.getBundle(baseName, locale, loader, Utf8Control.NO_CACHE);
    }

    public ResourceBundle getBundleFor(Locale locale) {
        Preconditions.checkNotNull(locale, "locale");
        return bundleMap.computeIfAbsent(locale, this::loadBundle);
    }

    /**
     * Clears any cached bundles, forcing them to be reloaded upon their next usage.
     */
    public void clearCache() {
        bundleMap.clear();
    }
}
