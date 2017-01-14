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
import li.l1t.common.exception.UserException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Thrown if a command may only be executed by players but the sender is not a player.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2016-10-25
 */
public class PlayerOnlyException extends UserException {
    public PlayerOnlyException(String messagePattern, Object... params) {
        super(messagePattern, params);
    }

    public static void checkIsPlayer(CommandSender target, String details, Object... params) {
        Preconditions.checkNotNull(target, "target");
        if (!(target instanceof Player)) {
            throw new PlayerOnlyException("Dieser Befehl kann nur von Spielern verwendet werden. " + details, params);
        }
    }
}
