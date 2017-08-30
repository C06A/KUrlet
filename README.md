# KUrilet -- Kotlin URI tempLET

This project includes the code to work with URI Template standard (https://tools.ietf.org/html/rfc6570)
implemented in Kotlin.

## Project structure

The project is built on Gradle and includes 2 submodules. Root-level module includes the main implementation code
shared between JVM and JS targets. Each submodule includes the target-specific code. The common code get copied
into submodule before build.

### Shared code

Root-level module includes 2 abstract classes:

* UriTempl -- represents the UriTemplate object and encloses code to split template into literals and placeholders
* Placeholder -- represents the string literal followed by placeholder (part of the template in the { } )

Both classes are abstract and implemented by corresponded classes in each submodule.

### JVM-specific code

JVM submodule includes:

* UriTemplate inherits from UriTempl and adds the method to create URL object. It also implements abstract
method to create JVM-appropriate implementation of Placeholder abstract class
* PlaceholderJVM inherits from Placeholder abstract class and implements JVM way to encode string for URL
* UriTemplateSpec -- unit tests for UriTemplate class

### JS-specific code

JS submodule includes:

* UriTemplate inherits from UriTempl. It also implements abstract method to create
JS-appropriate implementation of Placeholder abstract class
* PlaceholderJS inherits from Placeholder abstract class and implements JS way to encode string for URL

## Improvements

To suggest improvements please create Pull Requests in GitHub project.

Follow are areas suggestions would be most appreciated:

* Unit tests for Javascript implementation. Just copy test cases from UriTemplateSpec class
* Include Root-level module's source directory into each of submodule to avoid copying files
