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

package li.l1t.common.string;

import com.google.common.base.Preconditions;
import li.l1t.common.command.MissingArgumentException;
import li.l1t.common.i18n.Message;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Represents a list of arguments to something and provides helpful methods to access individual arguments.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2017-03-10
 */
public class Args {
    private final String[] args;

    public Args(String... args) {
        this.args = Preconditions.checkNotNull(args, "args");
    }

    /**
     * Creates a new object from a separated string of arguments
     *
     * @param argsSeparated  the source string
     * @param separatorRegex the separator that separates the individual arguments in the string
     * @see String#split(String) for details on how the splitting is done
     */
    public Args(String argsSeparated, String separatorRegex) {
        this(argsSeparated.split(separatorRegex));
    }

    public String[] args() {
        return args;
    }

    public List<String> argsList() {
        return Arrays.asList(args);
    }

    public Stream<String> argsStream() {
        return Arrays.stream(args);
    }

    public String arg(int index) {
        if (!hasArg(index)) {
            throw MissingArgumentException.forIndex(index);
        }
        return args[index];
    }

    public int intArg(int index) {
        try {
            return Integer.parseInt(arg(index));
        } catch (NumberFormatException e) {
            throw new ArgumentFormatException(arg(index), index, Message.of("x!api!number"), e);
        }
    }

    public double doubleArg(int index) {
        try {
            return Double.parseDouble(arg(index));
        } catch (NumberFormatException e) {
            throw new ArgumentFormatException(arg(index), index, Message.of("x!api!decimal"), e);
        }
    }

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
    public <E extends Enum<E>> E enumArg(Class<E> enumType, int index) {
        try {
            String name = arg(index);
            name = name.toUpperCase().replace("-", "_").replace(" ", "_");
            return Enum.valueOf(enumType, name);
        } catch (IllegalArgumentException e) {
            E[] enumConstants = enumType.getEnumConstants();
            String enumConstantsString = Arrays.toString(enumConstants);
            throw new ArgumentFormatException(arg(index), index, Message.of("x!api!enum-of", enumConstantsString), e);
        }
    }

    public UUID uuidArg(int index) {
        try {
            return UUID.fromString(arg(index));
        } catch (IllegalArgumentException e) {
            throw new ArgumentFormatException(arg(index), index, Message.of("x!api!uuid", UUID.randomUUID()), e);
        }
    }

    public Optional<String> findArg(int index) {
        return hasArg(index) ? Optional.of(arg(index)) : Optional.empty();
    }

    public boolean hasArg(int index) {
        return args.length > index;
    }

    public boolean hasNoArgs() {
        return args.length == 0;
    }

    /**
     * Provides a copy of given range in these arguments.
     *
     * @param startIndexInclusive the first argument to be included
     * @param endIndexExclusive   the first argument to be excluded
     * @return an Args object for given range
     * @see Arrays#copyOfRange(Object[], int, int)
     */
    public Args argRange(int startIndexInclusive, int endIndexExclusive) {
        return new Args(Arrays.copyOfRange(args, startIndexInclusive, endIndexExclusive));
    }

    /**
     * Provides a copy of these arguments, starting at a given argument
     *
     * @param startIndexInclusive the first argument to be included
     * @return an Args object for given range
     * @see Arrays#copyOfRange(Object[], int, int)
     */
    public Args argRangeFrom(int startIndexInclusive) {
        return new Args(Arrays.copyOfRange(args, startIndexInclusive, args.length));
    }

}
