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
import li.l1t.lanatus.api.LanatusConnected;

import java.util.Collection;

/**
 * A builder for queries resulting in a collection of products.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2016-10-10 (4.2.0)
 */
public interface ProductQueryBuilder extends LanatusConnected {
    /**
     * @param moduleName the name of the module to select
     * @return this builder
     */
    ProductQueryBuilder inModule(String moduleName);

    /**
     * Selects the current module.
     *
     * @return this builder
     */
    ProductQueryBuilder inThisModule();

    /**
     * Selects no module, making the query return products in all modules.
     *
     * @return this builder
     */
    ProductQueryBuilder inAnyModule();

    /**
     * @param nameFilter the name to select
     * @return this builder
     * @deprecated since products no longer have names, this method is kind of obsolete, and may be
     * ignored by implementations
     */
    @Deprecated
    ProductQueryBuilder withName(String nameFilter);

    /**
     * Limits the result of the query to products containing given search term in display name,
     * description, or module name. Note that an empty string as a search term does not limit the
     * query, effectively doing nothing.
     *
     * @param searchTerm the term products must contain in order to be included in the result, or an
     *                   empty string to disable contaions search (the default)
     * @return this builder
     */
    ProductQueryBuilder containing(String searchTerm);

    /**
     * Selects only active products, meaning that the query will only return active products.
     *
     * @return this builder
     */
    ProductQueryBuilder andActive();

    /**
     * Executes the query resulting from the current state of this builder.
     *
     * @return the result of the query, or an empty collection if there is no such product
     * @throws DatabaseException if a database error occurs
     */
    Collection<Product> execute() throws DatabaseException;
}
