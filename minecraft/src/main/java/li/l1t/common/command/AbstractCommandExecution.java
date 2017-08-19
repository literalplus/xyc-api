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
import li.l1t.common.chat.XyComponentBuilder;
import li.l1t.common.i18n.Message;
import li.l1t.common.i18n.XycI18n;
import li.l1t.common.string.Args;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;

/**
 * Abstract base class for {@link CommandExecution} implementations that is not platform-specific.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2017-08-12
 */
public abstract class AbstractCommandExecution extends Args implements CommandExecution {
    private final String label;

    public AbstractCommandExecution(String label, String[] args) {
        super(args);
        this.label = Preconditions.checkNotNull(label, "label");
    }

    @Override
    public String label() {
        return label;
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
    public void respond(Message message) {
        respond(XycI18n.getMessage(locale(), message));
    }

    @Override
    public void respond(ComponentBuilder messageBuilder) {
        respond(messageBuilder.create());
    }

    @Override
    public void respondUsage(String subCommand, String arguments, String description) {
        String commandLine = formatCommandLineForSub(subCommand);
        respond(
                new XyComponentBuilder(commandLine, ChatColor.YELLOW)
                        .suggest(commandLine)
                        .tooltip(XycI18n.getMessage(locale(), "x!mc!click-to-copy", commandLine))
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
    public void requirePermission(String permission) throws UserPermissionException {
        if (!hasPermission(permission)) {
            throw new UserPermissionException(permission);
        }
    }

    @Override
    public void requireIsPlayer() throws PlayerOnlyException {
        if (!isPlayer()) {
            throw new PlayerOnlyException();
        }
    }
}
