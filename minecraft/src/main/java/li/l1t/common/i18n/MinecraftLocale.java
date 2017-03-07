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

import com.google.common.collect.ImmutableMap;

import java.util.Locale;
import java.util.Map;

/**
 * Provides static utility methods for working with Minecraft language tags.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2017-03-07
 */
public class MinecraftLocale {
    private static final Map<String, Locale> LOCALE_REPLACEMENTS = ImmutableMap.<String, Locale>builder()
            .put("ksh_DE", Locale.forLanguageTag("de-DE-x-ksh"))
            .put("swg_DE", Locale.forLanguageTag("de-DE-x-swg"))
            .put("lol_US", Locale.forLanguageTag("en-US-x-lolcat"))
            .put("ca-val_ES", Locale.forLanguageTag("ca-VA"))
            .build();

    /**
     * Converts a Minecraft locale string to a Java Locale object. Note that not all Minecraft locales have
     * representations in Java, even though most language codes (as of 1.10) are compatible with the IETF language
     * tag format. To do the actual conversion, underscores are replaced with dashes.
     * <p>This method converts the following languages because of missing or incorrect Java representations:</p>
     * <table>
     * <thead><tr>
     * <th>Minecraft</th>
     * <th>Java / IETF</th>
     * <th>Notes</th>
     * </tr></thead>
     * <tbody>
     * <tr>
     * <td>ksh_DE</td>
     * <td>de-DE-x-ksh</td>
     * <td>ksh (K&ouml;lsch) is in ISO-639-3, but Java 8_121 doesn't support it</td>
     * </tr>
     * <tr>
     * <td>swg_DE</td>
     * <td>de-DE-x-swg</td>
     * <td>swg (Swabian German) is in ISO-639-3, but Java 8_121 doesn't support it</td>
     * </tr>
     * <tr>
     * <td>lol_US</td>
     * <td>en-US-x-lolcat</td>
     * <td>LOLCAT is not an actual language as far as ISO standards are concerned</td>
     * </tr>
     * <tr>
     * <td>ca-val-ES</td>
     * <td>ca-VA</td>
     * <td>Valencian does not have a distinct ISO-639-3 code from Catalan</td>
     * </tr>
     * </tbody>
     * </table>
     * <p>Also note that some languages, such as Catalan, are supposed to be included in Java 8 according to Oracle,
     * but were not included in tested JVMs. This especially applies to less common constructed languages.</p>
     *
     * @param minecraftLocale the Minecraft locale string to convert
     * @return the Java locale, at best effort
     */
    public static Locale toJava(String minecraftLocale) {
        if (LOCALE_REPLACEMENTS.containsKey(minecraftLocale)) {
            return LOCALE_REPLACEMENTS.get(minecraftLocale);
        } else {
            return Locale.forLanguageTag(minecraftLocale.replace("_", "-"));
        }
    }
}
