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

import java.sql.ResultSet;

/**
 * Represents the result of a query to a SQL data source. <p><b>Note:</b> This is supposed to be
 * used with try-with-resources. If used in another fashion, care must be taken to close the
 * object!</p>
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2016-10-09
 */
public interface QueryResult extends CloseableResult {
    /**
     * @return the ResultSet associated with this query result
     */
    ResultSet rs();
}
