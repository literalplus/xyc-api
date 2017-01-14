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

import com.google.common.base.Preconditions;
import li.l1t.common.chat.Arguments;
import li.l1t.common.chat.ResponseFormat;
import li.l1t.common.chat.XyComponentBuilder;
import li.l1t.common.exception.UserException;
import li.l1t.common.util.CommandHelper;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * A simple implementation of a {@link BukkitExecution}, holding metadata about a command
 * execution.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2016-10-26 / 4.4.0
 */
public class SimpleBukkitExecution implements BukkitExecution {
    private final CommandSender sender;
    private final Command command;
    private final String label;
    private final String[] args;

    public SimpleBukkitExecution(CommandSender sender, Command command, String label, String[] args) {
        this.sender = Preconditions.checkNotNull(sender, "sender");
        this.command = Preconditions.checkNotNull(command, "command");
        this.label = Preconditions.checkNotNull(label, "label");
        this.args = Preconditions.checkNotNull(args, "args");
    }

    @Override
    public CommandSender sender() {
        return sender;
    }

    @Override
    public UUID senderId() {
        return CommandHelper.getSenderId(sender);
    }

    @Override
    public String senderName() {
        return sender.getName();
    }

    @Override
    public String[] args() {
        return args;
    }

    @Override
    public List<String> argsList() {
        return Arrays.asList(args);
    }

    @Override
    public Stream<String> argsStream() {
        return Arrays.stream(args);
    }

    @Override
    public String arg(int index) {
        if (!hasArg(index)) {
            throw MissingArgumentException.forIndex(index);
        }
        return args[index];
    }

    @Override
    public int intArg(int index) {
        try {
            return Integer.parseInt(arg(index));
        } catch (NumberFormatException e) {
            throw new UserException("Argument %d: Das ist keine Zahl: '%s'", index + 1, arg(index));
        }
    }

    @Override
    public UUID uuidArg(int index) {
        try {
            return UUID.fromString(arg(index));
        } catch (IllegalArgumentException e) {
            throw new UserException(
                    "Argument %d: Das ist keine UUID: '%s'. Eine valide UUID ist zum Beispiel '%s'.",
                    index + 1, arg(index), UUID.randomUUID()
            );
        }
    }

    @Override
    public Optional<String> findArg(int index) {
        return hasArg(index) ? Optional.of(arg(index)) : Optional.empty();
    }

    @Override
    public boolean hasArg(int index) {
        return args.length > index;
    }

    @Override
    public boolean hasNoArgs() {
        return args.length == 0;
    }

    @Override
    public String joinedArgs(int startIndex) {
        return Arguments.joinRange(args(), startIndex, 0);
    }

    @Override
    public String joinedArgsColored(int startIndex) {
        return Arguments.joinRangeColored(args(), startIndex, 0);
    }

    @Override
    public Command command() {
        return command;
    }

    @Override
    public String label() {
        return label;
    }

    @Override
    public void respond(String message, Object... params) {
        sender.sendMessage(String.format(
                message,
                params
        ));
    }

    @Override
    public void respond(ResponseFormat format, Object... params) {
        format.sendTo(sender, params);
    }

    @Override
    public void respond(BaseComponent[] message) {
        ComponentSender.sendTo(message, sender);
    }

    @Override
    public void respond(ComponentBuilder messageBuilder) {
        ComponentSender.sendTo(messageBuilder, sender);
    }

    @Override
    public void respondUsage(String subCommand, String arguments, String description) {
        String commandLine = formatCommandLineForSub(subCommand);
        respond(
                new XyComponentBuilder(commandLine, ChatColor.YELLOW)
                        .suggest(commandLine)
                        .tooltip("§eKlicken zum Kopieren:\n" + commandLine)
                        .appendIf(!arguments.isEmpty(), " " + arguments)
                        .append(" ", ComponentBuilder.FormatRetention.NONE)
                        .append(description, ChatColor.GOLD)
        );
    }

    private String formatCommandLineForSub(String subCommand) {
        if (subCommand.isEmpty()) {
            return "/" + label();
        } else {
            return "/" + label() + " " + subCommand;
        }
    }

    @Override
    public void requireIsPlayer() throws PlayerOnlyException {
        PlayerOnlyException.checkIsPlayer(sender(), "/%s %s", label, joinedArgs(0));
    }

    @Override
    public Player player() {
        requireIsPlayer();
        return (Player) sender;
    }

    @Override
    public void requirePermission(String permission) throws UserPermissionException {
        UserPermissionException.checkPermission(sender(), permission);
    }

    @Override
    public boolean hasPermission(String permission) {
        Preconditions.checkNotNull(permission, "permission");
        return sender.hasPermission(permission);
    }
}
