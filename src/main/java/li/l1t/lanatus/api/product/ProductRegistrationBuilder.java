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

package li.l1t.lanatus.api.product;

import li.l1t.common.exception.DatabaseException;

/**
 * Fluent builder interface for product registrations, providing modules with a way to make sure
 * their products actually exist when they connect to Lanatus.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 * @since 2016-10-18 (4.2.0)
 */
public interface ProductRegistrationBuilder {
    /**
     * @param moduleName the name of the module to select
     * @return this builder
     */
    ProductRegistrationBuilder inModule(String moduleName);

    /**
     * Selects the current module.
     *
     * @return this builder
     */
    ProductRegistrationBuilder inThisModule();

    /**
     * @param displayName the display name to set on the builder
     * @return this builder
     */
    ProductRegistrationBuilder withDisplayName(String displayName);

    /**
     * @param description the description to set on the builder
     * @return this builder
     */
    ProductRegistrationBuilder withDescription(String description);

    /**
     * @param iconName the {@linkplain Product#getIconName() icon name} to set on the builder
     * @return this builder
     */
    ProductRegistrationBuilder withIcon(String iconName);

    /**
     * @param melonsCost the default melons cost to set on the builder
     * @return this builder
     * @throws IllegalArgumentException if given cost is negative
     */
    ProductRegistrationBuilder withMelonsCost(int melonsCost);

    /**
     * @param permanent the {@link Product#isPermanent() permanence status} to set on the builder
     * @return this builder
     */
    ProductRegistrationBuilder withPermanent(boolean permanent);

    /**
     * Executes the registration represented by this builder. Registrations where there is already a
     * product by that unique id are silently ignored. Note that only the unique id is actually
     * checked, to allow administrators to customise display names and other parts of the product
     * appearance.
     *
     * @return the newly created product, or the already existing product with the builder's unique
     * id
     * @throws DatabaseException if a database error occurs
     */
    Product register() throws DatabaseException;
}
