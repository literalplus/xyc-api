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

import li.l1t.common.command.MissingArgumentException;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ArgsTest {
    @Test
    void args__none() {
        //given
        String[] argsArr = {};
        //when
        Args args = givenArgsOf(argsArr);
        //then
        assertThat(args.args(), is(argsArr));
    }

    private Args givenArgsOf(String[] argsArr) {
        return argsOf(argsArr);
    }

    private Args argsOf(String... args) {
        return new Args(args);
    }

    @Test
    void args__direct() {
        //given
        String[] argsArr = {"some", "args"};
        //when
        Args args = givenArgsOf(argsArr);
        //then
        assertThat(args.args(), is(argsArr));
    }

    @Test
    void args__split_weird() {
        //given
        String[] argsArr = {"some", "args"};
        String argsStr = "some;;args";
        //when
        Args args = Args.fromSeparatedArgs(argsStr, ";;");
        //then
        assertThat(args.args(), is(argsArr));
    }

    @Test
    void args__commandLine_args() {
        //given
        String[] argsArr = {"some", "args"};
        String argsStr = "/command some args";
        //when
        Args args = Args.fromCommandLine(argsStr);
        //then
        assertThat(args.args(), is(argsArr));
    }

    @Test
    void args__commandLine_no_args() {
        //given
        String[] argsArr = {};
        String argsStr = "/command ";
        //when
        Args args = Args.fromCommandLine(argsStr);
        //then
        assertThat(args.args(), is(argsArr));
    }

    @Test
    void argsList__normal() {
        //given
        String[] argsArr = {"some", "args"};
        //when
        Args args = givenArgsOf(argsArr);
        //then
        assertThat(args.argsList(), contains(argsArr));
    }

    @Test
    void argsList__empty() {
        //given
        String[] argsArr = {};
        //when
        Args args = givenArgsOf(argsArr);
        //then
        assertThat(args.argsList(), is(empty()));
    }

    @Test
    void argsStream() {
        //given
        String[] argsArr = {"some", "args"};
        //when
        Args args = givenArgsOf(argsArr);
        //then
        assertThat(args.argsStream().toArray(), is(argsArr));
    }

    @Test
    void arg__exists() {
        //given
        String[] argsArr = {"some", "args"};
        //when
        Args args = givenArgsOf(argsArr);
        //then
        assertThat(args.arg(0), is("some"));
        assertThat(args.arg(1), is("args"));
    }

    @Test
    void arg__outOfRange() {
        //given
        String[] argsArr = {"some", "args"};
        //when
        Args args = givenArgsOf(argsArr);
        //then
        assertThrows(
                MissingArgumentException.class,
                () -> args.arg(2)
        );
    }

    @Test
    void intArg__base10() {
        //given
        String[] argsArr = {"some", "157"};
        //when
        Args args = givenArgsOf(argsArr);
        //then
        assertThat(args.intArg(1), is(157));
    }

    @Test
    void intArg__base16() {
        //given
        String[] argsArr = {"some", "0xFE"};
        //when
        Args args = givenArgsOf(argsArr);
        //then
        assertThat(args.intArg(1), is(0xFE));
    }

    @Test
    void intArg__invalid() {
        //given
        String[] argsArr = {"some", "stringlol"};
        //when
        Args args = givenArgsOf(argsArr);
        //then
        assertThrows(
                ArgumentFormatException.class,
                () -> args.intArg(1)
        );
    }

    @Test
    void intArg__outOfIntRange() {
        //given
        String[] argsArr = {"some", "2307147392065180651301935"};
        //when
        Args args = givenArgsOf(argsArr);
        //then
        assertThrows(
                ArgumentFormatException.class,
                () -> args.intArg(1)
        );
    }

    @Test
    void doubleArg__valid() {
        //given
        String[] argsArr = {"some", "0.05"};
        //when
        Args args = givenArgsOf(argsArr);
        //then
        assertThat(args.doubleArg(1), is(0.05D));
    }

    @Test
    void doubleArg__germanComma() {
        //given
        String[] argsArr = {"some", "0,05"};
        //when
        Args args = givenArgsOf(argsArr);
        //then
        assertThat(args.doubleArg(1), is(0.05D));
    }

    @Test
    void doubleArg__invalid() {
        //given
        String[] argsArr = {"some", "stringlol"};
        //when
        Args args = givenArgsOf(argsArr);
        //then
        assertThrows(
                ArgumentFormatException.class,
                () -> args.doubleArg(1)
        );
    }

    @Test
    void enumArg__upperCase() {
        //given
        String[] argsArr = {"MINUTES"};
        //when
        Args args = givenArgsOf(argsArr);
        //then
        assertThat(args.enumArg(TimeUnit.class, 0), is(TimeUnit.MINUTES));
    }

    @Test
    void enumArg__mixedCase() {
        //given
        String[] argsArr = {"MiNuTeS"};
        //when
        Args args = givenArgsOf(argsArr);
        //then
        assertThat(args.enumArg(TimeUnit.class, 0), is(TimeUnit.MINUTES));
    }

    @Test
    void enumArg__dashToUnderscore() {
        //given
        String[] argsArr = {"WOW-SCORE"};
        //when
        Args args = givenArgsOf(argsArr);
        //then
        assertThat(args.enumArg(EnumWithUnderscore.class, 0), is(EnumWithUnderscore.WOW_SCORE));
    }

    // Sorry for this, can't think of an enum with underscores in the Java standard lib right now
    private enum EnumWithUnderscore {
        WOW_SCORE;
    }

    @Test
    void enumArg__invalid() {
        //given
        String[] argsArr = {"like, forever"};
        //when
        Args args = givenArgsOf(argsArr);
        //then
        assertThrows(
                ArgumentFormatException.class,
                () -> args.enumArg(TimeUnit.class, 0)
        );
    }


    @Test
    void uuidArg__valid() {
        //given
        UUID uuid = UUID.randomUUID();
        String[] argsArr = {uuid.toString()};
        //when
        Args args = givenArgsOf(argsArr);
        //then
        assertThat(args.uuidArg(0), is(uuid));
    }

    @Test
    void uuidArg__invalid() {
        //given
        String[] argsArr = {"some", "stringlol"};
        //when
        Args args = givenArgsOf(argsArr);
        //then
        assertThrows(
                ArgumentFormatException.class,
                () -> args.uuidArg(1)
        );
    }

    @Test
    void findArg__present() {
        //given
        String[] argsArr = {"some", "arg"};
        //when
        Args args = givenArgsOf(argsArr);
        //then
        assertThat(args.findArg(0), is(Optional.of("some")));
        assertThat(args.findArg(1), is(Optional.of("arg")));
    }

    @Test
    void findArg__absent() {
        //given
        String[] argsArr = {"some", "arg"};
        //when
        Args args = givenArgsOf(argsArr);
        //then
        assertThat(args.findArg(2), is(Optional.empty()));
    }

    @Test
    void hasArg() {
        //given
        String[] argsArr = {"some", "arg"};
        //when
        Args args = givenArgsOf(argsArr);
        //then
        assertThat(args.hasArg(0), is(true));
        assertThat(args.hasArg(1), is(true));
        assertThat(args.hasArg(2), is(false));
        assertThat(args.hasArg(-1), is(false));
    }

    @Test
    void hasNoArgs() {
        //given nothing
        //when
        Args noArgs = new Args();
        Args withArgs = new Args("wow");
        //then
        assertThat(noArgs.hasNoArgs(), is(true));
        assertThat(withArgs.hasNoArgs(), is(false));
    }

    @Test
    void argRange__full() {
        //given
        String[] argsArr = {"some", "arg", "and"};
        //when
        Args args = givenArgsOf(argsArr);
        //then
        assertThat(args.argRange(0, 3), is(argsOf(argsArr)));
    }

    @Test
    void argRange__lowerEdge() {
        //given
        String[] argsArr = {"some", "arg", "and"};
        //when
        Args args = givenArgsOf(argsArr);
        //then
        assertThat(args.argRange(1, 3), is(argsOf("arg", "and")));
    }

    @Test
    void argRange__upperEdge() {
        //given
        String[] argsArr = {"some", "arg", "and"};
        //when
        Args args = givenArgsOf(argsArr);
        //then
        assertThat(args.argRange(0, 2), is(argsOf("some", "arg")));
    }

    @Test
    void argRange__middle() {
        //given
        String[] argsArr = {"some", "arg", "and"};
        //when
        Args args = givenArgsOf(argsArr);
        //then
        assertThat(args.argRange(1, 2), is(argsOf("arg")));
    }

    @Test
    void argRange__none() {
        //given
        String[] argsArr = {"some", "arg", "and"};
        //when
        Args args = givenArgsOf(argsArr);
        //then
        assertThat(args.argRange(1, 1), is(new Args()));
    }
}