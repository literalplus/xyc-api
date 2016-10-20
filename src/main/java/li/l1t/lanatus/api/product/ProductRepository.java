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

package li.l1t.lanatus.api.product;

import li.l1t.common.exception.DatabaseException;
import li.l1t.lanatus.api.LanatusRepository;
import li.l1t.lanatus.api.exception.NoSuchProductException;

import java.util.UUID;

/**
 * A repository for products that may be owned through Lanatus. Note that results may be heavily
 * cached in order to reduce database calls at the cost that product information may not always be
 * up to date.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2016-09-28 (4.2.0)
 */
public interface ProductRepository extends LanatusRepository {
    /**
     * Finds a product by its unique id, first querying any caches that might exist and then falling
     * back to the database if the product in question is not cached. Note that subsequent calls for
     * the same non-existent product id trigger subsequent database queries.
     *
     * @param productId the unique id of the product
     * @return the product with given unique id
     * @throws NoSuchProductException if there is no product with given id
     * @throws DatabaseException      if a database error occurs
     */
    Product findById(UUID productId) throws NoSuchProductException, DatabaseException;

    /**
     * Creates a new query builder. Note that queries ignore the product cache.
     *
     * @return a builder for product queries in this repository
     */
    ProductQueryBuilder query();

    /**
     * Creates a new product registration builder. This is intended for modules to register their
     * own products when they connect to Lanatus, in order to make sure that they actually exist. If
     * a product by that unique id already exists (or is cached), the registration will be silently
     * ignored.
     *
     * @param productId the unique id of the product, must be persistent over sessions - best saved
     *                  as a constant
     * @return a builder for a product registration
     */
    ProductRegistrationBuilder registration(UUID productId);
}
