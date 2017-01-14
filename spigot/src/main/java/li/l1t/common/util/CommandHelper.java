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
package li.l1t.common.util;

import com.google.common.base.Preconditions;
import li.l1t.common.command.BukkitExecution;
import li.l1t.common.command.CommandBehaviours;
import li.l1t.common.command.PlayerOnlyException;
import li.l1t.common.inventory.UUIDHelper;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Provides several utility methods for commands, and some unrelated methods. This class has been historically evolving
 * since the initial release of XYC in 2013. Many methods have been removed in 2017, when it was moved to the public
 * XYC-API.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 4.4.0 / legacy XYC class dating back to 2013
 */
public class CommandHelper {
    /**
     * Broadcasts a string message to all players online on the server that have given permission.
     *
     * @param msg        the message to send
     * @param permission the permission required to receive the message, or null to send to all players on the server
     * @implNote Contrary to the {@link Bukkit#broadcast(String, String)} method, this method works with the
     * PermissionsEx plugin.
     */
    public static void broadcast(@Nonnull String msg, @Nullable String permission) {
        for (Player plr : Bukkit.getOnlinePlayers()) {
            if (permission == null || plr.hasPermission(permission)) {
                plr.sendMessage(msg);
            }
        }
        Bukkit.getLogger().info("[XYC] Broadcast: " + msg);
    }

    /**
     * Comma separates a Collection's contents' String representations. This is the same as calling {@link
     * CommandHelper#CSCollection(Iterable, String)} with "{empty}" as default.
     *
     * @param input An Iterable to separate
     * @return Element1, Element2, Element3 OR "{empty}"
     * @see CommandHelper#CSCollection(Iterable, String)
     * @deprecated This method's name does not respect Java naming conventions, and what it does can be achieved in a
     * cleaner way using Streams and {@link Collectors#joining()}.
     */
    public static String CSCollection(Iterable<?> input) {
        return CSCollection(input, "{empty}");
    }

    /**
     * Comma separates a Collection's children's String representations.
     *
     * @param input      An Iterable to separate
     * @param defaultVal value to be returned if {@code col} is empty.
     * @return Element1, Element2, Element3 OR {@code defaultVal}
     * @deprecated This method's name does not respect Java naming conventions, and what it does can be achieved in a
     * cleaner way using Streams and {@link Collectors#joining()}.
     */
    public static String CSCollection(Iterable<?> input, String defaultVal) {
        if (input == null) {
            return "~~~null~~~";
        }

        Iterator<?> i = input.iterator();
        if (!i.hasNext()) {
            return defaultVal;
        }
        final StringBuilder rtrnBuilder = new StringBuilder(String.valueOf(i.next()));
        while (i.hasNext()) {
            rtrnBuilder.append(", ")
                    .append(i.next());
        }
        return rtrnBuilder.toString();
    }

    /**
     * <p>Formats {@code seconds} for a human-readable output in german. If {@code seconds &gt;= 60}, the output will be
     * formatted like this: <i>x Minuten und y Sekunden</i> <b>Notice:</b> Currently, there is no support for hours.
     * </p> <b>Examples:</b> 1 -&gt; <i>1 Sekunde</i> 46 -&gt; <i>46 Sekunden</i> 60 -&gt; <i>1 Minute</i> 61 -&gt; <i>1
     * Minute und 1 Sekunde</i> 90 -&gt; <i>1 Minute und 30 Sekunden</i> 120 -&gt; <i>2 Minuten</i> 125 -&gt; <i>2
     * Minuten und 5 Sekunden</i>
     *
     * @param seconds Time to be formatted, in seconds.
     * @return a human-readable time string <b>in German</b>.
     * @deprecated Implemented for a specific language, with no possibility to change locale. Deprecated without
     * replacement.
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Deprecated
    public static String formatSeconds(int seconds) {
        if (seconds < 60) {
            return seconds + " Sekunde" + ((seconds == 1) ? "" : "n");
        }
        short minutes = (short) (seconds / 60);
        seconds -= (60 * minutes);
        return minutes + " Minute" + ((minutes == 1) ? "" : "n") + ((seconds == 0) ? "" : " und " + seconds + " Sekunde" + ((seconds == 1) ? "" : "n"));
    }

    /**
     * Returns a string representing the size of each element in {@code values} in ASCII art. {@code values} may only
     * contain up to 16 values (Hexadecimal)
     *
     * @param maxLength how long the string shall be.
     * @param values    values.
     * @param max       The highest value in {@code values}.
     * @return 1111112222233333
     * @deprecated Implementation not clean, not generic enough
     */
    @Deprecated
    public static String getBarOfValues(int maxLength, List<Integer> values, int max) {
        Preconditions.checkArgument(values.size() <= 16, "Values may not contain more than 16 items!");

        maxLength -= 2; //why?
        int i = 0;
        final StringBuilder rtrnBuilder = new StringBuilder();
        for (int val : values) {
            float factor = (((float) val) / ((float) max));
            short drawCount = (short) (maxLength * factor);
            rtrnBuilder.append("\u00a7")
                    .append(Integer.toHexString(i))
                    .append(StringUtils.rightPad("", drawCount, (char) ('0' + i)));// there should never be more than 16 full items.
            i++;
        }
        return rtrnBuilder.toString();
    }

    /**
     * Returns the names of all online Players as a List. Actually kinda useful because it returns the NAMES and not the
     * corresponding objects.
     *
     * @return A List of all online Players' names.
     * @see Bukkit#getOnlinePlayers()
     */
    public static List<String> getOnlinePlayerNames() {
        final List<String> rtrn = new ArrayList<>();
        for (final Player plr : Bukkit.getOnlinePlayers()) {
            rtrn.add(plr.getName());
        }
        return rtrn;
    }

    /**
     * Returns an ASCII art bar indicating completion state of a task.
     * <br>
     * <b>Example:</b><br>
     * ████▒▒▒ for (6,1,2)
     *
     * @param maxLength How many characters the bar will take up.
     * @param value     the current progress
     * @param max       the goal (100%)
     * @return A nice ASCII art bar using ASCII blocks.
     */
    public static String getProgressBar(int maxLength, int value, int max) {
        Preconditions.checkArgument(value <= max, "The current progress may not be greater than the goal.");
        double factor = (((double) value) / ((double) max));
        maxLength -= 5;// "[]xx%".length
        byte linesToDraw = (byte) (maxLength * factor);
        return StringUtils.rightPad(StringUtils.rightPad("[", linesToDraw, '\u2588'), maxLength, '\u2592') + "]"
                + StringUtils.leftPad(((byte) (factor * 100)) + "%", 3, '0');
    }

    /**
     * Returns true and displays a message if given sender is not an instance of {@link Player}. Returns false and does
     * nothing otherwise.
     *
     * @param sender the command sender to inspect
     * @param label  the label of the command that was executed
     * @return {@code false} if given sender is an instance of {@link Player}
     * @deprecated This method does not support internationalisation and encourages usage of returns for errors, where
     * exceptions would make the code much more readable and result in less cyclomatic complexity. Use {@link
     * BukkitExecution#requireIsPlayer()}, {@link PlayerOnlyException}, or {@link CommandBehaviours#playerOnly()}
     * instead.
     */
    public static boolean kickConsoleFromMethod(CommandSender sender, String label) {
        if (sender instanceof Player) {
            return false;
        } else {
            sender.sendMessage("§c§lFehler: §cDieser Befehl kann nur von Spielern ausgeführt werden.");
            return true;
        }
    }

    /**
     * Sends given multi-line message to given sender by splitting it into one message per line.
     *
     * @param msg    the message to be sent, may contain newlines that will be translated into separate message
     * @param sender the receiver of the message
     * @return always {@code true}, for legacy compatibility
     */
    public static boolean msg(String msg, CommandSender sender) {
        for (String str2 : msg.split("\n")) {
            sender.sendMessage(str2);
        }
        return true;
    }

    /**
     * Sends a notification for an important action to all online operators and the console.
     *
     * @param sender  the player who performed the action
     * @param message a short description of the action
     * @deprecated What this method does can be achieved easily using only the Bukkit API, specifically by using {@link
     * org.bukkit.command.Command#broadcastCommandMessage(CommandSender, String)} instead.
     */
    public static void sendImportantActionMessage(CommandSender sender, String message) {
        CommandHelper.sendMessageToOpsAndConsole(String.format(
                "§7§o[%s: §a§o%s§7§o]", sender.getName(), message
        ));
    }

    /**
     * Sends a message to all online operators, all players with the {@code xyc.adminmsg} permission, and the console.
     *
     * @param message the message to be sent
     */
    public static void sendMessageToOpsAndConsole(String message) {
        Bukkit.getConsoleSender().sendMessage(message);
        for (Player plr : Bukkit.getOnlinePlayers()) {
            if (plr.isOp() || plr.hasPermission("xyc.adminmsg")) {
                plr.sendMessage(message);
            }
        }
    }

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
     * Bukkit, {@link UUIDHelper#NIL_UUID} is returned.
     *
     * @param sender the sender
     * @return a unique id representative for given sender
     * @implNote this implementation returns {@link Player#getUniqueId()} for all arguments that are instances of {@link
     * Player}, and the nil UUID for everything else
     */
    public static UUID getSenderId(CommandSender sender) {
        if (sender != null && sender instanceof Player) {
            return ((Player) sender).getUniqueId();
        } else {
            return UUIDHelper.NIL_UUID;
        }
    }
}
