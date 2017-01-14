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

package li.l1t.lanatus.api.builder;

import li.l1t.common.exception.DatabaseException;
import li.l1t.lanatus.api.exception.NoSuchProductException;
import li.l1t.lanatus.api.product.Product;
import li.l1t.lanatus.api.purchase.Purchase;

import java.util.UUID;

/**
 * Fluent builder interface for purchases, allowing to purchase products on behalf of a player.
 * Instances cannot be reused, and attempting to change purchase data after executing it will throw
 * {@link IllegalStateException}.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2016-10-17 (4.2.0)
 */
public interface PurchaseBuilder {
    /**
     * The special value that uses the product's default cost when passed to {@link
     * #withMelonsCost(int)} and is assumed if no custom melons cost is set.
     */
    int PRODUCT_DEFAULT_COST = Integer.MAX_VALUE;

    /**
     * @return the generated unique id for the purchase to be created from this builder
     */
    UUID getPurchaseId();

    /**
     * @return the executed purchase made from this builder
     * @throws IllegalStateException if the purchase has not yet been built
     */
    Purchase getPurchase();

    /**
     * @return whether the purchase has been built already
     */
    boolean hasBeenBuilt();

    /**
     * Executes the purchase defined by the current state of this builder and saves its result to
     * the database. Note that this method can only be executed once per builder instance. The
     * created purchase may be obtained using {@link #getPurchase()}.
     *
     * @throws IllegalStateException  if {@linkplain #hasBeenBuilt() the purchase has already been
     *                                built}
     * @throws NoSuchProductException if the product set on this builder does not exist or no
     *                                product has been set
     * @throws DatabaseException      if a database error occurs
     */
    void build() throws IllegalStateException, NoSuchProductException, DatabaseException;

    /**
     * @param product the product to purchase, may not be null
     * @return this builder
     * @throws IllegalStateException if the purchase has already been built
     */
    PurchaseBuilder withProduct(Product product);

    /**
     * @param productId the unique id of the product to purchase, may not be null
     * @return this builder
     * @throws IllegalStateException if the purchase has already been built
     */
    PurchaseBuilder withProductId(UUID productId);

    /**
     * Sets the melons cost of this builder, overriding the default cost of the product. Setting the
     * cost to {@link #PRODUCT_DEFAULT_COST} (the default) causes the product's default cost to be
     * used.
     *
     * @param melonsCost the melons cost of the purchase, may be negative or zero
     * @return this builder
     * @throws IllegalStateException if the purchase has already been built
     */
    PurchaseBuilder withMelonsCost(int melonsCost);

    /**
     * Attaches an arbitrary string to the purchase that describes specifics of the product to its
     * module. The default is an empty string.
     *
     * @param data the arbitrary product data string
     * @return this builder
     * @throws IllegalStateException if the purchase has already been built
     */
    PurchaseBuilder withData(String data);

    /**
     * Attaches an arbitrary string to the purchase for internal reference. The default is an empty
     * string.
     *
     * @param comment the purchase comment
     * @return this builder
     * @throws IllegalStateException if the purchase has already been built
     */
    PurchaseBuilder withComment(String comment);
}
