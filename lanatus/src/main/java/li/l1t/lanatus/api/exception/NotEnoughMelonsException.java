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

import com.google.common.base.Preconditions;
import li.l1t.common.exception.UserException;
import li.l1t.lanatus.api.account.LanatusAccount;

/**
 * Thrown if there are not enough melons in an account for a transaction, e.g. a purchase.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2016-10-07 (4.2.0)
 */
public class NotEnoughMelonsException extends UserException {
    private final LanatusAccount account;
    private final int melonsMissing;

    public NotEnoughMelonsException(LanatusAccount account, int melonsMissing) {
        super(String.format(
                "Das kannst du dir nicht leisten! (Du hast %d Melonen, dir fehlen %d)",
                Preconditions.checkNotNull(account, "account").getMelonsCount(),
                melonsMissing
        ));
        this.account = account;
        this.melonsMissing = melonsMissing;
    }

    /**
     * @return the amount of melons additionally required in the account to complete the transaction
     */
    public int getMelonsMissing() {
        return melonsMissing;
    }

    /**
     * @return the account involved in the transaction
     */
    public LanatusAccount getAccount() {
        return account;
    }
}
