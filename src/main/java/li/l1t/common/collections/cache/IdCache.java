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

package li.l1t.common.collections.cache;

import java.util.Optional;
import java.util.function.Function;

/**
 * Caches things that are identifiable and can be uniquely mapped back to an identifier. Cache
 * entries are expired automatically some time after they have been stored.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2016-10-20
 */
public interface IdCache<K, V> {
    /**
     * Invalidates all mappings in this cache.
     */
    void clear();

    /**
     * Caches a value in this cache, obtaining its identifier from the id function. Note that
     * mappings expire after the write expiry time set in the constructor.
     *
     * @param value the value of the mapping to cache
     * @param <R>   the implementation type of the value, for fluent returns
     * @return the value
     */
    <R extends V> R cache(R value);

    /**
     * Computes a value by applying given supplier to given key, using the id function to find its
     * identifier.
     *
     * @param id       the identifier parameter for the supplier function
     * @param supplier the supplier function supplying the value for the identifier
     * @param <R>      the implementation type of the supplied value, for fluent returns
     * @return the computed value
     */
    <R extends V> R compute(K id, Function<? super K, R> supplier);

    /**
     * Gets the optional mapping for given key.
     *
     * @param id the key to retrieve the mapping for
     * @return an optional containing the value of the mapping if it exists, or an empty optional if
     * it doesn't
     */
    Optional<V> get(K id);

    /**
     * Gets the currently cached value for given key, if present. Otherwise, computes the mapping
     * for given key using given supplier function, caches and returns it. Note that supplied values
     * are always mapped to the identifier returned by the id function, not to the id parameter.
     *
     * @param id       the identifier to get or compute the mapping for
     * @param supplier the value supplier
     * @return the found or computed value of the mapping for given key in the cache
     */
    V getOrCompute(K id, Function<? super K, ? extends V> supplier);

    /**
     * Invalidates the mapping for given value, if present, using the id function to obtain the
     * key.
     *
     * @param value the value whose mapping to invalidate
     */
    void invalidateValue(V value);

    /**
     * Invalidates the mapping for given key, if any.
     *
     * @param key the key to operate on
     */
    void invalidateKey(K key);

    /**
     * @param id the identifier to look up in the cache
     * @return whether given identifier is currently part of a valid mapping
     */
    boolean containsKey(K id);

    /**
     * @param value the value to pass to the id function
     * @return whether a mapping is currently cached for the identifier that the id function
     * returned for given key that is {@link Object#equals(Object) equal} to given value
     */
    boolean containsValue(V value);
}
