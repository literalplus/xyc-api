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

package li.l1t.common.bungee.command;


import com.google.common.base.Preconditions;
import li.l1t.common.bungee.util.CommandHelper;
import li.l1t.common.chat.XyComponentBuilder;
import li.l1t.common.command.AbstractCommandExecution;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Locale;
import java.util.UUID;

/**
 * A simple implementation of a {@link BungeeExecution}, holding metadata about a command execution.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2017-08-12 / 4.5.0
 */
public class SimpleBungeeExecution extends AbstractCommandExecution implements BungeeExecution {
    private final CommandSender sender;
    private final Command command;

    public SimpleBungeeExecution(CommandSender sender, Command command, String label, String[] args) {
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
        if (sender instanceof ProxiedPlayer) {
            return ((ProxiedPlayer) sender).getLocale();
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
        respond(new XyComponentBuilder(String.format(message, params)));
    }

    @Override
    public void respond(BaseComponent[] message) {
        sender.sendMessage(message);
    }

    @Override
    public boolean isPlayer() {
        return sender instanceof ProxiedPlayer;
    }

    @Override
    public ProxiedPlayer player() {
        requireIsPlayer();
        return (ProxiedPlayer) sender;
    }

    @Override
    public boolean hasPermission(String permission) {
        Preconditions.checkNotNull(permission, "permission");
        return sender.hasPermission(permission);
    }
}
