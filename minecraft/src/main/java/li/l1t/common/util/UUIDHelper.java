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

package li.l1t.common.util;

import com.google.common.base.Charsets;

import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Static utility class providing some helpful methods for dealing with UUIDs in the context of
 * Minecraft.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2014-08-04
 */
public class UUIDHelper {
    /**
     * The nil UUID - a special UUID which can be used to represent special values, such as the Minecraft server
     * console. {@code 00000000-0000-0000-0000-000000000000}
     *
     * @since 4.4.0
     */
    public static final UUID NIL_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");
    /**
     * A Pattern that matches valid Java UUIDs.
     */
    public static final Pattern UUID_PATTERN = Pattern.compile("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}");

    /**
     * Performs a match using {@link #UUID_PATTERN} to check whether {@code input} is a valid UUID
     * as accepted by the Java {@link java.util.UUID} impl.
     *
     * @param input Input string to check
     * @return Whether the input string is a valid Java UUID.
     */
    public static boolean isValidUUID(String input) {
        return UUID_PATTERN.matcher(input).matches();
    }

    /**
     * Creates an "offline" UUID as Minecraft would use for "cracked" players.
     *
     * @param offlineName The offline player's name, case-sensitive.
     * @return the offline UUID for given name.
     */
    public static UUID getOfflineUUID(String offlineName) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + offlineName).getBytes(Charsets.UTF_8));
    }

    /**
     * Gets an UUID from a String. The input may or may not contain the dashes used by Java, since
     * Mojang's API returns UUIDs without dashes at some points.
     *
     * @param input the input to convert
     * @return an UUID corresponding to the input or NULL if the input is invalid
     */
    public static UUID getFromString(String input) {
        if (input == null) {
            return null;
        } else if (isValidUUID(input)) {
            return UUID.fromString(input);
        } else {
            if (input.length() == 32) {
                String s1 = input.substring(0, 8) + "-" + input.substring(8, 12) + "-" + input.substring(12, 16) + "-" + input.substring(16, 20) + "-" + input.substring(20, 32);

                if (isValidUUID(s1)) {
                    return UUID.fromString(s1);
                }
            }

            return null;
        }
    }
}
