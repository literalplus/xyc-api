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

package li.l1t.lanatus.api.account;

import java.util.UUID;

/**
 * Represents an account in Lanatus. Implementations must specify mutability behaviour.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2016-09-29 (4.2.0)
 */
public interface LanatusAccount {
    /**
     * The name of the default rank that is assigned to accounts at creation time.
     */
    String DEFAULT_RANK = "default";

    /**
     * The amount of melons accounts have at creation time.
     */
    int INITIAL_MELONS_COUNT = 0;

    /**
     * @return the unique id of the player the account belongs to
     */
    UUID getPlayerId();

    /**
     * @return the amount of melons in this account
     */
    int getMelonsCount();

    /**
     * @return the most recently known rank of this account
     */
    String getLastRank();
}
