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

package li.l1t.common.exception;

import li.l1t.common.i18n.Message;
import li.l1t.common.i18n.XycI18n;

import java.util.Locale;

/**
 * A kind of exception whose messages do not contain sensitive content and therefore can be shown to players as-is.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2016-09-29
 */
public abstract class NonSensitiveException extends RuntimeException {
    private Message i18nMessage;

    /**
     * Creates a new exception with an internationalisable message.
     *
     * @param message the message
     */
    public NonSensitiveException(Message message) {
        this(message, null);
    }

    /**
     * Creates a new exception with an internationalisable message and a cause.
     *
     * @param message the message
     * @param cause   the cause
     */
    public NonSensitiveException(Message message, Throwable cause) {
        super(" -- use getMessage() --", cause);
        this.i18nMessage = message;
    }

    /**
     * Creates a new exception with a static message that is not internationalised.
     *
     * @param staticMessage the message describing the exception
     */
    public NonSensitiveException(String staticMessage) {
        this(Message.ofText(staticMessage));
    }

    /**
     * Creates a new exception with a static message that is not internationalised and a cause.
     *
     * @param staticMessage the message describing the exception
     * @param cause         the throwable that caused this exception
     */
    public NonSensitiveException(String staticMessage, Throwable cause) {
        this(Message.ofText(staticMessage), cause);
    }

    /**
     * Creates a new exception with just a cause and an empty message.
     *
     * @param cause the throwable that caused this exception
     */
    public NonSensitiveException(Throwable cause) {
        this("", cause);
    }

    /**
     * @return whether this exception should be logged to allow further investigation
     */
    public abstract boolean needsLogging();

    /**
     * @return the wrapper message key around exception messages, for coloring
     */
    public abstract String getColorWrapperKey();

    /**
     * <p><b>Implementation Note:</b> This returns the output of {@link #getColoredI18nMessage()} for {@link
     * Locale#ENGLISH} in {@link XycI18n}.</p>
     *
     * @return the message of this exception decorated with Minecraft formatting codes
     */
    public String getColoredMessage() {
        return XycI18n.getMessage(Locale.ENGLISH, i18nMessage);
    }

    /**
     * @return the internationalisation message of the exception
     * @since 2017-08-12 / 4.5.0
     */
    public Message getI18nMessage() {
        return i18nMessage;
    }

    /**
     * @return the internationalised message wrapped in a coloring message specific to the exception type
     */
    public Message getColoredI18nMessage() {
        return Message.of(getColorWrapperKey(), getI18nMessage());
    }

    /**
     * {@inheritDoc} <p><b>Implementation Note:</b> This returns the output of {@link #getI18nMessage()} for {@link
     * Locale#ENGLISH} in {@link XycI18n}.</p>
     */
    @Override
    public String getMessage() {
        return XycI18n.getMessage(Locale.ENGLISH, i18nMessage);
    }
}
