package net.byteflux.libby;

import cn.nukkit.plugin.Plugin;
import net.byteflux.libby.classloader.URLClassLoaderHelper;
import net.byteflux.libby.logging.adapters.NukkitLogAdapter;

import java.io.InputStream;
import java.net.URLClassLoader;
import java.nio.file.Path;

import static java.util.Objects.requireNonNull;

/**
 * A runtime dependency manager for Nukkit plugins.
 */
public class NukkitLibraryManager extends LibraryManager {
    /**
     * Default libraries.json name (see Minecrell/plugin-yml repository at GitHub)
     */
    private static final String DEFAULT_JSON = "nukkit-libraries.json";

    /**
     * Plugin classpath helper
     */
    private final URLClassLoaderHelper classLoader;
    /**
     * The plugin
     */
    private final Plugin plugin;

    /**
     * Creates a new Nukkit library manager.
     *
     * @param plugin the plugin to manage
     */
    public NukkitLibraryManager(Plugin plugin) {
        this(plugin, "lib");
    }

    /**
     * Creates a new Nukkit library manager.
     *
     * @param plugin the plugin to manage
     * @param directoryName download directory name
     */
    public NukkitLibraryManager(Plugin plugin, String directoryName) {
        super(new NukkitLogAdapter(requireNonNull(plugin, "plugin").getLogger()), plugin.getDataFolder().toPath(), directoryName);
        classLoader = new URLClassLoaderHelper((URLClassLoader) plugin.getClass().getClassLoader(), this);
        this.plugin = plugin;
    }

    /**
     * Adds a file to the Nukkit plugin's classpath.
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
        final InputStream stream = plugin.getResource(resourcePath);
        if (stream != null) {
            loadLibrariesInJsonFile(stream);
        }
    }
}
