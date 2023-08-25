# <span style="color:blue">KUrlet</span> -- <span style="color:blue">K</span>otlin <span style="color:blue">UR</span>i temp<span style="color:blue">LET</span>
[![](https://jitpack.io/v/com.helpchoice/KUrlet.svg)](https://jitpack.io/#com.helpchoice/KUrlet)

This project (sounds "`qur-let`") includes the library
to work with URI Template standard (https://tools.ietf.org/html/rfc6570) implemented as Kotlin multiplatform project.

## Dependency on KUrlet library

In order to use this library in other Kotlin project add at the end of repositories list of your `gradle.build` file:

```kotlin
repositories {
    ...
    maven(url = 'https://jitpack.io')
}
```
... and in dependencies
```kotlin
dependencies {
    implementation("com.helpchoice:KUrlet:2.0.0")
}
```

## Project structure

The project is built on Gradle and includes 2 submodules. Root-level module includes the main implementation code
shared between JVM and JS targets. Each submodule includes the target-specific code. The common code get copied
into submodule before build.

### Shared code

Root-level module includes 2 abstract classes:

* **`UriTempl`** -- represents the UriTemplate object and encloses code to split template into literals and expressions
* **`Expression`** -- represents the string literal followed by expression (as defined in the standard)

Both classes are abstract and implemented by corresponded classes in each submodule.

### JVM-specific code

JVM submodule includes:

* **`UriTemplate`** inherits from UriTempl and adds the method to create URL object. It also implements abstract
method to create JVM-appropriate implementation of Expression abstract class
* **`ExpressionJvm`** inherits from Expression abstract class and implements JVM way to encode string for URL
* **`UriTemplateSpec`** -- unit tests for UriTemplate class checking all samples from the standard
* **`UriTemplatetCombineSpec`** -- unit tests not based on the standard examples

### JS-specific code

JS submodule includes:

* **`UriTemplate`** inherits from UriTempl. It also implements abstract method to create
JS-appropriate implementation of Placeholder abstract class
* **`ExpressionJS`** inherits from Expression abstract class and implements JS way to encode string for URL

## Improvements

To suggest improvements please create Pull Requests with your code changes in GitHub project (https://github.com/C06A/KUrlet/pulls).

Follow are areas suggestions would be most appreciated for:

* Unit tests for Javascript implementation. Just copy test cases from UriTemplateSpec class
* Include Root-level module's source directory into each of submodule to avoid copying files
