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

package li.l1t.common.bungee.chat;

import com.google.common.base.Preconditions;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.util.Arrays;


/**
 * Static utility class that allows to send {@link ComponentBuilder}s to BungeeCord {@link CommandSender} instances.
 * This allows to use the builder but still not exclude non-Player senders from receiving formatted messages.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2017-08-12 / 4.5.0
 */
public class ComponentSender {
    private ComponentSender() {

    }

    /**
     * Sends the built components from the given builder to a receiver. Players will receiver fully formatted JSON chat
     * including events and all that fancy stuff, everything else will receive solely the legacy text representation.
     *
     * @param builder  the builder to use to create the parts to send
     * @param receiver the receiver of the parts
     * @return whether a message was sent
     */
    public static boolean sendTo(ComponentBuilder builder, CommandSender receiver) {
        return sendTo(builder.create(), receiver);
    }

    /**
     * Sends the built components from the given builder to a receiver. Players will receive fully formatted JSON chat
     * including events and all that fancy stuff, everything else will receive solely the legacy text representation.
     * <p>This method allows to concat multiple {@code BaseComponent[]}s. This is useful when converted legacy text
     * needs to be combined with native components, for example when a part of the message is localised with legacy
     * formatting codes.</p>
     *
     * @param receiver the receiver of the parts
     * @param first    the first components of the message
     * @param rest     the remaining components of the message
     * @return whether a message was sent
     */
    public static boolean sendTo(CommandSender receiver, BaseComponent[] first, BaseComponent[]... rest) {
        return sendTo(concatAll(first, rest), receiver);
    }

    /**
     * Sends the given components to a receiver. Players will receiver fully formatted JSON chat including events and
     * all that fancy stuff, everything else will receive solely the legacy text representation.
     *
     * @param parts    the parts to send
     * @param receiver the receiver of the parts
     * @return whether a message was sent
     */
    public static boolean sendTo(BaseComponent[] parts, CommandSender receiver) {
        receiver.sendMessage(parts);
        return true;
    }

    private static BaseComponent[] concatAll(BaseComponent[] first, BaseComponent[]... rest) {
        Preconditions.checkNotNull(first, "first");
        Preconditions.checkNotNull(rest, "rest");
        int totalLength = first.length;
        for (BaseComponent[] array : rest) {
            totalLength += array.length;
        }
        BaseComponent[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (BaseComponent[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }
}
