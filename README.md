# **KUrlet** -- **K**otlin **UR**i temp**LET**

[![GitHub](https://img.shields.io/github/license/C06A/KUrlet?logo=apache)](https://github.com/C06A/KUrlet/blob/master/LICENSE)
[![Kotlin](https://img.shields.io/badge/kotlin-1.9.10-blue.svg?logo=kotlin&color=yellow)](http://kotlinlang.org)
![GitHub top language](https://img.shields.io/github/languages/top/C06A/KUrlet)
![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/C06A/KUrlet)

[![Source](https://img.shields.io/badge/project-GITHub-green.svg?logo=github)](https://github.com/C06A/KUrlet)
[![Download Latest](https://img.shields.io/badge/download-GITHub-green.svg?logo=github)](https://github.com/C06A/KUrlet/releases)
![GitHub repo size](https://img.shields.io/github/repo-size/C06A/KUrlet?logo=github)
![GitHub repo file count (file type)](https://img.shields.io/github/directory-file-count/C06A/KUrlet?logo=github)
![GitHub repo file count (file extension)](https://img.shields.io/github/directory-file-count/C06A/KUrlet/src?label=extensions&logo=github)

[//]: # (![GitHub last commit &#40;by committer&#41;]&#40;https://img.shields.io/github/last-commit/C06A/KUrlet/develop&#41;)

[//]: # (![GitHub tag checks state]&#40;https://img.shields.io/github/checks-status/C06A/KUrlet/2.1.0&#41;)

[//]: # (![GitHub Release Date - Published_At]&#40;https://img.shields.io/github/release-date/C06A/KUrlet&#41;)

[//]: # ([![Maven Central]&#40;https://img.shields.io/maven-central/v/com.helpchoice.kotlin/KUrlet?logo=apachemaven&#41;]&#40;https://mvnrepository.com/search?q=KUrlet&d=com.helpchoice&#41;)

[//]: # (![GitHub all releases]&#40;https://img.shields.io/github/downloads/C06A/KUrlet/total&#41;)

[//]: # ()
[//]: # (![GitHub issues]&#40;https://img.shields.io/github/issues-raw/C06A/KUrlet?logo=github&#41;)

[//]: # (![GitHub pull requests]&#40;https://img.shields.io/github/issues-pr-raw/C06A/KUrlet?logo=github&#41;)

[//]: # (![GitHub Discussions]&#40;https://img.shields.io/github/discussions/C06A/KUrlet&#41;)

[//]: # (![GitHub forks]&#40;https://img.shields.io/github/forks/C06A/KUrlet&#41;)

[//]: # (![GitHub Repo stars]&#40;https://img.shields.io/github/stars/C06A/KUrlet?label=Repo+stars&#41;)

[//]: # (![GitHub watchers]&#40;https://img.shields.io/github/watchers/C06A/KUrlet&#41;)

[//]: # (![GitHub followers]&#40;https://img.shields.io/github/followers/C06A&#41;)
[//]: # (![GitHub User's stars]&#40;https://img.shields.io/github/stars/C06A?label=User+stars&#41;)

[//]: # (![GitHub contributors &#40;via allcontributors.org&#41;]&#40;https://img.shields.io/github/contributors/C06A/KUrlet?color=blue&#41;)
[//]: # (![GitHub language count]&#40;https://img.shields.io/github/languages/count/C06A/KUrlet&#41;)
[//]: # (![GitHub search hit counter]&#40;https://img.shields.io/github/search/C06A/KUrlet/search&#41;)
[//]: # (![GitHub issues]&#40;https://img.shields.io/github/issues/C06A/KUrlet&#41;)
[//]: # (![GitHub pull requests]&#40;https://img.shields.io/github/issues-pr/C06A/KUrlet&#41;)
[//]: # (![Maven metadata URL]&#40;https://img.shields.io/maven-metadata/v?metadataUrl=https://repo1.maven.org/maven2/com/helpchoice/kotlin/KUrlet/maven-metadata.xml&logo=apachemaven&#41;)
[//]: # (![GitHub release &#40;with filter&#41;]&#40;https://img.shields.io/github/v/release/C06A/KUrlet&#41;)

This project (sounds "`qur-let`") includes the library
to work with URI Template [RFC-6570](https://tools.ietf.org/html/rfc6570) implemented as Kotlin multiplatform project.

## Dependency on KUrlet library

In order to use any version of this library, published to the MavenCentral, in other Gradle project
add into dependencies section of the `build.gradle.kts` file:
```kotlin
dependencies {
    implementation("com.helpchoice.kotlin:KUrlet:<version>")
}
```

Alternatively, in the projects other than Kotlin MPP, should include platform-specific artifact:
```kotlin
dependencies {
    implementation("com.helpchoice.kotlin:KUrlet-jvm:<version>")
}
```
```kotlin
dependencies {
    implementation("com.helpchoice.kotlin:KUrlet-js:<version>")
}
```
```kotlin
dependencies {
    implementation("com.helpchoice.kotlin:KUrlet-native:<version>")
}
```

For earlier versions, which were not published in MavenCentral use JitPack project.
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
provided in the [RFC-6570](https://www.rfc-editor.org/rfc/rfc6570) and additional tests for more complicated cases.

Tests are organized in groups, represented by classes and inner classes.

## How this project measures against RFC-6570?

People have different interpretation of some aspects of the [RFC-6570](https://www.rfc-editor.org/rfc/rfc6570). Here are some comments
on this project implementation approach and reasoning behind them.

Some of these conversation are from long time ago,
but because the [RFC-6570](https://www.rfc-editor.org/rfc/rfc6570) didn't change and still allows multiple interpretation,
file issue in GitHub with your comments and recommendations.

### Combinations of the modifiers

The RFC6570 states that a prefix modifier should not be applied `to variables that have composite values`.
On another hand an explode modifier `indicates that the variable is to be treated as a composite value`.

Some people interpret this as both modifiers should not be applied to the single expansion. However, in Appendix A
it saies: `... If it is an explode ("*"), scan the next character.  If it is a prefix (":"), continue scanning...`.
This means that modifiers can be combined.

This project accepts both modifiers even though apply only one, appropriate to the type of the variable value.


### Order of modifiers

The RFC6570 doesn't mention the order of modifiers. The Appendix A shows an explode modifier comes before a prefix.
There is no reason to do not allow reverse order as well.

This project allows modifiers in either order.


### Restrictions

This project built to expand the template. To minimize the load on the client, using this library,
it may not report on templates not following all limitations of the [RFC-6570](https://www.rfc-editor.org/rfc/rfc6570).
If template is not valid, the result of expansion is undefined.


[//]: # (### Resources)

[//]: # ()
[//]: # (Online utility to test URI Template: https://www.utilities-online.info/uritemplate)


## Improvements

To suggest improvements please create Pull Requests with your code changes in
the [GitHub project](https://github.com/C06A/KUrlet/pulls).
