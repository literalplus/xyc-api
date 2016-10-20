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

package li.l1t.lanatus.api.purchase;

import li.l1t.common.misc.Identifiable;
import li.l1t.lanatus.api.position.Position;
import li.l1t.lanatus.api.product.Product;

import java.time.Instant;
import java.util.UUID;

/**
 * Represents a purchase made through Lanatus. Note that purchases need not correspond to an actual
 * {@link Position}. This may be the case for one-time bonuses as well as for purchases which have
 * been undone.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2016-09-28 (4.2.0)
 */
public interface Purchase extends Identifiable {
    /**
     * @return the unique id of the player whose account the purchase applies to
     */
    UUID getPlayerId();

    /**
     * @return the product associated with this purchase
     */
    Product getProduct();

    /**
     * @return the instant this purchase was made at
     */
    Instant getCreationInstant();

    /**
     * @return the arbitrary string describing specifics of the product to its module
     */
    String getData();

    /**
     * @return the arbitrary string attached to this purchase for reference
     */
    String getComment();

    /**
     * @return the amount of melons that was actually spent on this purchase, may be negative if
     * melons were given to the associated player
     */
    int getMelonsCost();
}
