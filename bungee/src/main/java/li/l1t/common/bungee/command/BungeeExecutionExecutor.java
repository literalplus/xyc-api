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

import li.l1t.common.command.ExecutionExecutor;
import li.l1t.common.exception.NonSensitiveException;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

/**
 * Abstract base class for execution executors which get notified of executions via the BungeeCord {@link Command} API.
 * Catches {@link NonSensitiveException}s and prints their {@link NonSensitiveException#getColoredI18nMessage() colored
 * messages}.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2017-08-12 / 4.5.0
 */
public abstract class BungeeExecutionExecutor extends Command implements ExecutionExecutor<BungeeExecution> {
    public BungeeExecutionExecutor(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        SimpleBungeeExecution exec = new SimpleBungeeExecution(sender, this, getName(), args);
        try {
            execute(exec);
        } catch (NonSensitiveException e) {
            if (e.needsLogging()) {
                e.printStackTrace();
            }
            exec.respond(e.getColoredI18nMessage());
        }
    }
}
