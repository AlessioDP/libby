### Version 1.3.2
* Support relocation of libraries compiled for Java up to 27
* Updated libraries used by Libby

### Version 1.3.1
* Support relocation of libraries compiled for Java up to 23
* Updated libraries used by Libby

### Version 1.3.0
* Support for snapshot libraries ([GH-20](https://github.com/AlessioDP/libby/pull/20))
* Updated jar-relocator ([GH-21](https://github.com/AlessioDP/libby/pull/21))
* Updated Maven plugins

### Version 1.2.0
* Updated platform supported by Libby
  * Sponge from 7 to 8 (Logger now uses log4j)
* Added Java 9 Modules support ([GH-12](https://github.com/AlessioDP/libby/pull/12))
* Added support for new Paper library manager ([GH-13](https://github.com/AlessioDP/libby/pull/13))

### Version 1.1.5

* Fixed Velocity and Sponge support:
    * Removed the constructor that didn't specify `directoryName` from Sponge
    * Removed @Inject from Velocity constructors

### Version 1.1.4

* Added another way to support Java 16+ without needing any additional command line parameters (using the Unsafe class)
* Updated libraries used by Libby

### Version 1.1.3

* Added support for Java 16+ without needing `--illegal-access=permit` or `--add-opens java.base/java.net=ALL-UNNAMED` (using [ByteBuddy's Java Agent](https://github.com/raphw/byte-buddy/tree/master/byte-buddy-agent))
* Added possibility to specify per-library repositories with `Library.Builder#repository(String repositoryURL)`
* Avoid registration of duplicated repositories

### Version 1.1.2

* Added support for libraries compiled with Java 16
* Updated libraries used by Libby

### Version 1.1.1

* Download directory name can now be changed when instantiating the LibraryManager
* When loading a library with `libraryBuilder.isolatedLoad(true).id(aId)` and an IsolatedClassLoader with id `aId` is present
  it will be used instead of creating a new one

### Version 1.1.0

* Libraries can be loaded from an `IsolatedClassLoader`
    * Use `LibraryManager#getIsolatedClassLoaderOf(...)` to get the `IsolatedClassLoader` via its `id`
    * Use `Library.Builder#id(...)` to set an ID to the library
    * Use `Library.Builder#isolatedLoad(...)` to load it via `IsolatedClassLoader`
* Libraries are updated
* Support for Java 9+ Modules to prevent deprecations
* Distribution management to repo.alessiodp.com
