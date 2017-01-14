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

import li.l1t.common.exception.InternalException;
import li.l1t.common.exception.UserException;

/**
 * Something that executes commands specified by {@link BukkitExecution} instances.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2016-10-26
 */
public interface ExecutionExecutor {
    /**
     * Executes the command specified by given execution.
     *
     * @param exec the execution to process
     * @return whether the execution was "successful". An error message may be shown by the caller if the execution was
     * unsuccessful.
     * @throws UserException     if incorrect user input led to an error
     * @throws InternalException if internal system conditions led to an error
     */
    boolean execute(BukkitExecution exec) throws UserException, InternalException;
}
