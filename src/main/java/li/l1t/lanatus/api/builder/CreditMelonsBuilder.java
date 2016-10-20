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

package li.l1t.lanatus.api.builder;

import li.l1t.common.exception.DatabaseException;

import java.util.UUID;

/**
 * Fluent builder interface for crediting melons to a player's account.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2016-10-18 (4.2.0)
 */
public interface CreditMelonsBuilder {
    /**
     * The unique id of the product that represents credited melons.
     */
    UUID PRODUCT_ID = UUID.fromString("e94dd44d-93ea-4f21-823e-9aa15e278803");

    /**
     * @return whether the melons were credited already
     */
    boolean hasBeenExecuted();

    /**
     * Actually credits the amount of melons defined in this builder to the player. Note that this
     * method can only be executed once per builder instance.
     *
     * @throws IllegalStateException {@linkplain #hasBeenExecuted() if the melons have already been
     *                               credited}
     * @throws DatabaseException     if a database error occurs
     */
    void build() throws IllegalStateException, DatabaseException;

    /**
     * Sets the melons count to be credited to the player.
     *
     * @param melonsCount the amount of melons to credit
     * @return this builder
     * @throws IllegalArgumentException if melonsCount is negative
     */
    CreditMelonsBuilder withMelonsCount(int melonsCount);

    /**
     * Attaches an arbitrary string comment to the purchase associated with the action.
     *
     * @param comment the comment string
     * @return this builder
     */
    CreditMelonsBuilder withComment(String comment);
}
