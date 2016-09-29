/*
 * MIT License
 *
 * Copyright (c) 2016 Philipp Nowak (Literallie)
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

/**
 * Represents an exception that will be shown to command senders as error they caused by providing
 * invalid arguments.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 2016-08-23
 */
public class UserException extends NonSensitiveException {
    public UserException(String message) {
        super(message);
    }

    /**
     * Creates a new UserException with a {@link String#format(String, Object...)}-like message
     * pattern and parameters.
     *
     * @param messagePattern the pattern for the message
     * @param params         the parameters
     */
    public UserException(String messagePattern, Object... params) {
        super(String.format(messagePattern, params));
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

    public static UserException wrap(Exception cause) {
        return wrap(cause, cause.getMessage());
    }

    public static UserException wrap(IllegalArgumentException cause) {
        return wrap(cause, cause.getMessage());
    }

    public static UserException wrap(IllegalStateException cause) {
        return wrap(cause, cause.getMessage());
    }

    public static UserException wrap(Exception cause, String message) {
        return new UserException(message, cause);
    }

    public static UserException wrapNotANumber(NumberFormatException nfe) {
        String strippedMessage = nfe.getMessage().replace("For Input string: ", ""); //For input string: "notanint"
        return new UserException("Das ist keine Zahl: %s", strippedMessage);
    }

    @Override
    public boolean needsLogging() {
        return false;
    }
}
