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

import li.l1t.lanatus.api.account.AccountRepository;
import li.l1t.lanatus.api.builder.CreditMelonsBuilder;
import li.l1t.lanatus.api.builder.PurchaseBuilder;
import li.l1t.lanatus.api.position.PositionRepository;
import li.l1t.lanatus.api.product.ProductRepository;
import li.l1t.lanatus.api.purchase.PurchaseRepository;

import java.util.UUID;

/**
 * Main entry point to an implementation of the Lanatus API, providing access to all other parts of
 * the API.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2016-09-28 (4.2.0)
 */
public interface LanatusClient {
    /**
     * @return the unique name of the module this client is for, must not be longer than 20
     * characters
     */
    String getModuleName();

    /**
     * @return the account repository associated with this client
     */
    AccountRepository accounts();

    /**
     * @return the position repository associated with this client
     */
    PositionRepository positions();

    /**
     * @return the purchase repository associated with this client
     */
    PurchaseRepository purchases();

    /**
     * @return the product repository associated with this client
     */
    ProductRepository products();

    /**
     * Starts a new purchase builder for given player.
     *
     * @param playerId the unique id of the player on whose behalf the purchase is to be made
     * @return a new purchase builder
     */
    PurchaseBuilder startPurchase(UUID playerId);

    /**
     * Creates a new builder that credits melons to given player.
     *
     * @param playerId the unique id of the player whose account should be credited
     * @return a new purchase builder
     */
    CreditMelonsBuilder creditMelons(UUID playerId);
}
