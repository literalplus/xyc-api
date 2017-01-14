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

package li.l1t.common.command;

import com.google.common.base.Preconditions;
import li.l1t.common.exception.UserException;
import org.bukkit.permissions.Permissible;

/**
 * Thrown if the executor of a command does not have permission for something.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2016-10-25
 */
public class UserPermissionException extends UserException {
    public UserPermissionException(String messagePattern, Object... params) {
        super(messagePattern, params);
    }

    public static void checkPermission(Permissible target, String permission, String messagePattern, Object... params) {
        Preconditions.checkNotNull(target, "target");
        Preconditions.checkNotNull(permission, "permission");
        if (!target.hasPermission(permission)) {
            throw new UserPermissionException(messagePattern, params);
        }
    }

    public static void checkPermission(Permissible target, String permission) {
        checkPermission(target, permission, "Du bist nicht autorisiert, dies zu tun. (%s)", permission);
    }
}
