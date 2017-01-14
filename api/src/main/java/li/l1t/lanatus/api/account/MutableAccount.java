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

package li.l1t.lanatus.api.account;

import li.l1t.lanatus.api.exception.NotEnoughMelonsException;

/**
 * A mutable representation of an account. Mutable accounts should not be shared between scopes.
 * They must written back to the database as soon as possible to avoid conflicts. Implementations
 * should not be expected to be thread-safe.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2016-09-28 (4.2.0)
 */
public interface MutableAccount extends LanatusAccount {
    /**
     * @return an immutable snapshot of the account as it was before this mutable account was
     * fetched
     */
    AccountSnapshot getInitialState();

    /**
     * @return the amount of melons owned by this account in its mutated state
     */
    @Override
    int getMelonsCount();

    /**
     * Sets the amount of melons owned by this account in its mutated state.
     *
     * @param newMelonsCount the amount of melons to be owned by this account
     * @throws NotEnoughMelonsException if the new count is less than zero
     */
    void setMelonsCount(int newMelonsCount) throws NotEnoughMelonsException;

    /**
     * Adds an integer value to the amount of melons owned by this account in its mutated state.
     *
     * @param melonsModifier the amount of melons to add, may be negative
     * @throws NotEnoughMelonsException if the new count is less than zero
     */
    void modifyMelonsCount(int melonsModifier);

    /**
     * @return the latest known rank of this account in its mutated state
     */
    @Override
    String getLastRank();

    /**
     * @param lastRank the latest known rank of this account in its mutated state
     */
    void setLastRank(String lastRank);
}
