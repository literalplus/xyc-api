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

/**
 * A specific format of response that may be sent to {@link CommandSender}s. All methods allow to specify arguments. The
 * underlying format may either be static or dynamically retrieved from a message source. Further, the way the string
 * message arguments are handled is up to the specific implementation. Specifically, implementations may choose to apply
 * pre-processing to messages. Such pre-processing may also include localisation.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @apiNote <p>A clean way to implement this for static formats is to use an enum that implements this interface. This
 * allows static access from anywhere in code and allows to make intentions clear using the enum constant names.</p><p>
 * A good way to provide generic message formats that do not specify the actual content of the message is to always
 * convert the first argument to a string and then use it as {@link String#format(String, Object...)} pattern for the
 * remaining arguments.</p>
 * @since 2017-01-14 / 4.4.0
 */
public interface Response {
    /**
     * Formats a message of this type with given arguments and returns the string representation of the result.
     *
     * @param arguments the arguments for the message
     * @return the formatted message
     */
    String format(Object... arguments);

    /**
     * Formats a message of this type and sends the formatted message to a command sender.
     *
     * @param sender    the receiver of the message
     * @param arguments the arguments for the message
     */
    void sendTo(CommandSender sender, Object... arguments);

    /**
     * Formats a message of this type and sends the formatted message to all players on given
     * server.
     *
     * @param server    the server to get players from
     * @param arguments the arguments for the message
     */
    void broadcast(Server server, Object... arguments);
}
