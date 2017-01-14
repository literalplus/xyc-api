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

package li.l1t.common.version;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;
import java.util.jar.JarInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class offers a static utility method to extract manifest version information from JAR files
 * by a class. Instances hold the retrieved immutable information.
 *
 * @author <a href="https://l1t.li/">Literallie</a>
 */
public final class PluginVersion {

    private final String implementationTitle;
    private final String implementationVersion;
    private final String implementationBuild;

    private PluginVersion(String implementationTitle, String implementationVersion, String implementationBuild) {
        this.implementationTitle = implementationTitle;
        this.implementationVersion = implementationVersion;
        this.implementationBuild = implementationBuild;
    }

    /**
     * Attempts to retrieve version information for a class from its enclosing JAR archive's
     * manifest. If the class is not in such archive, or the information cannot be acquired for any
     * other reason, a special instance with {@link Class#getSimpleName()} as implementation title
     * and "unknown" as version and build number will be returned. Note that any or all of the
     * attributes may be unspecified and therefore null.
     *
     * @param clazz the class to retrieve version information for
     * @return the version information for given class
     */
    @Nonnull
    public static PluginVersion ofClass(@Nonnull Class<?> clazz) {
        @Nullable CodeSource codeSource = clazz.getProtectionDomain().getCodeSource();

        if (codeSource != null) {
            URL url = codeSource.getLocation();

            if (url != null && url.toExternalForm().endsWith(".jar")) {
                try (JarInputStream jis = new JarInputStream(url.openStream())) {
                    if (jis.getManifest() != null) {
                        Attributes attrs = jis.getManifest().getMainAttributes();

                        return new PluginVersion(
                                attrs.getValue(Name.IMPLEMENTATION_TITLE),
                                attrs.getValue(Name.IMPLEMENTATION_VERSION),
                                attrs.getValue("Implementation-Build")
                        );
                    }
                } catch (IOException ex) {
                    Logger.getLogger(PluginVersion.class.getName()).log(Level.SEVERE,
                            "Error occurred while reading JAR version info for " + clazz.getName(), ex);
                }
            }
        }

        return new PluginVersion(clazz.getSimpleName(), "unknown", "unknown");
    }

    @Override
    public String toString() {
        return implementationTitle + " Version " + implementationVersion + " Build " + implementationBuild;
    }

    @Nullable
    public String getImplementationTitle() {
        return this.implementationTitle;
    }

    @Nullable
    public String getImplementationVersion() {
        return this.implementationVersion;
    }

    @Nullable
    public String getImplementationBuild() {
        return this.implementationBuild;
    }
}
