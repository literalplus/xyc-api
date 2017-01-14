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
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package li.l1t.common.chat;

import net.md_5.bungee.api.ChatColor;

/**
 * Provides static utility methods for dealing with String[] arguments to Minecraft commands.
 *
 * @author <a href="http://xxyy.github.io/">xxyy98</a>
 */
public final class Arguments {
    public static final String VALID_FORMATTING_CODES = "0123456789abcdefklmnor";

    private Arguments() {
    }

    /**
     * Concatenates an array of strings, using a space as separator.
     *
     * @param args the array to concatenate
     * @return the concatenated string
     */
    public static String join(String[] args) {
        return joinRange(args, 0, 0);
    }

    /**
     * Concatenates an array of strings, using a space as separator. The method starts to process items at given
     * start index, and quits processing {@code ignoreFromEnd} items before reaching the last element.
     *
     * @param args          the array to concatenate
     * @param startIndex    first array index to process
     * @param ignoreFromEnd how many items to ignore at the end; 0 means to process all items
     * @return the concatenated string
     */
    public static String joinRange(String[] args, int startIndex, int ignoreFromEnd) {
        final StringBuilder builder = new StringBuilder();
        for (int i = startIndex; i < (args.length - ignoreFromEnd); i++) {
            builder.append(((i == startIndex) ? "" : " ")).append(args[i]);
        }
        return builder.toString();
    }

    /**
     * Concatenates an array of strings, using a space as separator. The method starts to process items at given start
     * index, and quits processing {@code ignoreFromEnd} items before reaching the last element. Then {@link
     * ChatColor#translateAlternateColorCodes(char, String) translates alternate color codes in the result}.
     *
     * @param args          the array to concatenate
     * @param startIndex    first array index to process
     * @param ignoreFromEnd how many items to ignore at the end; 0 means to process all items
     * @return the concatenated string
     */
    public static String joinRangeColored(String[] args, int startIndex, int ignoreFromEnd) {
        return ChatColor.translateAlternateColorCodes('&', joinRange(args, startIndex, ignoreFromEnd));
    }

    /**
     * Concatenates an array of strings, using a space as separator. Then {@link
     * ChatColor#translateAlternateColorCodes(char, String) translates alternate color codes in the result}.
     *
     * @param args the array to concatenate
     * @return the concatenated string
     */
    public static String joinColored(String[] args) {
        return ChatColor.translateAlternateColorCodes('&', join(args));
    }
}
