package com.helpchoice.kotlin.urlet.impl

import io.kotlintest.matchers.shouldBe
import io.kotlintest.properties.forAll
import io.kotlintest.properties.headers
import io.kotlintest.properties.row
import io.kotlintest.properties.table
import io.kotlintest.specs.WordSpec

class UriTemplatetCombineSpec : WordSpec({
    val variables = mapOf<String, Any?>(
            "scheme" to "http"
            , "uid" to "user's ID"
            , "subDomain" to "sub"
            , "domains" to listOf("sub.domain", "server")
            , "IP4" to listOf(192, 168, "001", 1)
            , "tld" to "com"
            , "port" to 8080
            , "singlePath" to "path"
            , "multiPath" to listOf("super", "sub/subPath")
            , "singleParam" to "query"
            , "mapParam" to mapOf("simple" to "sim-ple", "nameOnly" to null)
            , "fragment" to "Name of Fragment"
            , "queryMap" to mapOf("q1" to "v1", "q2" to "v 2")
            , "queryList" to listOf("l1", "l 2")
            , "query" to "value"
    )

    "Complex tests" should {
        "include all elements" {
            UriTemplate("""
            |{scheme}://
            |{uid}@
            |{subDomain}{.domains*,tld}
            |:{port}
            |{/singlePath}{/multiPath*,singlePath}
            |{;singleParam}{;mapParam*,singleParam}
            |{#fragment}
            |{?queryList,query,queryMap*}
            |{&queryList,query,queryMap*}
            |""".trimMargin().replace("\n", "")).expand(variables) shouldBe
                    """
                    http://user%27s%20ID@sub.sub.domain.server.com:8080
                    /path/super/sub%2FsubPath/path;singleParam=query;simple=sim-ple;nameOnly=null;singleParam=query
                    #Name%20of%20Fragment
                    ?queryList=l1,l%202&query=value&q1=v1&q2=v%202&queryList=l1,l%202&query=value&q1=v1&q2=v%202
                    """.trimIndent().replace("\n", "")
        }

        "present IP4 address" {
            UriTemplate("{scheme}://{subDomain}{.IP4*}").expand(variables) shouldBe "http://sub.192.168.001.1"

        }
    }

    "Collecting names" should {
        "extract placeholders' list" {
            UriTemplate("""
            |{scheme}://
            |{uid}@
            |{subDomain}{.domains*,tld}
            |:{port}
            |{/singlePath}{/multiPath*,singlePath}
            |{;singleParam}{;mapParam*,singleParam}
            |{#fragment}
            |{?queryList,query,queryMap*}
            |{&queryList,query,queryMap*}
            |""".trimMargin().replace("\n", "")).names() shouldBe
                    listOf("scheme", "uid", "subDomain", "domains", "tld", "port"
                            , "singlePath", "multiPath", "singleParam", "mapParam", "fragment"
                            , "queryList", "query", "queryMap")
        }
    }

    "using vararg instead of map" should {
        "Simple string expansion" {
            val level1 = table(
                    headers("Expression", "Expansion")
                    , row("{var}", "value")
                    , row("{hello}", "Hello%20World%21")
                    , row("http://{var}{.domain*}{/path*}{?var}", "http://value.dom.tld/folder/file?var=value")
            )
            forAll(level1) { expression, expansion ->
                UriTemplate(expression).expand(
                        "var" to "value"
                        , "hello" to "Hello World!"
                        , "domain" to listOf("dom", "tld")
                        , "path" to listOf("folder", "file")
                ) shouldBe expansion
            }
        }
    }
})
