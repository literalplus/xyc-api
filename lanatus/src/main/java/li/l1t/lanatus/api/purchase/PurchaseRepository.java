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

package li.l1t.lanatus.api.purchase;

import li.l1t.common.exception.DatabaseException;
import li.l1t.lanatus.api.LanatusRepository;
import li.l1t.lanatus.api.exception.NoSuchPurchaseException;

import java.util.Collection;
import java.util.UUID;

/**
 * A repository for purchases made through Lanatus.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2016-09-28 (4.2.0)
 */
public interface PurchaseRepository extends LanatusRepository {
    /**
     * Finds a purchase by its unique id.
     *
     * @param purchaseId the unique id of the purchase
     * @return the purchase with given unique id
     * @throws NoSuchPurchaseException if there is no such purchase
     * @throws DatabaseException  if a database error occurs
     */
    Purchase findById(UUID purchaseId) throws NoSuchPurchaseException, DatabaseException;

    /**
     * Finds the collection of purchases a player has made.
     *
     * @param playerId the unique id of the player
     * @return the collection of purchases, or an empty collection for none
     * @throws DatabaseException if a database error occurs
     */
    Collection<Purchase> findByPlayer(UUID playerId) throws DatabaseException;
}
