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

package li.l1t.lanatus.api.position;


import li.l1t.lanatus.api.LanatusRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * A repository for positions. Note that results may be cached.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2016-09-28 (4.2.0)
 */
public interface PositionRepository extends LanatusRepository {
    /**
     * Finds the position belonging to a purchase, if any.
     *
     * @param purchaseId the purchase to search for
     * @return an Optional containing the associated purchase if it exists, otherwise an empty
     * Optional
     */
    Optional<Position> findByPurchase(UUID purchaseId);

    /**
     * Finds all positions currently associated with given player.
     *
     * @param playerId the unique id of the player
     * @return the collection of positions, or an empty collection if none
     */
    Collection<Position> findAllByPlayer(UUID playerId);

    /**
     * @param playerId  the unique id of the player
     * @param productId the unique id of the product
     * @return whether given player has a position matching given product
     */
    boolean playerHasProduct(UUID playerId, UUID productId);
}
