# <span style="color:blue">KUrlet</span> -- <span style="color:blue">K</span>otlin <span style="color:blue">UR</span>i temp<span style="color:blue">LET</span>
[![](https://jitpack.io/v/com.helpchoice/KUrlet.svg)](https://jitpack.io/#com.helpchoice/KUrlet)

This project (sounds "`qur-let`") includes the library
to work with URI Template [RFC-6570](https://tools.ietf.org/html/rfc6570) implemented as Kotlin multiplatform project.

## Dependency on KUrlet library

In order to use any version of this library, published to the MavenCentral, in other Kotlin project
add into dependencies section of the `build.gradle.kts` file:
```kotlin
dependencies {
    implementation("com.helpchoice.kotlin:KUrlet:<version>")
}
```

For versions, which were not published in MavenCentral use JitPack project.
To do that add at the end of repositories list of your `gradle.build.kts` file:

```kotlin
repositories {
    ...
    maven(url = 'https://jitpack.io')
}
```
... and in dependencies
```kotlin
dependencies {
    implementation("com.helpchoice.kotlin:KUrlet:<version-tag>")
}
```

## Project structure

The project is a multiplatform pure Kotlin library. It includes separate folder of the top level module
for each supported platform (JVM, JS, and Native) code and tests.

There is one more module `convention-plugins`, which publishes released artifacts to MavenCentral.

### Common code

The common code includes 2 classes:

* **`UriTempl`** -- represents the UriTemplate object and encloses code to split template into literals and expressions
* **`Expression`** -- represents the string literal followed by expression
(as defined in the [RFC6570](https://www.rfc-editor.org/rfc/rfc6570))

Both classes are pure Kotlin and don't need platform-specific related code.

### Platform-specific code

Because common classes defined in the pure Kotlin, there is no need to implement anything in the platform-specific code.

### Regression Tests

The Common tests folder includes tests, based on the examples
provided in the [RFC6570](https://www.rfc-editor.org/rfc/rfc6570) and additional tests for more complicated cases.

Tests are organized in groups, represented by classes and inner classes.

## Improvements

To suggest improvements please create Pull Requests with your code changes in
the [GitHub project](https://github.com/C06A/KUrlet/pulls).
