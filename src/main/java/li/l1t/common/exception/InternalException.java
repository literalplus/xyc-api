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

import java.sql.SQLException;

/**
 * Represents an exception that will be shown to command senders as 'internal exception' with the
 * provided reason string.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 2016-08-23
 */
public class InternalException extends NonSensitiveException {
    public InternalException(String message) {
        super(message);
    }

    public InternalException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Wraps an arbitrary exception in an InternalException, <b>retaining its message</b>. Note that
     * for some exception types, sensitive information may be included in the message, so consider
     * using {@link #wrap(Exception, String)} for these. <p>If there is a specific wrap method for
     * the type of exception provided, this method forwards this call there.</p>
     *
     * @param cause the exception to wrap
     * @return a new InternalException
     */
    public static InternalException wrap(Exception cause) {
        if (cause instanceof SQLException) {
            return wrap((SQLException) cause);
        }
        return wrap(cause, cause.getMessage());
    }

    /**
     * Wraps an SQL exception, hiding away its message behind a generic database error message.
     *
     * @param cause the SQL exception to wrap
     * @return a new InternalException
     */
    public static DatabaseException wrap(SQLException cause) {
        return new DatabaseException(cause);
    }

    /**
     * Wraps an exception in an InternalException, with provided message on the InternalException.
     *
     * @param cause   the exception to wrap as a cause
     * @param message the message for the InternalException
     * @return a new InternalException
     */
    public static InternalException wrap(Exception cause, String message) {
        return new InternalException(message, cause);
    }

    public boolean needsLogging() {
        return getCause() != null;
    }

    @Override
    public String getColoredMessage() {
        return "§4§lInterner Fehler: §c" + getLocalizedMessage();
    }
}
