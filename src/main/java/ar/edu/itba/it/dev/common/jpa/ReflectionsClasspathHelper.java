package ar.edu.itba.it.dev.common.jpa;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;

import org.reflections.Reflections;

import com.google.common.collect.Sets;

/**
 * Copy of Reflections ClasspathHelper from trunk (to be 0.97)  until a new version is released
 */
public class ReflectionsClasspathHelper {

    /** returns {@code Thread.currentThread().getContextClassLoader()} */
    public static ClassLoader contextClassLoader() { return Thread.currentThread().getContextClassLoader(); }

    /** returns {@code Reflections.class.getClassLoader()} */
    public static ClassLoader staticClassLoader() { return Reflections.class.getClassLoader(); }

    /** returns given classLoaders, if not null, otherwise defaults to both {@link #contextClassLoader()} and {@link #staticClassLoader()} */
    public static ClassLoader[] classLoaders(ClassLoader... classLoaders) {
        if (classLoaders != null && classLoaders.length != 0) {
            return classLoaders;
        } else {
            ClassLoader contextClassLoader = contextClassLoader(), staticClassLoader = staticClassLoader();
            return contextClassLoader != staticClassLoader ? new ClassLoader[]{contextClassLoader, staticClassLoader} : new ClassLoader[]{contextClassLoader};
        }
    }

    /** returns urls with resources of package starting with given name, using {@link ClassLoader#getResources(String)}
     * <p>that is, forPackage("org.reflections") effectively returns urls from classpath with packages starting with {@code org.reflections}
     * <p>if optional {@link ClassLoader}s are not specified, then both {@link #contextClassLoader()} and {@link #staticClassLoader()} are used for {@link ClassLoader#getResources(String)}
     */
    public static Set<URL> forPackage(String name, ClassLoader... classLoaders) {
        final Set<URL> result = Sets.newHashSet();

        final ClassLoader[] loaders = classLoaders(classLoaders);
        final String resourceName = name.replace(".", "/");
        String encodedResourceName = ReflectionsUtils.encodePath(resourceName, true);

        for (ClassLoader classLoader : loaders) {
            try {
                final Enumeration<URL> urls = classLoader.getResources(resourceName);
                while (urls.hasMoreElements()) {
                    final URL url = urls.nextElement();
                    int index = url.toExternalForm().lastIndexOf(encodedResourceName);
                    if (index != -1) {
                        result.add(new URL(url.toExternalForm().substring(0, index)));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
    
}
