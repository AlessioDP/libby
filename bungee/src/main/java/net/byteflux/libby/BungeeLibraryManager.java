package net.byteflux.libby;

import net.byteflux.libby.classloader.URLClassLoaderHelper;
import net.byteflux.libby.logging.adapters.JDKLogAdapter;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.InputStream;
import java.net.URLClassLoader;
import java.nio.file.Path;

import static java.util.Objects.requireNonNull;

/**
 * A runtime dependency manager for Bungee plugins.
 */
public class BungeeLibraryManager extends LibraryManager {
    /**
     * Default libraries.json name (see Minecrell/plugin-yml repository at GitHub)
     */
    private static final String DEFAULT_JSON = "bungee-libraries.json";

    /**
     * Plugin classpath helper
     */
    private final URLClassLoaderHelper classLoader;
    /**
     * The plugin
     */
    private final Plugin plugin;

    /**
     * Creates a new Bungee library manager.
     *
     * @param plugin the plugin to manage
     */
    public BungeeLibraryManager(Plugin plugin) {
        this(plugin, "lib");
    }

    /**
     * Creates a new Bungee library manager.
     *
     * @param plugin the plugin to manage
     * @param directoryName download directory name
     */
    public BungeeLibraryManager(Plugin plugin, String directoryName) {
        super(new JDKLogAdapter(requireNonNull(plugin, "plugin").getLogger()), plugin.getDataFolder().toPath(), directoryName);
        classLoader = new URLClassLoaderHelper((URLClassLoader) plugin.getClass().getClassLoader(), this);
        this.plugin = plugin;
    }

    /**
     * Adds a file to the Bungee plugin's classpath.
     *
     * @param file the file to add
     */
    @Override
    protected void addToClasspath(Path file) {
        classLoader.addToClasspath(file);
    }

    /**
     * Load the libraries by reading {@link #DEFAULT_JSON} file in the plugin.
     */
    public void loadLibrariesInJsonFile() {
        loadLibrariesInJsonFile(DEFAULT_JSON);
    }

    /**
     * Load the libraries by reading provided JSON file in the plugin.
     *
     * @param resourcePath The path to the JSON file
     */
    public void loadLibrariesInJsonFile(String resourcePath) {
        final InputStream stream = plugin.getResourceAsStream(resourcePath);
        if (stream != null) {
            loadLibrariesInJsonFile(stream);
        }
    }
}
