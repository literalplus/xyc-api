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

package li.l1t.common.sql.sane.statement;

import java.sql.PreparedStatement;

/**
 * Provides prepared statements filled with parameters.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2016-10-09
 */
public interface StatementProvider {
    /**
     * Creates a new statement filled with some parameters. {@code ?} placeholders in the query
     * string are substituted by the corresponding parameter (in order) by the database driver
     * natively, providing strong defense against SQL Injection attacks. Concatenation of data to
     * the query string should be avoided.
     *
     * @param sql        the query string for the database
     * @param parameters the parameters for the query string
     * @return the created statement
     */
    PreparedStatement create(String sql, Object... parameters);
}
