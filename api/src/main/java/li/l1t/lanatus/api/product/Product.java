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

package li.l1t.lanatus.api.product;

import li.l1t.common.misc.Identifiable;

/**
 * Represents an immutable snapshot of a product that may be bought through Lanatus.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2016-09-28 (4.2.0)
 */
public interface Product extends Identifiable {
    /**
     * @return the Lanatus module handling this product
     */
    String getModule();

    /**
     * @return the display name of this product
     */
    String getDisplayName();

    /**
     * @return the human-readable description of this product, may contain newlines
     */
    String getDescription();

    /**
     * Gets the internal name of the icon to use for this product in Minecraft, represented in the
     * format {@code materialName[:dataValue]}, where {@code materialName} corresponds to the
     * lowercase name of the material in the Material enum and {@code dataValue} means the legacy
     * data "damage" on the item.<p>For example, {@code melon_block} represents a melon block and
     * {@code wool:0} represents white wool.</p>
     *
     * @return the internal name of the icon to use for this product in Minecraft
     */
    String getIconName();

    /**
     * @return the amount of melons a player has to pay when purchasing this product
     */
    int getMelonsCost();

    /**
     * @return whether this product may be purchased currently
     */
    boolean isActive();

    /**
     * Gets whether this product is permanent. Permanent products are represented by positions in
     * players' accounts and have effect as long as their position exists. Non-permanent products
     * only appear in the purchase log and generally are single-use.
     *
     * @return whether this product is permanent
     */
    boolean isPermanent();
}
