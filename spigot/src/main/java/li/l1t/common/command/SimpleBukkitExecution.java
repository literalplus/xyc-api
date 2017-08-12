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
import li.l1t.common.chat.ComponentSender;
import li.l1t.common.chat.FormattedResponse;
import li.l1t.common.chat.Response;
import li.l1t.common.i18n.MinecraftLocale;
import li.l1t.common.util.CommandHelper;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Locale;
import java.util.UUID;

/**
 * A simple implementation of a {@link BukkitExecution}, holding metadata about a command execution.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2016-10-26 / 4.4.0
 */
public class SimpleBukkitExecution extends AbstractCommandExecution implements BukkitExecution {
    private final CommandSender sender;
    private final Command command;

    public SimpleBukkitExecution(CommandSender sender, Command command, String label, String[] args) {
        super(label, args);
        this.sender = Preconditions.checkNotNull(sender, "sender");
        this.command = Preconditions.checkNotNull(command, "command");
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
    public Locale locale() {
        if (sender instanceof Player) {
            return MinecraftLocale.toJava(((Player) sender).spigot().getLocale());
        } else {
            return Locale.ENGLISH;
        }
    }

    @Override
    public Command command() {
        return command;
    }

    @Override
    public void respond(String message, Object... params) {
        sender.sendMessage(String.format(
                message,
                params
        ));
    }

    @Override
    public void respond(Response response, Object... arguments) {
        response.sendTo(sender, arguments);
    }

    @Override
    public void respond(FormattedResponse response, String message, Object... parameters) {
        response.sendTo(sender, message, parameters);
    }

    @Override
    public void respond(BaseComponent[] message) {
        ComponentSender.sendTo(message, sender);
    }

    @Override
    public boolean isPlayer() {
        return sender instanceof Player;
    }

    @Override
    public Player player() {
        requireIsPlayer();
        return (Player) sender;
    }

    @Override
    public boolean hasPermission(String permission) {
        Preconditions.checkNotNull(permission, "permission");
        return sender.hasPermission(permission);
    }
}
