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

package li.l1t.common.inventory;

import com.google.common.base.Preconditions;

/**
 * Represents the position of a slot in an inventory by its distance from the top left slot. The
 * x axis is horizontal and the y axis is vertical. (0, 0) is the top left slot.
 * <p>This implementation is immutable.</p>
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 2016-07-22
 */
public class SlotPosition {
    /**
     * How many slots there are in each row in a fake inventory.
     */
    public static final int SLOTS_PER_ROW = 9;

    /**
     * The maximum amount of rows in an inventory.
     */
    public static final int MAX_ROW_COUNT = 6;

    private final int x;
    private final int y;

    /**
     * Creates a new slot position from its coordinates in the coordinate system.
     *
     * @param x the x coordinate, that is, the horizontal distance from the top left slot
     * @param y the y coordinate, that is, the vertical distance from the top left slot
     */
    private SlotPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a new position object from a {@link #toSlotId() slot id}.
     *
     * @param slotId the slot id to create the position from
     * @return the created position
     * @throws IllegalArgumentException if slotId is negative
     */
    public static SlotPosition fromSlotId(int slotId) {
        Preconditions.checkArgument(slotId >= 0, "slotId must not be negative!");
        int y = Math.floorDiv(slotId, SLOTS_PER_ROW);
        int x = slotId % SLOTS_PER_ROW;
        return new SlotPosition(x, y);
    }

    /**
     * Creates a new position object from its coordinates.
     *
     * @param x the x coordinate, that is, the horizontal distance from the top left slot
     * @param y the y coordinate, that is, the vertical distance from the top left slot
     * @return the created position
     */
    public static SlotPosition ofXY(int x, int y) {
        return new SlotPosition(x, y);
    }

    /**
     * Creates a new position object from its coordinates, validating that it is a valid slot.
     *
     * @param x the x coordinate, that is, the horizontal distance from the top left slot
     * @param y the y coordinate, that is, the vertical distance from the top left slot
     * @return the created position
     * @throws IllegalArgumentException if the represented position is not a
     *                                  {@link #isValidSlot() valid slot}
     */
    public static SlotPosition ofXYValidated(int x, int y) {
        Preconditions.checkArgument(isValidSlot(x, y), "not a valid slot: (%s,%s)", x, y);
        return ofXY(x, y);
    }

    /**
     * Checks whether given coordinates represent a valid slot in a Minecraft double chest with
     * {@value #SLOTS_PER_ROW} columns and {@value #MAX_ROW_COUNT}
     * rows.
     *
     * @param x the x coordinate of the slot to check
     * @param y the y coordinate of the slot to check
     * @return whether the slot represented by given coordinates is within the bounds of a double
     * chest
     */
    public static boolean isValidSlot(int x, int y) {
        return x >= 0 && y >= 0 &&
                x < SLOTS_PER_ROW && y < MAX_ROW_COUNT;
    }

    /**
     * @return the x coordinate of this position, that is, the horizontal distance from the top
     * left slot.
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y coordinate of this position, that is, the vertical distance from the top
     * left slot.
     */
    public int getY() {
        return y;
    }

    /**
     * Converts this position to a slot id. The top left slot has the id of zero, the one right to
     * that has the id of one, and so on. The slot below the top left slot has the id of nine.
     *
     * @return the slot id corresponding to this position
     */
    public int toSlotId() {
        return (y * SLOTS_PER_ROW) + x;
    }

    /**
     * Adds some coordinates to this position.
     *
     * @param xMod the modifier for the x value, may be negative
     * @param yMod the modifier for the y value, may be negative
     * @return the new position
     */
    public SlotPosition add(int xMod, int yMod) {
        return new SlotPosition(x + xMod, y + yMod);
    }

    /**
     * Adds some another position to this position by adding the x and y coordinates.
     *
     * @param position the position to add
     * @return the new position
     */
    public SlotPosition add(SlotPosition position) {
        return new SlotPosition(x + position.getX(), y + position.getY());
    }

    /**
     * Checks whether this position represents a valid slot in a Minecraft double chest.
     *
     * @return whether this position is a valid double chest slot
     * @see SlotPosition#isValidSlot(int, int)
     */
    public boolean isValidSlot() {
        return isValidSlot(x, y);
    }

    /**
     * Checks whether the result of the addition of this slot and given coordinates would result
     * in a {@link #isValidSlot(int, int) valid slot}.
     *
     * @param xMod the modifier for the x value, may be negative
     * @param yMod the modifier for the y value, may be negative
     * @return whether given addition is possible
     */
    public boolean isAdditionPossible(int xMod, int yMod) {
        return isValidSlot(x + xMod, y + yMod);
    }

    /**
     * Checks whether the result of the addition of this slot and given position would result
     * in a {@link #isValidSlot(int, int) valid slot}.
     *
     * @param position the position to add
     * @return whether given addition is possible
     */
    public boolean isAdditionPossible(SlotPosition position) {
        return isValidSlot(x + position.getX(), y + position.getY());
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SlotPosition)) return false;
        SlotPosition that = (SlotPosition) o;
        return x == that.x && y == that.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
