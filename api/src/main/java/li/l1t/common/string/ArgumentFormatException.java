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

package li.l1t.common.string;

/**
 * Thrown if an argument to {@link Args} is incorrectly formatted.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2017-03-10
 */
public class ArgumentFormatException extends RuntimeException {
    private final String actual;
    private final int index;
    private final String expected;

    public ArgumentFormatException(String actual, int index, String expected, Throwable cause) {
        super(createMessage(actual, index, expected), cause);
        this.actual = actual;
        this.index = index;
        this.expected = expected;
    }

    public ArgumentFormatException(String actual, int index, String expected) {
        this(actual, index, expected, null);
    }

    private static String createMessage(String text, int index, String expected) {
        return text + "(#" + index + "): Expected " + expected;
    }

    public String getActual() {
        return actual;
    }

    public int getIndex() {
        return index;
    }

    public String getExpected() {
        return expected;
    }
}
