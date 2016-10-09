/*
 * Copyright (c) 2013 - 2015 xxyy (Philipp Nowak; devnull@nowak-at.net). All rights reserved.
 *
 * Any usage, including, but not limited to, compiling, running, redistributing, printing,
 *  copying and reverse-engineering is strictly prohibited without explicit written permission
 *  from the original author and may result in legal steps being taken.
 *
 * See the included LICENSE file (core/src/main/resources) or email xxyy98+xyclicense@gmail.com for details.
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
    void tryClose();


    /**
     * @return the statement that was used to create this result
     */
    PreparedStatement getStatement();
}
