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

/**
 * Represents an exception that will be shown to command senders as error they caused by providing invalid arguments.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2016-08-23
 */
public class UserException extends NonSensitiveException {

    public UserException(Message message) {
        super(message);
    }

    public UserException(Message message, Throwable cause) {
        super(message, cause);
    }

    public UserException(String staticMessage) {
        super(staticMessage);
    }

    /**
     * Creates a new UserException with a {@link String#format(String, Object...)}-like static message pattern and
     * parameters.
     *
     * @param messagePattern the pattern for the message
     * @param params         the parameters
     */
    public UserException(String messagePattern, Object... params) {
        this(String.format(messagePattern, params));
    }

    public UserException(String staticMessage, Throwable cause) {
        super(staticMessage, cause);
    }

    /**
     * Wraps an arbitrary exception in a UserException, <b>retaining its message</b>. Note that for some exception
     * types, sensitive information may be included in the message, so consider using {@link #wrap(Exception, String)}
     * for these. <p>If there is a specific wrap method for the type of exception provided, this method forwards this
     * call there.</p>
     *
     * @param cause the exception to wrap
     * @return a new UserException
     */
    public static UserException wrap(Exception cause) {
        if (cause instanceof NumberFormatException) {
            return wrapNotANumber((NumberFormatException) cause);
        } else if (cause instanceof IllegalArgumentException) {
            return wrap((IllegalArgumentException) cause);
        } else if (cause instanceof IllegalStateException) {
            return wrap((IllegalStateException) cause);
        }
        return wrap(cause, cause.getMessage());
    }

    /**
     * Wraps an IllegalArgumentException in a UserException, retaining its message.
     *
     * @param cause the exception to wrap
     * @return a new UserException with cause and message
     */
    public static UserException wrap(IllegalArgumentException cause) {
        return wrap(cause, cause.getMessage());
    }

    /**
     * Wraps an IllegalStateException in a UserException, retaining its message.
     *
     * @param cause the exception to wrap
     * @return a new UserException with cause and message
     */
    public static UserException wrap(IllegalStateException cause) {
        return wrap(cause, cause.getMessage());
    }

    /**
     * Wraps an exception in a UserException, with provided message on the UserException.
     *
     * @param cause   the exception to wrap as a cause
     * @param message the message for the UserException
     * @return a new UserException
     */
    public static UserException wrap(Exception cause, String message) {
        return new UserException(message, cause);
    }

    /**
     * Wraps a NumberFormatException, replacing the "For Input string: " prefix on its message with a nicer German
     * message.
     *
     * @param nfe the exception to wrap
     * @return a new UserException
     */
    public static UserException wrapNotANumber(NumberFormatException nfe) {
        String strippedMessage = nfe.getMessage().replace("For Input string: ", ""); //For input string: "notanint"
        return new UserException(Message.of("x!api!error.notanumber", strippedMessage));
    }

    @Override
    public boolean needsLogging() {
        return false;
    }

    @Override
    public String getColorWrapperKey() {
        return "x!api!format.error-user";
    }
}
