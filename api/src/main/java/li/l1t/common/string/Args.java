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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Represents a list of arguments to something and provides helpful methods to access individual arguments.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2017-03-10
 */
public class Args {
    private final String[] args;
    // TODO: This does not belong in this class v
    private final Pattern HEXADECIMAL_PATTERN = Pattern.compile("^0x", Pattern.CASE_INSENSITIVE);

    /**
     * @param args the string arguments to wrap
     * @deprecated Clashes with {@link #Args(String, String)} - Use {@link #of(String...)} instead.
     */
    @Deprecated
    public Args(String... args) {
        this.args = Preconditions.checkNotNull(args, "args");
    }

    /**
     * Creates a new object from a separated string of arguments
     *
     * @param argsSeparated  the source string
     * @param separatorRegex the separator that separates the individual arguments in the string
     * @see String#split(String) for details on how the splitting is done
     * @deprecated Clashes with {@link #Args(String...)} with two arguments - will be removed; Use {@link
     * #fromSeparatedArgs(String, String)} instead.
     */
    @Deprecated
    public Args(String argsSeparated, String separatorRegex) {
        this(argsSeparated.split(separatorRegex));
    }

    /**
     * Creates a new Args object wrapping given arguments. This method always considers given arguments as an array,
     * unlike {@link #Args(String...)}, which clashes with {@link #Args(String, String)} for two arguments.
     *
     * @param args the string arguments to wrap
     * @return the created Args object
     */
    @SuppressWarnings("deprecation")
    public static Args of(String... args) {
        return new Args(args);
    }

    /**
     * Creates a new Args object from a separated string of arguments. Creates an empty Args object if the source string
     * is empty when {@link String#trim() trimmed}.
     *
     * @param argsSeparated  the source string
     * @param separatorRegex the separator that separates the individual arguments in the string
     * @return the created Args object
     * @see String#split(String) for details on how the splitting is done
     */
    @SuppressWarnings("deprecation")
    public static Args fromSeparatedArgs(String argsSeparated, String separatorRegex) {
        Preconditions.checkNotNull(argsSeparated, "argsSeparated");
        Preconditions.checkNotNull(separatorRegex, "separatorRegex");
        if (argsSeparated.trim().isEmpty()) {
            return new Args();
        } else {
            return new Args(argsSeparated.split(separatorRegex));
        }
    }

    /**
     * Creates a new Args object from a space-separated string of arguments. Creates an empty Args object if the source
     * string, or the source string with the command name and slash removed is empty when {@link String#trim()
     * trimmed}.
     *
     * @param commandLine the source string, with a slash (/) directly before the command name
     * @return the created Args object
     */
    public static Args fromCommandLine(String commandLine) {
        Preconditions.checkNotNull(commandLine, "commandLine");
        commandLine = commandLine.replaceFirst("/\\w+ ?", "");
        return fromSeparatedArgs(commandLine, " ");
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

    /**
     * @param index the zero-based index of the argument to retrieve
     * @return the string argument at that index
     * @throws MissingArgumentException if the index is out of range for this args object
     */
    public String arg(int index) {
        if (!hasArg(index)) {
            throw MissingArgumentException.forIndex(index);
        }
        return args[index];
    }

    /**
     * @param index the zero-based index of the argument to retrieve
     * @return the integer argument at that index
     * @throws MissingArgumentException if the index is out of range for this args object
     * @throws ArgumentFormatException  if the argument at that index could not be converted to an integer using {@link
     *                                  Integer#parseInt(String)}
     */
    public int intArg(int index) {
        String argStr = arg(index);
        try {
            Matcher hexMatcher = HEXADECIMAL_PATTERN.matcher(argStr);
            if (hexMatcher.find()) {
                return Integer.parseInt(hexMatcher.replaceFirst(""), 16);
            } else {
                return Integer.parseInt(argStr, 10);
            }
        } catch (NumberFormatException e) {
            throw new ArgumentFormatException(argStr, index, Message.of("x!api!number"), e);
        }
    }

    /**
     * @param index the zero-based index of the argument to retrieve
     * @return the double argument at that index
     * @throws MissingArgumentException if the index is out of range for this args object
     * @throws ArgumentFormatException  if the argument at that index could not be converted to a double using {@link
     *                                  Double#parseDouble(String)}
     */
    public double doubleArg(int index) {
        try {
            String rawArg = arg(index);
            String germanSafeArg = rawArg.replace(",", "."); // Germans use , as decimal separator
            return Double.parseDouble(germanSafeArg);
        } catch (NumberFormatException e) {
            throw new ArgumentFormatException(arg(index), index, Message.of("x!api!decimal"), e);
        }
    }

    /**
     * Attempts to match an enum argument on a best-effort basis. This converts the string to uppercase and replaces
     * spaces and dashes with underscores, expecting the enum to follow standard Java naming conventions.
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

    /**
     * @param index the zero-based index of the argument to retrieve
     * @return the UUID-like argument at that index
     * @throws MissingArgumentException if the index is out of range for this args object
     * @throws ArgumentFormatException  if the argument at that index could not be converted to an UUID using {@link
     *                                  UUID#fromString(String)}
     */
    public UUID uuidArg(int index) {
        try {
            return UUID.fromString(arg(index));
        } catch (IllegalArgumentException e) {
            throw new ArgumentFormatException(arg(index), index, Message.of("x!api!uuid", UUID.randomUUID()), e);
        }
    }

    /**
     * @param index the zero-based index of the argument to find
     * @return an optional containing the string argument at given index, or an empty optional if given index is out of
     * range for this args object
     */
    public Optional<String> findArg(int index) {
        return hasArg(index) ? Optional.of(arg(index)) : Optional.empty();
    }

    /**
     * @param index the zero-based index of the argument to inspect
     * @return whether this args object has an argument at given index
     */
    public boolean hasArg(int index) {
        return index >= 0 && args.length > index;
    }

    /**
     * @return whether this args object does not have any arguments
     */
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

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Args)) {
            return false;
        }
        Args otherArgs = (Args) other;
        return Arrays.equals(args, otherArgs.args);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(args);
    }

    @Override
    public String toString() {
        return "Args" + Arrays.toString(args);
    }
}
