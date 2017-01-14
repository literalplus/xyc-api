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

package li.l1t.lanatus.api.exception;

import li.l1t.lanatus.api.account.AccountSnapshot;
import li.l1t.lanatus.api.account.MutableAccount;

/**
 * Thrown if a conflict arises while attempting to merge a local copy of an account back into the
 * database.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2016-09-28 (4.2.0)
 */
public class AccountConflictException extends Exception {
    private final AccountSnapshot databaseState;
    private final MutableAccount localState;

    public AccountConflictException(AccountSnapshot databaseState, MutableAccount localState) {
        this.databaseState = databaseState;
        this.localState = localState;
    }

    /**
     * @return the conflicting state of the database
     */
    public AccountSnapshot getDatabaseState() {
        return databaseState;
    }

    /**
     * @return the local account state that could not be merged
     */
    public MutableAccount getLocalState() {
        return localState;
    }
}
