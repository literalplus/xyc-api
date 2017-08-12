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
 * This file is part of XYC.
 * It may only be used in my projects
 * and only be distributed with my explicit permission.
 */
package li.l1t.common.bungee.util;

import com.google.common.base.Preconditions;
import li.l1t.common.util.UUIDHelper;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.List;
import java.util.UUID;

/**
 * Provides several utility methods for commands, and some unrelated methods.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @apiNote This class is basically the BungeeCord port of the same-named class in Bukkit.
 * @since 2017-08-12 / 4.5.0 / legacy XYC class dating back to 2013
 */
public class CommandHelper {
    /**
     * Trims given input to sixteen characters of length and prepends it with given prefix if possible. The prefix is
     * not prepended if the combination of prefix and input exceeds sixteen characters. If the prefix is null, it is not
     * prepended.
     *
     * @param input  the input string to trim
     * @param prefix the prefix to append if possible, or null to just trim
     * @return a string with a length of sixteen characters or less
     * @throws NullPointerException if given input is null
     */
    public static String sixteenCharColorize(String input, String prefix) {
        Preconditions.checkNotNull(input, "input");
        if (prefix == null) {
            return CommandHelper.sixteenCharLimit(input);
        }
        if ((input.length() + prefix.length()) > 16) {
            return CommandHelper.sixteenCharLimit(input);
        }
        return prefix.concat(input);
    }

    /**
     * Makes sure that a string does not exceed 16 characters in length and removes characters from the end if
     * necessary.
     *
     * @param input the input string that might exceed 16 characters in length
     * @return the input, but limited to 16 characters in length
     * @throws NullPointerException if given input is null
     */
    public static String sixteenCharLimit(String input) {
        Preconditions.checkNotNull(input, "input");
        if (input.length() > 16) {
            return input.substring(0, 16);
        }
        return input;
    }

    /**
     * Removes irrelevant tab-complete suggestions from given list of suggestions. If given args are empty, or given
     * suggestions are null, or the final element of given args is empty, given suggestions are returned unmodified.
     *
     * @param args        the array of arguments already entered
     * @param suggestions the list of suggestions
     * @return {@code suggestions}, with all elements not starting with the last element of {@code args} removed.
     */
    public static List<String> tabCompleteArgs(String[] args, List<String> suggestions) {
        if (suggestions == null || args.length == 0) {
            return suggestions;
        }
        String finalArg = args[args.length - 1];
        suggestions.removeIf(s -> !s.startsWith(finalArg));
        return suggestions;
    }

    /**
     * Figures out a representative unique id for given sender. If given sender does not have a unique id assigned by
     * BungeeCord, {@link UUIDHelper#NIL_UUID} is returned.
     *
     * @param sender the sender
     * @return a unique id representative for given sender
     * @implNote this implementation returns {@link net.md_5.bungee.api.connection.ProxiedPlayer#getUniqueId()} for all
     * arguments that are instances of {@link net.md_5.bungee.api.connection.ProxiedPlayer}, and {@link
     * UUIDHelper#NIL_UUID} for everything else
     */
    public static UUID getSenderId(CommandSender sender) {
        if (sender instanceof ProxiedPlayer) {
            return ((ProxiedPlayer) sender).getUniqueId();
        } else {
            return UUIDHelper.NIL_UUID;
        }
    }
}
