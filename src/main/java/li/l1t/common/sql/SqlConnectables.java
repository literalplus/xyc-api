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

package li.l1t.common.sql;

import com.google.common.base.Preconditions;

import javax.annotation.Nonnull;

/**
 * Provides some static utility methods for {@link SqlConnectable}s.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2014-05-14
 */
public final class SqlConnectables {

    private SqlConnectables() {
    }

    /**
     * Builds a host string from a connectable. <p>A host string consists of the {@link
     * SqlConnectable#getSqlHost() database URL}, a slash (/), and the {@link
     * SqlConnectable#getSqlDb() database name}.</p> If the database URL ends with the database
     * name, it is not appended another time.
     *
     * @param connectable the connectable to get information from
     * @return a host string derived from given connectable
     */
    public static String getHostString(@Nonnull SqlConnectable connectable) {
        String jdbcUrl = Preconditions.checkNotNull(connectable.getSqlHost(), "connectable.sqlHost");
        return getHostString(connectable.getSqlDb(), jdbcUrl);
    }

    /**
     * Builds a host string from database name and database URL. <p>A host string consists of the
     * {@link SqlConnectable#getSqlHost() database URL}, a slash (/), and the {@link
     * SqlConnectable#getSqlDb() database name}.</p> If the database URL ends with the database
     * name, it is not appended another time.
     *
     * @param database the database the host string should point to
     * @param jdbcUrl  the fully qualified JDBC URL the host string should point to
     * @return a host string derived from given parameters
     */
    public static String getHostString(String database, String jdbcUrl) {
        String hostString = appendDatabaseIfNotAlreadyPresent(database, jdbcUrl);

        if (!hostString.startsWith("jdbc:")) {
            if (!hostString.startsWith("mysql://")) {
                hostString = "jdbc:mysql://" + hostString;
            } else {
                hostString = "jdbc:" + hostString;
            }
        }

        if (!hostString.contains("?")) {
            hostString += "?autoReconnect=true";
        }

        return hostString;
    }

    private static String appendDatabaseIfNotAlreadyPresent(String database, String jdbcUrl) {
        if (jdbcUrl.contains(database)) { //some databases allow parameters with ?, so can't use endsWith
            return jdbcUrl;
        } else if (jdbcUrl.endsWith("/")) {
            return jdbcUrl + database;
        } else {
            return jdbcUrl + "/" + database;
        }
    }

    /**
     * @param host     {@link SqlConnectable#getSqlHost() the fully-qualified JDBC URL}
     * @param database {@link SqlConnectable#getSqlDb() the database}
     * @param user     {@link SqlConnectable#getSqlUser() the user name}
     * @param password {@link SqlConnectable#getSqlPwd() the password}
     * @return a connectable instance with given credentials
     */
    @Nonnull
    public static SqlConnectable fromCredentials(@Nonnull final String host, @Nonnull final String database,
                                                 @Nonnull final String user, @Nonnull final String password) {
        return new SqlConnectable() {
            @Override
            public String getSqlDb() {
                return database;
            }

            @Override
            public String getSqlHost() {
                return host;
            }

            @Override
            public String getSqlPwd() {
                return password;
            }

            @Override
            public String getSqlUser() {
                return user;
            }
        };
    }
}
