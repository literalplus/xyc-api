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

package li.l1t.common.sql.sane.result;

import java.sql.PreparedStatement;

/**
 * Represents the result of the execution of a prepared statement in a SQL data source.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2016-10-09
 */
public interface CloseableResult extends AutoCloseable {
    /**
     * Attempts to close the result set held by this result, swallowing any encountered
     * exceptions. This might also close the statement, depending on whether the implementation is
     * built for single-use or reusable statements.
     */
    @Override
    void close();

    /**
     * @return the statement that was used to create this result
     */
    PreparedStatement getStatement();
}
