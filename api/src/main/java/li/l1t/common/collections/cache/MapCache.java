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

package li.l1t.common.collections.cache;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Caches mappings from one thing to another thing. Implementations may implement ways to
 * automatically expire mappings based on certain conditions documented in their class JavaDoc.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2016-10-19
 */
public interface MapCache<K, V> {
    /**
     * Invalidates all entries of this cache.
     */
    void clear();

    /**
     * Caches a mapping in this cache. Note that mappings expire after the write expiry time set in
     * the constructor.
     *
     * @param key   the key of the mapping to cache
     * @param value the value of the mapping
     * @param <R>   the implementation return type, for fluent returns
     * @return the value
     */
    <R extends V> R cache(K key, R value);

    /**
     * Computes a value by applying given supplier to given key.
     *
     * @param key      the key to compute the mapping for
     * @param supplier the supplier of the value
     * @param <R>      the implementation return type, for fluent returns
     * @return the computed value
     */
    <R extends V> R compute(K key, Function<? super K, R> supplier);

    /**
     * Gets the optional mapping for given key.
     *
     * @param key the key to retrieve the mapping for
     * @return an optional containing the value of the mapping if it exists, or an empty optional if
     * it doesn't
     */
    Optional<V> get(K key);

    /**
     * Gets the currently cached value for given key, if present. Otherwise, computes the mapping
     * for given key using given supplier function, caches and returns it.
     *
     * @param key      the key to get or compute the mapping for
     * @param supplier the value supplier
     * @return the found or computed value of the mapping for given key in the cache
     */
    V getOrCompute(K key, Function<K, ? extends V> supplier);

    /**
     * Invalidates the mapping for given key, if any.
     *
     * @param key the key to operate on
     */
    void invalidateKey(K key);

    /**
     * @param key the key to look up in the cache
     * @return whether given key is currently part of a valid mapping
     */
    boolean containsKey(K key);

    /**
     * @return a stream of all current entries of this cache
     */
    Stream<Map.Entry<K, V>> entryStream();

    /**
     * @return a stream of all current keys of this cache
     */
    Stream<K> keyStream();

    /**
     * @return a stream of all current values of this cache
     */
    Stream<V> valueStream();
}
