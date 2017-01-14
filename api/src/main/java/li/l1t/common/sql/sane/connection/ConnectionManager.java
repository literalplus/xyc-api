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

package li.l1t.common.sql.sane.connection;

import li.l1t.common.sql.sane.exception.SqlConnectionException;

import java.sql.Connection;

/**
 * Manages a single connection to a JDBC data source. Connections may be reused, so any connection
 * returned by a manager may not be closed by client directly. However, all connections associated
 * with this manager may be closed by calling {@link ConnectionManager#close()}. Connections are
 * scoped to the manager. If a reconnect is forced, all previously created connections are
 * invalidated.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2016-10-09
 */
public interface ConnectionManager extends AutoCloseable, ConnectionProvider {
    /**
     * @return the connection most recently created by this manager, or null if no connection has
     * been created yet
     */
    Connection getCurrentConnection();

    /**
     * Forces the manager to close its current connection and establish a new one.
     */
    void forceReconnect() throws SqlConnectionException;

    /**
     * <p><b>Note:</b> This method may send a statement to the data source in order to validate the
     * connection, depending on the driver.</p>
     *
     * @return whether there is currently a connection and that connection is considered valid by
     * the database driver
     */
    boolean hasActiveConnection();
}
