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

package li.l1t.common.chat;

import org.bukkit.Server;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

/**
 * A response that uses a generic format and allows to specify arbitrary string messages and arguments directly.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2017-01-15 / 4.4.0
 */
public interface FormattedResponse extends Response {
    /**
     * Formats a message of this type according to the format's template.
     *
     * @param message   the message to format, with arguments represented like in {@link String#format(String,
     *                  Object...)}
     * @param arguments the arguments for the message
     * @return the formatted message
     */
    String format(String message, Object... arguments);

    /**
     * Formats a message of this type and sends the formatted message to a command sender.
     *
     * @param sender    the receiver of the message
     * @param message   the message to format
     * @param arguments the arguments for the message
     * @see #format(String, Object...) for details on formatting
     */
    void sendTo(CommandSender sender, String message, Object... arguments);

    /**
     * Formats a message of this type and sends the formatted message to all players on given
     * server.
     *
     * @param server    the server to get players from
     * @param message   the message to format
     * @param arguments the arguments for the message
     * @see #format(String, Object...) for details on formatting
     */
    void broadcast(Server server, String message, Object... arguments);

    /**
     * {@inheritDoc}
     * <p>Formats a list of {@link Response} arguments with this formatted response. This works by first
     * converting the first argument to a string and taking that as message pattern for {@link #format(String,
     * Object...)}. Then, the first argument from given arguments (that has been converted to a message pattern) is
     * removed and the remaining arguments also passed to the format method. If given arguments are empty or null,
     * an empty string and no arguments are passed to the format method.</p>
     * <p><b>Note:</b> For {@link FormattedResponse} objects, prefer the more strict {@link #format(String, Object...)}
     * method. That one provides type-checking for the first string argument, makes the intention clearer and avoids
     * an unnecessary array copy.</p>
     *
     * @param arguments {@inheritDoc}
     * @return {@inheritDoc}
     * @apiNote The reason a default method is provided in the interface instead of having an abstract base class is
     * that this way, we can still have enums with hard-coded formats.
     */
    @Override
    default String format(Object... arguments) {
        if (arguments == null || arguments.length == 0) {
            return format("");
        } else {
            String message = String.valueOf(arguments[0]);
            Object[] actualArguments = Arrays.copyOfRange(arguments, 1, arguments.length);
            return format(message, actualArguments);
        }
    }

    /**
     * {@inheritDoc} <p>Broadcasts a list of {@link Response} arguments using this formatted response.</p>
     * <p><b>Note:</b> For {@link FormattedResponse} objects, prefer the more strict {@link #broadcast(Server,
     * Object...)} method. That one provides type-checking for the first string argument, makes the intention clearer
     * and avoids an unnecessary array copy.</p>
     *
     * @param arguments {@inheritDoc}
     * @return {@inheritDoc}
     * @see #format(Object...) for notes on how the arguments are processed
     */
    @Override
    default void broadcast(Server server, Object... arguments) {
        if (arguments == null || arguments.length == 0) {
            broadcast(server, "");
        } else {
            String message = String.valueOf(arguments[0]);
            Object[] actualArguments = Arrays.copyOfRange(arguments, 1, arguments.length);
            broadcast(server, message, actualArguments);
        }
    }

    /**
     * {@inheritDoc} <p>Sends a list of {@link Response} arguments using this formatted response.</p> <p><b>Note:</b>
     * For {@link FormattedResponse} objects, prefer the more strict {@link #sendTo(CommandSender, String, Object...)}
     * method. That one provides type-checking for the first string argument, makes the intention clearer and avoids an
     * unnecessary array copy.</p>
     *
     * @param arguments {@inheritDoc}
     * @return {@inheritDoc}
     * @see #format(Object...) for notes on how the arguments are processed
     */
    @Override
    default void sendTo(CommandSender sender, Object... arguments) {
        if (arguments == null || arguments.length == 0) {
            sendTo(sender, "");
        } else {
            String message = String.valueOf(arguments[0]);
            Object[] actualArguments = Arrays.copyOfRange(arguments, 1, arguments.length);
            sendTo(sender, message, actualArguments);
        }
    }
}
