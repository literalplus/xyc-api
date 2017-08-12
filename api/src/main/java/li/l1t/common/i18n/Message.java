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

package li.l1t.common.i18n;

import java.util.Arrays;

/**
 * Represents a message that may be translatable or static.
 * <p><b>Note:</b> It is advised that all methods accepting message objects also provide an overload that
 * accepts translation key and arguments, where readability allows, to reduce clutter in code./p>
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2017-03-04
 */
public class Message {
    private final String staticText;
    private final String key;
    private final Object[] arguments;
    private Message fallback;

    protected Message(String staticText, String key, Object... arguments) {
        this.key = key;
        this.arguments = arguments;
        this.staticText = staticText;
    }

    /**
     * @return whether this message is static and does not need translation
     * @see #toString() for the static text if the message is static
     */
    public boolean isStatic() {
        return staticText != null;
    }

    /**
     * @return the translation key of this message, or null if it is static
     */
    public String getKey() {
        return key;
    }

    /**
     * @return the arguments array of this message, or always an empty array if it is static
     */
    public Object[] getArguments() {
        return arguments;
    }

    /**
     * @param message the new {@link #getFallback() fallback message}
     * @return this message
     */
    public Message orElse(Message message) {
        this.fallback = message;
        return this;
    }

    /**
     * @return whether this message has a {@link #getFallback() fallback message}
     */
    public boolean hasFallback() {
        return fallback != null;
    }

    /**
     * @return the fallback message that is to be used if translation of this message fails
     */
    public Message getFallback() {
        return fallback;
    }

    /**
     * Returns a string representation of this message.<p>For {@link #isStatic() static messages}, this is
     * the static text, literally.</p>
     * <p>For dynamic messages with no arguments, this is the {@link #getKey() translation key}.</p>
     * <p>For dynamic messages with arguments, this is the {@link #getKey() translation key}, with the
     * {@link Arrays#toString(Object[]) string representation of the arguments} attached directly without a space.</p>
     *
     * @return a string representation of this message
     */
    @Override
    public String toString() {
        if (isStatic()) {
            return staticText;
        } else if (arguments == null || arguments.length == 0) {
            return key;
        } else {
            return key + Arrays.toString(arguments);
        }
    }

    /**
     * Creates a new dynamic message.
     *
     * @param key       the translation key
     * @param arguments the arguments to pass to the translator
     * @return the created message
     */
    public static Message of(String key, Object... arguments) {
        return new Message(null, key, arguments);
    }

    /**
     * Creates a new static message.
     *
     * @param staticText the static message text
     * @return the created message
     */
    public static Message ofText(String staticText) {
        return new Message(staticText, null);
    }
}
