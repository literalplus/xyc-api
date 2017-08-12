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


/**
 * Static utility class that contains a few helpful methods for wrapping messages in common formats.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2017-08-12 / 4.5.0
 */
public class DefaultFormat {
    private static Message wrapIn(String key, Message message) {
        return Message.of("x!api!format." + key, message);
    }

    public static Message broadcast(Message message) {
        return wrapIn("broadcast", message);
    }

    public static Message broadcast(String key, Object... arguments) {
        return wrapIn("broadcast", Message.of(key, arguments));
    }

    public static Message internalError(Message message) {
        return wrapIn("error-internal", message);
    }

    public static Message internalError(String key, Object... arguments) {
        return wrapIn("error-internal", Message.of(key, arguments));
    }

    public static Message userError(Message message) {
        return wrapIn("error-user", message);
    }

    public static Message userError(String key, Object... arguments) {
        return wrapIn("error-user", Message.of(key, arguments));
    }

    public static Message warning(Message message) {
        return wrapIn("warning", message);
    }

    public static Message warning(String key, Object... arguments) {
        return wrapIn("warning", Message.of(key, arguments));
    }

    public static Message result(Message message) {
        return wrapIn("result", message);
    }

    public static Message result(String key, Object... arguments) {
        return wrapIn("result", Message.of(key, arguments));
    }

    public static Message success(Message message) {
        return wrapIn("result-success", message);
    }

    public static Message success(String key, Object... arguments) {
        return wrapIn("result-success", Message.of(key, arguments));
    }

    public static Message listHeader(Message message) {
        return wrapIn("list-header", message);
    }

    public static Message listHeader(String key, Object... arguments) {
        return wrapIn("list-header", Message.of(key, arguments));
    }

    public static Message header(Message message) {
        return wrapIn("header", message);
    }

    public static Message header(String key, Object... arguments) {
        return wrapIn("header", Message.of(key, arguments));
    }

    public static Message listItem(Message message) {
        return wrapIn("list-item", message);
    }

    public static Message listItem(String key, Object... arguments) {
        return wrapIn("list-item", Message.of(key, arguments));
    }

    public static Message bool(boolean input) {
        return Message.of(input ? "x!api!yes" : "x!api!no");
    }

    public static Message rank(long num) {
        if (num >= 11L && num <= 13L) {
            return Message.of("x!api!number.other", num); //eleventh, twelfth, thirteenth
        }
        switch ((int) (num % 10L)) {
            case 1:
                return Message.of("x!api!number.one", num);
            case 2:
                return Message.of("x!api!number.two", num);
            case 3:
                return Message.of("x!api!number.three", num);
            default:
                return Message.of("x!api!number.other", num);
        }
    }
}
