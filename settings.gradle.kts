rootProject.name = "fulflix"

registerModules(rootDir)

private fun registerModules(directory: File) {
    directory.walkTopDown()
        .filter {
            it.isDirectory && (
                    File(it, "build.gradle.kts").exists()
                            || File(it, "build.gradle").exists()
                    )
        }
        .forEach { includeModule(it) }
}

private fun includeModule(moduleDir: File) {
    val relativePath = moduleDir.relativeTo(rootDir).path
    val moduleName = convertPathToModuleName(relativePath)

    include(moduleName)
    project(moduleName).projectDir = moduleDir
}

private fun convertPathToModuleName(relativePath: String): String {
    val modulePrefix = ":"

    return "${modulePrefix}${
        relativePath
            .replace(File.separatorChar, modulePrefix.first())
            .substringAfter(modulePrefix)
    }"
}
