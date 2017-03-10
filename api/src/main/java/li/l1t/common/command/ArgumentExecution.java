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

import li.l1t.common.string.ArgumentFormatException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Stores information about the execution of a command that may be supplied a list of string arguments and provides
 * convenient accessor methods for arguments based on their indices in the list of arguments.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2017-01-14
 */
public interface ArgumentExecution {
    /**
     * @return the array of arguments passed to the command
     */
    String[] args();

    /**
     * @return the list of arguments passed to this command
     */
    List<String> argsList();

    /**
     * @return a stream containing all arguments passed to this execution
     */
    Stream<String> argsStream();

    /**
     * @param index the zero-based index of the {@link #args() argument} to access
     * @return whether this execution has an argument at given index
     */
    boolean hasArg(int index);

    /**
     * @return whether no arguments were passed to this execution
     */
    boolean hasNoArgs();

    /**
     * @param index the zero-based index of the {@link #args() argument} to access
     * @return the argument at given index
     * @throws MissingArgumentException if there is no argument at that index
     */
    String arg(int index);

    /**
     * @param index the zero-based index of the {@link #args() argument} to access
     * @return the integer argument at given index
     * @throws MissingArgumentException if there is no argument at that index
     * @throws ArgumentTypeException    if the argument at given index cannot be converted to an integer
     */
    int intArg(int index);

    /**
     * @param index the zero-based index of the {@link #args() argument} to access
     * @return the UUID argument at given index
     * @throws MissingArgumentException if there is no argument at that index
     * @throws ArgumentTypeException    if the argument at given index is not a UUID
     */
    UUID uuidArg(int index);

    /**
     * @param index the zero-based index of the {@link #args() argument} to find
     * @return an optional containing the argument at given index, or an empty optional if there is no argument at that
     * index
     */
    Optional<String> findArg(int index);

    /**
     * @param startIndex the index of the first argument to process
     * @return the space-separated string of arguments passed to this execution, starting with the argument at given
     * start index
     * @throws MissingArgumentException if there is no argument at given start index
     */
    String joinedArgs(int startIndex);

    /**
     * Attempts to match an enum argument on a best-effort basis. This converts the string to
     * uppercase and replaces spaces and dashes with underscores, expecting the enum to follow
     * standard Java naming conventions.
     *
     * @param enumType the enum class to search in
     * @param index    the index of the argument to use
     * @param <E>      the enum type
     * @return the enum type
     * @throws ArgumentFormatException if the enum does not contain such constant
     */
    <E extends Enum<E>> E enumArg(Class<E> enumType, int index);
}
