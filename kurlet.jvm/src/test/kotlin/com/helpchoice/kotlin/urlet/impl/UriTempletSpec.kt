package com.helpchoice.kotlin.urlet.impl

import com.helpchoice.kotlin.urlet.impl.UriTemplate
import io.kotlintest.matchers.shouldBe
import io.kotlintest.properties.forAll
import io.kotlintest.properties.headers
import io.kotlintest.properties.row
import io.kotlintest.properties.table
import io.kotlintest.specs.WordSpec

class UriTemplatetSpec : WordSpec({
    "Level 1" should {
        val variables = mapOf<String, Any?>(
                "var" to "value"
                , "hello" to "Hello World!"
        )
        "Simple string expansion" {
            val level1 = table(
                    headers("Expression", "Expansion")
                    , row("{var}", "value")
                    , row("{hello}", "Hello%20World%21")
            )
            forAll(level1) { expression, expansion ->
                UriTemplate(expression).expand(variables) shouldBe expansion
            }
        }
    }

    "Level 2" should {
        val variables = mapOf<String, Any?>(
                "var" to "value"
                , "hello" to "Hello World!"
                , "path" to "/foo/bar"
        )
        "Reserved string expansion" {
            val level2 = table(
                    headers("Expression", "Expansion")
                    , row("{+var}", "value")
                    , row("{+hello}", "Hello%20World!")
                    , row("{+path}/here", "/foo/bar/here")
                    , row("here?ref={+path}", "here?ref=/foo/bar")
            )
            forAll(level2) { expression, expansion ->
                UriTemplate(expression).expand(variables) shouldBe expansion
            }
        }
        "Fragment expansion, crosshatch-prefixed" {
            val level2 = table(
                    headers("Expression", "Expansion")
                    , row("X{#var}", "X#value")
                    , row("X{#hello}", "X#Hello%20World!")
            )
            forAll(level2) { expression, expansion ->
                UriTemplate(expression).expand(variables) shouldBe expansion
            }
        }
    }

    "Level 3" should {
        val variables = mapOf<String, Any?>(
                "var" to "value"
                , "hello" to "Hello World!"
                , "path" to "/foo/bar"
                , "empty" to ""
                , "x" to "1024"
                , "y" to "768"
        )
        "String expansion with multiple variables" {
            val level3 = table(
                    headers("Expression", "Expansion")
                    , row("map?{x,y}", "map?1024,768")
                    , row("{x,hello,y}", "1024,Hello%20World%21,768")
            )
            forAll(level3) { expression, expansion ->
                UriTemplate(expression).expand(variables) shouldBe expansion
            }
        }
        "Reserved expansion with multiple variables" {
            val level3 = table(
                    headers("Expression", "Expansion")
                    , row("{+x,hello,y}", "1024,Hello%20World!,768")
                    , row("{+path,x}/here", "/foo/bar,1024/here")
            )
            forAll(level3) { expression, expansion ->
                UriTemplate(expression).expand(variables) shouldBe expansion
            }
        }
        "Fragment expansion with multiple variables" {
            val level3 = table(
                    headers("Expression", "Expansion")
                    , row("{#x,hello,y}", "#1024,Hello%20World!,768")
                    , row("{#path,x}/here", "#/foo/bar,1024/here")
            )
            forAll(level3) { expression, expansion ->
                UriTemplate(expression).expand(variables) shouldBe expansion
            }
        }
        "Label expansion, dot-prefixed" {
            val level3 = table(
                    headers("Expression", "Expansion")
                    , row("X{.var}", "X.value")
                    , row("X{.x,y}", "X.1024.768")
            )
            forAll(level3) { expression, expansion ->
                UriTemplate(expression).expand(variables) shouldBe expansion
            }
        }
        "Path segments, slash-prefixed" {
            val level3 = table(
                    headers("Expression", "Expansion")
                    , row("{/var}", "/value")
                    , row("{/var,x}/here", "/value/1024/here")
            )
            forAll(level3) { expression, expansion ->
                UriTemplate(expression).expand(variables) shouldBe expansion
            }
        }
        "Path-style parameters, semicolon-prefixed" {
            val level3 = table(
                    headers("Expression", "Expansion")
                    , row("{;x,y}", ";x=1024;y=768")
                    , row("{;x,y,empty}", ";x=1024;y=768;empty")
            )
            forAll(level3) { expression, expansion ->
                UriTemplate(expression).expand(variables) shouldBe expansion
            }
        }
        "Form-style query, ampersand-separated" {
            val level3 = table(
                    headers("Expression", "Expansion")
                    , row("{?x,y}", "?x=1024&y=768")
                    , row("{?x,y,empty}", "?x=1024&y=768&empty=")
            )
            forAll(level3) { expression, expansion ->
                UriTemplate(expression).expand(variables) shouldBe expansion
            }
        }
        "Form-style query continuation" {
            val level3 = table(
                    headers("Expression", "Expansion")
                    , row("?fixed=yes{&x}", "?fixed=yes&x=1024")
                    , row("{&x,y,empty}", "&x=1024&y=768&empty=")
            )
            forAll(level3) { expression, expansion ->
                UriTemplate(expression).expand(variables) shouldBe expansion
            }
        }
    }

    "Level 4" should {
        val variables = mapOf<String, Any?>(
                "var" to "value"
                , "hello" to "Hello World!"
                , "path" to "/foo/bar"
                , "list" to listOf("red", "green", "blue")
                , "keys" to mapOf("semi" to ";", "dot" to ".", "comma" to ",")
        )
        "String expansion with value modifiers" {
            val level4 = table(
                    headers("Expression", "Expansion")
                    , row("{var:3}", "val")
                    , row("{var:30}", "value")
                    , row("{list}", "red,green,blue")
                    , row("{list*}", "red,green,blue")
                    , row("{keys}", "semi,%3B,dot,.,comma,%2C")
                    , row("{keys*}", "semi=%3B,dot=.,comma=%2C")
            )
            forAll(level4) { expression, expansion ->
                UriTemplate(expression).expand(variables) shouldBe expansion
            }
        }
        "Reserved expansion with value modifiers" {
            val level4 = table(
                    headers("Expression", "Expansion")
                    , row("{+path:6}/here", "/foo/b/here")
                    , row("{+list}", "red,green,blue")
                    , row("{+list*}", "red,green,blue")
                    , row("{+keys}", "semi,;,dot,.,comma,,")
                    , row("{+keys*}", "semi=;,dot=.,comma=,")
            )
            forAll(level4) { expression, expansion ->
                UriTemplate(expression).expand(variables) shouldBe expansion
            }
        }
        "Fragment expansion with value modifiers" {
            val level4 = table(
                    headers("Expression", "Expansion")
                    , row("{#path:6}/here", "#/foo/b/here")
                    , row("{#list}", "#red,green,blue")
                    , row("{#list*}", "#red,green,blue")
                    , row("{#keys}", "#semi,;,dot,.,comma,,")
                    , row("{#keys*}", "#semi=;,dot=.,comma=,")
            )
            forAll(level4) { expression, expansion ->
                UriTemplate(expression).expand(variables) shouldBe expansion
            }
        }
        "Label expansion, dot-prefixed" {
            val level4 = table(
                    headers("Expression", "Expansion")
                    , row("X{.var:3}", "X.val")
                    , row("X{.list}", "X.red,green,blue")
                    , row("X{.list*}", "X.red.green.blue")
                    , row("X{.keys}", "X.semi,%3B,dot,.,comma,%2C")
                    , row("X{.keys*}", "X.semi=%3B.dot=..comma=%2C")
            )
            forAll(level4) { expression, expansion ->
                UriTemplate(expression).expand(variables) shouldBe expansion
            }
        }
        "Path segments, slash-prefixed" {
            val level4 = table(
                    headers("Expression", "Expansion")
                    , row("{/var:1,var}", "/v/value")
                    , row("{/list}", "/red,green,blue")
                    , row("{/list*}", "/red/green/blue")
                    , row("{/list*,path:4}", "/red/green/blue/%2Ffoo")
                    , row("{/keys}", "/semi,%3B,dot,.,comma,%2C")
                    , row("{/keys*}", "/semi=%3B/dot=./comma=%2C")
            )
            forAll(level4) { expression, expansion ->
                UriTemplate(expression).expand(variables) shouldBe expansion
            }
        }
        "Path-style parameters, semicolon-prefixed" {
            val level4 = table(
                    headers("Expression", "Expansion")
                    , row("{;hello:5}", ";hello=Hello")
                    , row("{;list}", ";list=red,green,blue")
                    , row("{;list*}", ";list=red;list=green;list=blue")
                    , row("{;keys}", ";keys=semi,%3B,dot,.,comma,%2C")
                    , row("{;keys*}", ";semi=%3B;dot=.;comma=%2C")
            )
            forAll(level4) { expression, expansion ->
                UriTemplate(expression).expand(variables) shouldBe expansion
            }
        }
        "Form-style query, ampersand-separated" {
            val level4 = table(
                    headers("Expression", "Expansion")
                    , row("{?var:3}", "?var=val")
                    , row("{?list}", "?list=red,green,blue")
                    , row("{?list*}", "?list=red&list=green&list=blue")
                    , row("{?keys}", "?keys=semi,%3B,dot,.,comma,%2C")
                    , row("{?keys*}", "?semi=%3B&dot=.&comma=%2C")
            )
            forAll(level4) { expression, expansion ->
                UriTemplate(expression).expand(variables) shouldBe expansion
            }
        }
        "Form-style query continuation" {
            val level4 = table(
                    headers("Expression", "Expansion")
                    , row("{&var:3}", "&var=val")
                    , row("{&list}", "&list=red,green,blue")
                    , row("{&list*}", "&list=red&list=green&list=blue")
                    , row("{&keys}", "&keys=semi,%3B,dot,.,comma,%2C")
                    , row("{&keys*}", "&semi=%3B&dot=.&comma=%2C")
                    , row("", "")
            )
            forAll(level4) { expression, expansion ->
                UriTemplate(expression).expand(variables) shouldBe expansion
            }
        }
    }

    "Overview" {
        val tests = table(
                headers("Template", "Expansion", "With")
                , row("http://example.com/~{username}/", "http://example.com/~fred/", mapOf<String, Any?>("username" to "fred"))
                , row("http://example.com/~{username}/", "http://example.com/~mark/", mapOf<String, Any?>("username" to "mark"))
                , row("http://example.com/dictionary/{term:1}/{term}", "http://example.com/dictionary/c/cat", mapOf<String, Any?>("term" to "cat"))
                , row("http://example.com/dictionary/{term:1}/{term}", "http://example.com/dictionary/d/dog", mapOf<String, Any?>("term" to "dog"))
                , row("http://example.com/search{?q,lang}", "http://example.com/search?q=cat&lang=en", mapOf<String, Any?>("q" to "cat", "lang" to "en"))
                , row("http://example.com/search{?q,lang}", "http://example.com/search?q=chien&lang=fr", mapOf<String, Any?>("q" to "chien", "lang" to "fr"))
                , row("http://www.example.com/foo{?query,number}", "http://www.example.com/foo?query=mycelium&number=100", mapOf<String, Any?>("query" to "mycelium", "number" to 100))
                , row("http://www.example.com/foo{?query,number}", "http://www.example.com/foo?number=100", mapOf<String, Any?>("number" to 100))
                , row("http://www.example.com/foo{?query,number}", "http://www.example.com/foo", mapOf<String, Any?>())
        )

        forAll(tests) { template: String, expansion: String, with: Map<String, Any?> ->
            UriTemplate(template).expand(with) shouldBe expansion
        }
    }

    "Composite Values" {
        val tests = table(
                headers("Template", "Expansion", "With")
                , row("/mapper{?address*}", "/mapper?city=Newport%20Beach&state=CA", mapOf<String, Any?>("address" to mapOf("city" to "Newport Beach", "state" to "CA")))
                , row("find{?year*}", "find?year=1965&year=2000&year=2012", mapOf<String, Any?>("year" to listOf("1965", 2000, "2012")))
                , row("www{.dom*}", "www.example.com", mapOf<String, Any?>("dom" to listOf("example", "com")))
        )

        forAll(tests) { template: String, expansion: String, with: Map<String, Any?> ->
            UriTemplate(template).expand(with) shouldBe expansion
        }

    }
})