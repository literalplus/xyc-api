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

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2017-08-13
 */
class MessagePathTest {
    @Test
    void of__full() {
        whenParsedThenFieldsAreOk("li.l1t.test", "li.l1t.test", "somebundle", "my.key");
    }

    private void whenParsedThenFieldsAreOk(String shorthandKey, String packageKey, String bundleName, String key) {
        String fullPath = shorthandKey + "!" + bundleName + "!" + key;
        MessagePath path = MessagePath.of(fullPath);
        assertThat(path.packageKey(), is(packageKey));
        assertThat(path.bundle(), is(packageKey + "." + bundleName));
        assertThat(path.key(), is(key));
        assertThat(path.bundle(), startsWith(path.packageKey()));
    }

    @Test
    void of__noPackageName() {
        whenParsedThenFieldsAreOk("", "", "somebundle", "another.key");
    }

    @Test
    void of__xycPackage() {
        whenParsedThenFieldsAreOk("x", "li.l1t.common", "somebundle", "my.key");
    }

    @Test
    void of__customShorthand() {
        String customShorthand = "cust";
        String customPackageName = "li.l1t.some.custom.package";
        MessagePath.registerPackageShorthand(customShorthand, customPackageName);
        whenParsedThenFieldsAreOk(customShorthand, customPackageName, "custom-bundle", "my.custom.msg");
    }
}