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

package li.l1t.common.sql;

/**
 * Provides credentials for connecting to a JDBC database. <p><b>Note:</b> Try to keep the scope of
 * these as small as possible. Untrusted code could easily steal credentials!</p>
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 */
public interface SqlConnectable {

    /**
     * @return the database to connect to
     */
    String getSqlDb();

    /**
     * @return the fully qualified JDBC connection string for the host to connect to
     */
    String getSqlHost();

    /**
     * @return the password for authentication with the database
     */
    String getSqlPwd();

    /**
     * @return the user for authentication with the database
     */
    String getSqlUser();
}
