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

package li.l1t.lanatus.api.account;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * An immutable snapshot of the state of a Lanatus account.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2016-09-28 (4.2.0)
 */
public interface AccountSnapshot extends LanatusAccount {
    /**
     * @return the instant at which this snapshot was taken
     */
    Instant getSnapshotInstant();

    /**
     * Gets whether the account represented by this snapshot existed in the database at the time
     * this snapshot was fetched. Note that it depends on the implementation of the account
     * repository that fetched this snapshot how it handles non-existing snapshots.
     *
     * @return whether the account existed at snapshot instant
     * @deprecated Use {@link AccountRepository#find(UUID)} and {@link Optional#isPresent()}.
     */
    @Deprecated
    boolean existed();
}
