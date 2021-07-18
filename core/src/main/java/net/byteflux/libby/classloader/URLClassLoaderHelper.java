package net.byteflux.libby.classloader;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;

import static java.util.Objects.requireNonNull;

/**
 * A wrapper around {@link URLClassLoader} for adding URLs to
 * the classpath.
 */
public class URLClassLoaderHelper {

    /**
     * The class loader being managed by this helper.
     */
    private final CustomClassLoader classLoader;

    /**
     * Creates a new custom class loader with the parent's urls & the parent itself.
     * The custom class loader has a public method referencing the protected one, and serves as a go-around solution, instead of reflection.
     *
     * @param classLoader the class loader to manage
     */
    public URLClassLoaderHelper(URLClassLoader classLoader) {
        this.classLoader = new CustomClassLoader(requireNonNull(classLoader).getURLs(), requireNonNull(classLoader));
    }

    /**
     * Adds a URL to the class loader's classpath.
     *
     * @param url the URL to add
     */
    public void addToClasspath(URL url) {
        this.classLoader.addURL(url);
    }

    /**
     * Adds a path to the class loader's classpath.
     *
     * @param path the path to add
     */
    public void addToClasspath(Path path) {
        try {
            addToClasspath(requireNonNull(path, "path").toUri().toURL());
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static void openUrlClassLoaderModule() throws Exception {
        //
        // Thanks to lucko (Luck) <luck@lucko.me> for this snippet used in his own class loader
        //
        // This is a workaround used to maintain Java 9+ support with reflections
        // Thanks to this you will be able to run this class loader with Java 8+

        // This is effectively calling:
        //
        // URLClassLoader.class.getModule().addOpens(
        //     URLClassLoader.class.getPackageName(),
        //     ReflectionClassLoader.class.getModule()
        // );
        //
        // We use reflection since we build against Java 8.

        Class<?> moduleClass = Class.forName("java.lang.Module");
        Method getModuleMethod = Class.class.getMethod("getModule");
        Method addOpensMethod = moduleClass.getMethod("addOpens", String.class, moduleClass);

        Object urlClassLoaderModule = getModuleMethod.invoke(URLClassLoader.class);
        Object thisModule = getModuleMethod.invoke(URLClassLoaderHelper.class);

        addOpensMethod.invoke(urlClassLoaderModule, URLClassLoader.class.getPackage().getName(), thisModule);
    }
}
