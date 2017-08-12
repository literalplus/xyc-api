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

package li.l1t.common.command;

import li.l1t.common.chat.FormattedResponse;
import li.l1t.common.chat.Response;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Stores basic information about an individual execution of a Bukkit command and provides methods for convenient access
 * of these.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2016-10-26 / 4.4.0
 */
public interface BukkitExecution extends CommandExecution {
    /**
     * @return the sender that invoked this execution
     */
    CommandSender sender();

    /**
     * @return the player that invoked this execution
     * @throws PlayerOnlyException if the sender of this execution is not a player
     */
    Player player();

    /**
     * @return the command managing this execution
     */
    Command command();

    /**
     * Sends a response to this execution's sender with given arguments.
     *
     * @param response  the response to use for formatting
     * @param arguments the arguments for the response
     * @see Response for details on how responses work
     */
    void respond(Response response, Object... arguments);

    /**
     * Sends a formatted response to this execution's sender with given parameters.
     *
     * @param response   the formatted response to send
     * @param message    the message to pass to the formatted response
     * @param parameters the parameters for the response
     * @see FormattedResponse#sendTo(CommandSender, Object...) for details on how formatted responses work and why this
     * is a separate method
     */
    void respond(FormattedResponse response, String message, Object... parameters);
}
