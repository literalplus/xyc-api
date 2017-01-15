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
import li.l1t.common.util.CommandHelper;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Stores basic information about an individual execution of a Bukkit command and provides methods for convenient access
 * of these.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2016-10-26 / 4.4.0
 */
public interface BukkitExecution extends ArgumentExecution {
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
     * @return a unique id representing the command sender, according to the specifications detailed in {@link
     * CommandHelper#getSenderId(CommandSender)}
     */
    UUID senderId();

    /**
     * @return the name of the sender executing the command, as returned by {@link CommandSender#getName()}
     */
    String senderName();

    /**
     * @param startIndex the index of the first argument to process
     * @return the space-separated string of arguments passed to this execution, with {@link
     * net.md_5.bungee.api.ChatColor#translateAlternateColorCodes(char, String)} alternate color codes} replaced with
     * '&amp;'
     */
    String joinedArgsColored(int startIndex);

    /**
     * @return the command managing this execution
     */
    Command command();

    /**
     * @return the alias the command was executed with
     */
    String label();

    /**
     * Sends a message to the command sender.
     *
     * @param message the message pattern
     * @param params  the arguments for given pattern, which will be inserted into the pattern like {@link
     *                String#format(String, Object...)} does
     */
    void respond(String message, Object... params);

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

    /**
     * Sends a message to this execution's sender, first converting it to legacy text if the sender is
     * not a player.
     *
     * @param message the message to send
     */
    void respond(BaseComponent[] message);

    /**
     * Sends a message to this execution's sender, first converting it to legacy text if the sender is
     * not a player.
     *
     * @param messageBuilder the builder that will be used to obtain the message to send
     */
    void respond(ComponentBuilder messageBuilder);

    /**
     * Sends a message to this execution's sender, indicating the intended usage of given sub command
     * using given description and details. This uses this execution's {@link #label()}.
     *
     * @param subCommand  the sub command the usage refers to, or an empty string for the main command
     * @param arguments   the standardised description of the arguments required for given sub command
     * @param description a short ellipsis of what given sub command does
     */
    void respondUsage(String subCommand, String arguments, String description);

    /**
     * @throws PlayerOnlyException if the sender is not an instance of {@link org.bukkit.entity.Player}
     */
    void requireIsPlayer() throws PlayerOnlyException;

    /**
     * @param permission the permission required to proceed
     * @throws UserPermissionException if the sender does not have given permission
     */
    void requirePermission(String permission) throws UserPermissionException;

    /**
     * @param permission the permission to check for
     * @return whether the sender of this execution has given permission
     */
    boolean hasPermission(String permission);
}
