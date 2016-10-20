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

package li.l1t.common.sql.sane.scoped;

import li.l1t.common.exception.DatabaseException;
import li.l1t.common.exception.InternalException;

/**
 * An auto-closeable session manager that keeps the count of scopes currently using it. Intended for
 * use with try...finally statements. <p><b>Important:</b> Before accessing any state of this
 * object, join() must be called. After the current unit of work (ideally a try block) is done,
 * close() must be called. If this is not adhered, the reference count will become invalid and the
 * session might not get closed properly. Note that close() is automatically called by the JVM on
 * resources managed by try-with-resources.</p> <p>Different implementations may handle different
 * transaction APIs, which is why this interface provides a type parameter for the transaction
 * object type. For implementations which do not expose a special session object, their own type is
 * to be used as type parameter.</p>
 *
 * @param <T> the type of the transaction object returned by this session
 * @author <a href="https://l1t.li/">Literallie</a>
 * @see RawScopedSession an interface for implementations which do not expose a special session
 * object
 * @since 2016-10-18
 */
public interface ScopedSession<T> extends AutoCloseable {
    /**
     * Joins this scoped session and returns itself. Note that a joined session needs to be released
     * using {@link #close()} to make sure it will get properly closed. Further note that {@link
     * #tx()} joins the session too, so calling join again will leave the session thinking that it
     * is one layer deeper than it actually is, and will not get closed.
     *
     * @return the scoped session that was just joined
     */
    ScopedSession<T> join();

    /**
     * Joins this session, starts a new transaction and ensures that the database driver does not
     * auto-commit.
     *
     * @return this scoped session
     */
    T tx();

    /**
     * Commits the managed transaction and resets the database driver's auto-commit state if
     * changed.
     *
     * @throws InternalException if this session has no transaction associated or there are no more
     *                           tracked references to this session
     */
    void commit();

    /**
     * Rolls back the managed transaction and resets the database driver's auto-commit state if
     * changed.
     *
     * @throws InternalException if this session has no transaction associated or there are no more
     *                           tracked references to this session
     */
    void rollbackAndClose();

    /**
     * {@linkplain #commit() Commits} the underlying transaction if this layer is the last layer
     * that has joined this scoped session.
     *
     * @throws InternalException if this session has no transaction associated or there are no more
     *                           tracked references to this session
     */
    void commitIfLast();

    /**
     * {@linkplain #commit() Commits} the underlying transaction if this layer is the last layer
     * that has joined this scoped session and a transaction was crated in a lower layer.
     *
     * @throws InternalException if this session has no transaction associated or there are no more
     *                           tracked references to this session
     */
    void commitIfLastAndChanged();

    /**
     * {@inheritDoc}
     *
     * @throws DatabaseException if an error occurs communicating with the database driver while
     *                           trying to clean up the session
     * @throws InternalException if this session has an associated transaction and that transaction
     *                           was not properly ended, that is, neither committed nor rolled back
     *                           via the scoped session API. If that happens, the transaction is
     *                           rolled back forcefully.
     */
    @Override
    void close() throws InternalException;

    /**
     * @return whether this scoped session is currently being referenced by a scope
     */
    boolean hasReferences();

    /**
     * @return whether this session is able to accept further references by a scope
     */
    boolean acceptsFurtherReferences();

    /**
     * @return whether a transaction has been started in this session
     */
    boolean hasTransaction();
}
