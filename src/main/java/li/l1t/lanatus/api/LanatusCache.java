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

package li.l1t.lanatus.api;

import java.util.UUID;

/**
 * Something that keeps a local cache of database status related to Lanatus and allows to clear that
 * cache. Implementations must implement mechanisms on their own to assure that stale data cannot
 * taint the cache for longer times, for example by invalidating cache entries after some minutes.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2016-10-28 (4.2.2)
 */
public interface LanatusCache {
    /**
     * Forcefully clears this cache and all the sub-caches it may manage. Note that caches must
     * invalidate their caches on their own discretion, so usage of this method should not be
     * necessary in normal operation. Generally, it should only be called if there is reason to
     * believe that stale data is present in the caches and that cannot be easily resolved using
     * other methods.
     */
    void clearCache();

    /**
     * Forcefully invalidates the data in this cache and sub-caches directly associated with given
     * player. Note that this may miss some data if the mapping from player to data is not present
     * in the cache. Implementations are not obliged to scan their whole cache in such a case.
     *
     * @param playerId the unique id of the player to operate on
     */
    void clearCachesFor(UUID playerId);
}
