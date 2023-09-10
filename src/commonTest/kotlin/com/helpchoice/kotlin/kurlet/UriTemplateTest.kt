package com.helpchoice.kotlin.kurlet

import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

class UrlTemplateParsingTest {
    private fun parse(template: String): List<String> = UriTemplate(template).names().toList()

    @Test
    fun value() = assertEquals(listOf("var"), parse("{var}"))

    @Test
    fun valuePlus() = assertEquals(listOf("var"), parse("{+var}"))

    @Test
    fun valuePound() = assertEquals(listOf("var"), parse("{#var}"))

    @Test
    fun valueDot() = assertEquals(listOf("var"), parse("{.var}"))

    @Test
    fun valueSlash() = assertEquals(listOf("var"), parse("{/var}"))

    @Test
    fun valueSemi() = assertEquals(listOf("var"), parse("{;var}"))

    @Test
    fun valueQuestion() = assertEquals(listOf("var"), parse("{?var}"))

    @Test
    fun valueAmpersent() = assertEquals(listOf("var"), parse("{&var}"))

    @Test
    fun valueList() = assertEquals(listOf("var", "hello"), parse("{var,hello}"))

    @Test
    fun valueListPlus() = assertEquals(listOf("var", "hello"), parse("{+var,hello}"))

    @Test
    fun valueListPound() = assertEquals(listOf("var", "hello"), parse("{#var,hello}"))

    @Test
    fun valueListDot() = assertEquals(listOf("var", "hello"), parse("{.var,hello}"))

    @Test
    fun valueListSlash() = assertEquals(listOf("var", "hello"), parse("{/var,hello}"))

    @Test
    fun valueListSemi() = assertEquals(listOf("var", "hello"), parse("{;var,hello}"))

    @Test
    fun valueListQuestion() = assertEquals(listOf("var", "hello"), parse("{?var,hello}"))

    @Test
    fun valueListAmpersent() = assertEquals(listOf("var", "hello"), parse("{&var,hello}"))

    @Test
    fun valueModified() = assertEquals(
        listOf("var", "limited", "expended", "both"),
        parse("{var}{limited:3}{expended*}{both*:5}")
    )

    @Test
    fun valueModifiedList() = assertEquals(
        listOf("var", "limited", "expended", "both"),
        parse("{var,limited:3,expended*,both*:5}")
    )

    @Test
    fun valuePlusModified() = assertEquals(
        listOf("var", "limited", "expended", "both"),
        parse("{+var}{+limited:3}{+expended*}{+both*:5}")
    )

    @Test
    fun valuePlusModifiedList() = assertEquals(
        listOf("var", "limited", "expended", "both"),
        parse("{+var,limited:3,expended*,both*:5}")
    )

    @Test
    fun valuePoundModified() = assertEquals(
        listOf("var", "limited", "expended", "both"),
        parse("{#var}{#limited:3}{#expended*}{#both*:5}")
    )

    @Test
    fun valuePoundModifiedList() = assertEquals(
        listOf("var", "limited", "expended", "both"),
        parse("{#var,limited:3,expended*,both*:5}")
    )

    @Test
    fun valueDotModified() = assertEquals(
        listOf("var", "limited", "expended", "both"),
        parse("{.var}{.limited:3}{.expended*}{.both*:5}")
    )

    @Test
    fun valueDotModifiedList() = assertEquals(
        listOf("var", "limited", "expended", "both"),
        parse("{.var,limited:3,expended*,both*:5}")
    )

    @Test
    fun valueSlashModified() = assertEquals(
        listOf("var", "limited", "expended", "both"),
        parse("{/var}{/limited:3}{/expended*}{/both*:5}")
    )

    @Test
    fun valueSlashModifiedList() = assertEquals(
        listOf("var", "limited", "expended", "both"),
        parse("{/var,limited:3,expended*,both*:5}")
    )

    @Test
    fun valueSemiModified() = assertEquals(
        listOf("var", "limited", "expended", "both"),
        parse("{;var}{;limited:3}{;expended*}{;both*:5}")
    )

    @Test
    fun valueSemiModifiedList() = assertEquals(
        listOf("var", "limited", "expended", "both"),
        parse("{;var,limited:3,expended*,both*:5}")
    )

    @Test
    fun valueQuestionModified() = assertEquals(
        listOf("var", "limited", "expended", "both"),
        parse("{?var}{?limited:3}{?expended*}{?both*:5}")
    )

    @Test
    fun valueQuestionModifiedList() = assertEquals(
        listOf("var", "limited", "expended", "both"),
        parse("{?var,limited:3,expended*,both*:5}")
    )

    @Test
    fun valueAmpersentModified() = assertEquals(
        listOf("var", "limited", "expended", "both"),
        parse("{&var}{&limited:3}{&expended*}{&both*:5}")
    )

    @Test
    fun valueAmpersentModifiedList() = assertEquals(
        listOf("var", "limited", "expended", "both"),
        parse("{&var,limited:3,expended*,both*:5}")
    )

    @Test
    fun wholeURL() = assertEquals(
        listOf("domain", "path", "param1", "param2", "fragment"),
        parse("http://www{.domain}/applet{/path}{?param1}&flag{&param2}&val=value{#fragment}")
    )
}

class UriTemplateTest {
    class UriTemplateLevel1Test {
        @Test
        fun value() = UriTemplateLevels.LEVEL1.test("{var}" to "value")

        @Test
        fun HelloWorld() = UriTemplateLevels.LEVEL1.test("{hello}" to "Hello%20World%21")

        @Test
        fun reserved() = UriTemplateLevels.LEVEL1.test(
            "{mix}" to "colon%3Aslash%2Fquestion%3Fampersent%26pound%23semi%3Bdot.cyr%D0%90%D0%91%D0%92equal%3D"
        )
    }

    class UriTemplateLevel2Test {
        @Test
        fun level2_1() = UriTemplateLevels.LEVEL2.test("{+var}" to "value")

        @Test
        fun level2_2() = UriTemplateLevels.LEVEL2.test("{+hello}" to "Hello%20World!")

        @Test
        fun level2_3() = UriTemplateLevels.LEVEL2.test("{+path}/here" to "/foo/bar/here")

        @Test
        fun level2_4() = UriTemplateLevels.LEVEL2.test("here?ref={+path}" to "here?ref=/foo/bar")

        @Test
        fun level2_5() = UriTemplateLevels.LEVEL2.test("X{#var}" to "X#value")

        @Test
        fun level2_6() = UriTemplateLevels.LEVEL2.test("X{#hello}" to "X#Hello%20World!")

        @Test
        fun reservedPlus() =
            UriTemplateLevels.LEVEL1.test(
                "{+mix}" to "colon:slash/question?ampersent&pound#semi;dot.cyr%D0%90%D0%91%D0%92equal="
            )

        @Test
        fun reservedPound() =
            UriTemplateLevels.LEVEL1.test(
                "{#mix}" to "#colon:slash/question?ampersent&pound#semi;dot.cyr%D0%90%D0%91%D0%92equal="
            )
    }

    class UriTemplateLevel3Test {
        @Test
        fun level3_1() = UriTemplateLevels.LEVEL3.test("map?{x,y}" to "map?1024,768")

        @Test
        fun level3_2() = UriTemplateLevels.LEVEL3.test("{x,hello,y}" to "1024,Hello%20World%21,768")

        @Test
        fun level3_3() = UriTemplateLevels.LEVEL3.test("{+x,hello,y}" to "1024,Hello%20World!,768")

        @Test
        fun level3_4() = UriTemplateLevels.LEVEL3.test("{+path,x}/here" to "/foo/bar,1024/here")

        @Test
        fun level3_5() = UriTemplateLevels.LEVEL3.test("{#x,hello,y}" to "#1024,Hello%20World!,768")

        @Test
        fun level3_6() = UriTemplateLevels.LEVEL3.test("{#path,x}/here" to "#/foo/bar,1024/here")

        @Test
        fun level3_7() = UriTemplateLevels.LEVEL3.test("X{.var}" to "X.value")

        @Test
        fun level3_8() = UriTemplateLevels.LEVEL3.test("X{.x,y}" to "X.1024.768")

        @Test
        fun level3_9() = UriTemplateLevels.LEVEL3.test("{/var}" to "/value")

        @Test
        fun level3_10() = UriTemplateLevels.LEVEL3.test("{/var,x}/here" to "/value/1024/here")

        @Test
        fun level3_11() = UriTemplateLevels.LEVEL3.test("{;x,y}" to ";x=1024;y=768")

        @Test
        fun level3_12() = UriTemplateLevels.LEVEL3.test("{;x,y,empty}" to ";x=1024;y=768;empty")

        @Test
        fun level3_13() = UriTemplateLevels.LEVEL3.test("{?x,y}" to "?x=1024&y=768")

        @Test
        fun level3_14() = UriTemplateLevels.LEVEL3.test("{?x,y,empty}" to "?x=1024&y=768&empty=")

        @Test
        fun level3_15() = UriTemplateLevels.LEVEL3.test("?fixed=yes{&x}" to "?fixed=yes&x=1024")

        @Test
        fun level3_16() = UriTemplateLevels.LEVEL3.test("{&x,y,empty}" to "&x=1024&y=768&empty=")
    }

    class UriTemplateLevel4Test {
        @Test
        fun level4_1() = UriTemplateLevels.LEVEL4.test("{var:3}" to "val")

        @Test
        fun level4_2() = UriTemplateLevels.LEVEL4.test("{var:30}" to "value")

        @Test
        fun addition() = UriTemplateLevels.LEVEL4.test("{path}" to "%2Ffoo%2Fbar")

        @Test
        fun level4_3() = UriTemplateLevels.LEVEL4.test("{list}" to "red,green,blue")

        @Test
        fun level4_4() = UriTemplateLevels.LEVEL4.test("{list*}" to "red,green,blue")

        @Test
        fun level4_5() = UriTemplateLevels.LEVEL4.test("{keys}" to "semi,%3B,dot,.,comma,%2C")

        @Test
        fun level4_6() = UriTemplateLevels.LEVEL4.test("{keys*}" to "semi=%3B,dot=.,comma=%2C")

        @Test
        fun level4_7() = UriTemplateLevels.LEVEL4.test("{+path:6}/here" to "/foo/b/here")

        @Test
        fun level4_8() = UriTemplateLevels.LEVEL4.test("{+list}" to "red,green,blue")

        @Test
        fun level4_9() = UriTemplateLevels.LEVEL4.test("{+list*}" to "red,green,blue")

        @Test
        fun level4_10() = UriTemplateLevels.LEVEL4.test("{+keys}" to "semi,;,dot,.,comma,,")

        @Test
        fun level4_11() = UriTemplateLevels.LEVEL4.test("{+keys*}" to "semi=;,dot=.,comma=,")

        @Test
        fun level4_12() = UriTemplateLevels.LEVEL4.test("{#path:6}/here" to "#/foo/b/here")

        @Test
        fun level4_13() = UriTemplateLevels.LEVEL4.test("{#list}" to "#red,green,blue")

        @Test
        fun level4_14() = UriTemplateLevels.LEVEL4.test("{#list*}" to "#red,green,blue")

        @Test
        fun level4_15() = UriTemplateLevels.LEVEL4.test("{#keys}" to "#semi,;,dot,.,comma,,")

        @Test
        fun level4_16() = UriTemplateLevels.LEVEL4.test("{#keys*}" to "#semi=;,dot=.,comma=,")

        @Test
        fun level4_17() = UriTemplateLevels.LEVEL4.test("X{.var:3}" to "X.val")

        @Test
        fun level4_18() = UriTemplateLevels.LEVEL4.test("X{.list}" to "X.red,green,blue")

        @Test
        fun level4_19() = UriTemplateLevels.LEVEL4.test("X{.list*}" to "X.red.green.blue")

        @Test
        fun level4_20() = UriTemplateLevels.LEVEL4.test("X{.keys}" to "X.semi,%3B,dot,.,comma,%2C")

        @Test
        fun level4_21() = UriTemplateLevels.LEVEL4.test("{/var:1,var}" to "/v/value")

        @Test
        fun level4_22() = UriTemplateLevels.LEVEL4.test("{/list}" to "/red,green,blue")

        @Test
        fun level4_23() = UriTemplateLevels.LEVEL4.test("{/list*}" to "/red/green/blue")

        @Test
        fun level4_24() = UriTemplateLevels.LEVEL4.test("{/list*,path:4}" to "/red/green/blue/%2Ffoo")

        @Test
        fun level4_25() = UriTemplateLevels.LEVEL4.test("{/keys}" to "/semi,%3B,dot,.,comma,%2C")

        @Test
        fun level4_26() = UriTemplateLevels.LEVEL4.test("{/keys*}" to "/semi=%3B/dot=./comma=%2C")

        @Test
        fun level4_27() = UriTemplateLevels.LEVEL4.test("{;hello:5}" to ";hello=Hello")

        @Test
        fun level4_28() = UriTemplateLevels.LEVEL4.test("{;list}" to ";list=red,green,blue")

        @Test
        fun level4_29() = UriTemplateLevels.LEVEL4.test("{;list*}" to ";list=red;list=green;list=blue")

        @Test
        fun level4_30() = UriTemplateLevels.LEVEL4.test("{;keys}" to ";keys=semi,%3B,dot,.,comma,%2C")

        @Test
        fun level4_31() = UriTemplateLevels.LEVEL4.test("{;keys*}" to ";semi=%3B;dot=.;comma=%2C")

        @Test
        fun level4_32() = UriTemplateLevels.LEVEL4.test("{?var:3}" to "?var=val")

        @Test
        fun level4_33() = UriTemplateLevels.LEVEL4.test("{?list}" to "?list=red,green,blue")

        @Test
        fun level4_34() = UriTemplateLevels.LEVEL4.test("{?list*}" to "?list=red&list=green&list=blue")

        @Test
        fun level4_35() = UriTemplateLevels.LEVEL4.test("{?keys}" to "?keys=semi,%3B,dot,.,comma,%2C")

        @Test
        fun level4_36() = UriTemplateLevels.LEVEL4.test("{?keys*}" to "?semi=%3B&dot=.&comma=%2C")

        @Test
        fun level4_37() = UriTemplateLevels.LEVEL4.test("{&var:3}" to "&var=val")

        @Test
        fun level4_38() = UriTemplateLevels.LEVEL4.test("{&list}" to "&list=red,green,blue")

        @Test
        fun level4_39() = UriTemplateLevels.LEVEL4.test("{&list*}" to "&list=red&list=green&list=blue")

        @Test
        fun level4_40() = UriTemplateLevels.LEVEL4.test("{&keys}" to "&keys=semi,%3B,dot,.,comma,%2C")

        @Test
        fun level4_41() = UriTemplateLevels.LEVEL4.test("{&keys*}" to "&semi=%3B&dot=.&comma=%2C")
    }
}

class UriTemplateAdditionTest {
    @Test
    fun missingKey() = UriTemplateLevels.ADDITIONAL.test("{missing}" to "")

    @Test
    fun nullValues() =
        UriTemplateLevels.ADDITIONAL.test("{null},{+null},{#null},{/null},{?null},{&null},{;null}" to ",,,,,,")

    @Test
    fun reserved() =
        UriTemplateLevels.ADDITIONAL.test("{reserved}" to "%3A%2F%3F%23%5B%5D%40%21%24%26%27%28%29%2A%2B%2C%3B%3D")

    @Test
    fun addition_reservedPlus() = UriTemplateLevels.ADDITIONAL.test("{+reserved}" to ":/?#[]@!\$&'()*+,;=")

    @Test
    fun addition_reservedPound() = UriTemplateLevels.ADDITIONAL.test("{#reserved}" to "#:/?#[]@!\$&'()*+,;=")

    @Test
    fun addition_reservedSlesh() =
        UriTemplateLevels.ADDITIONAL.test("{/reserved}" to "/%3A%2F%3F%23%5B%5D%40%21%24%26%27%28%29%2A%2B%2C%3B%3D")

    @Test
    fun addition_reservedQuestion() =
        UriTemplateLevels.ADDITIONAL.test(
            "{?reserved}"
                    to "?reserved=%3A%2F%3F%23%5B%5D%40%21%24%26%27%28%29%2A%2B%2C%3B%3D"
        )

    @Test
    fun addition_reservedAmpersent() =
        UriTemplateLevels.ADDITIONAL.test(
            "{&reserved}"
                    to "&reserved=%3A%2F%3F%23%5B%5D%40%21%24%26%27%28%29%2A%2B%2C%3B%3D"
        )

    @Test
    fun addition_reservedSemicolon() =
        UriTemplateLevels.ADDITIONAL.test(
            "{;reserved}"
                    to ";reserved=%3A%2F%3F%23%5B%5D%40%21%24%26%27%28%29%2A%2B%2C%3B%3D"
        )

    @Test
    fun addition_cyrilic() =
        UriTemplateLevels.ADDITIONAL.test("{cyrillic}" to "%D0%90%D0%91%D0%92%D0%93%D0%94%D0%95%D0%B9%D0%BA%D0%B0")

    @Test
    fun addition_cyrilicAmpersent() =
        UriTemplateLevels.ADDITIONAL.test(
            "{&cyrillic}"
                    to "&cyrillic=%D0%90%D0%91%D0%92%D0%93%D0%94%D0%95%D0%B9%D0%BA%D0%B0"
        )

    @Test
    fun addition_cyrilicSub() =
        UriTemplateLevels.ADDITIONAL.test("{/cyrillic:6}" to "/%D0%90%D0%91%D0%92%D0%93%D0%94%D0%95")

    @Test
    fun addition_cyrilicPlusSub() =
        UriTemplateLevels.ADDITIONAL.test("{+cyrillic:6}" to "%D0%90%D0%91%D0%92%D0%93%D0%94%D0%95")

    @Test
    fun addition_tai() =
        UriTemplateLevels.ADDITIONAL.test(
            "{/tai}" to "/%E0%B8%A0%E0%B8%B2%E0%B8%A9%E0%B8%B2%E0%B9%84%E0%B8%97%E0%B8%A2"
        )

    @Test
    fun addition_taiSub() = UriTemplateLevels.ADDITIONAL.test("{?tai:2}" to "?tai=%E0%B8%A0%E0%B8%B2")

    @Test
    fun queryList() = UriTemplateLevels.ADDITIONAL.test(
        "{?var,keys*:3,list:2}"
                to "?var=value&semi=%3B&dot=.&comma=%2C&text=sam&list=re,gr,bl"
    )

    @Test
    fun prefixes() = UriTemplateLevels.ADDITIONAL.test(
        "{?var:3,keys:2,list:2}"
                to "?var=val&keys=semi,%3B,dot,.,comma,%2C,text,sa&list=re,gr,bl"
    )

    @Test
    fun explode() = UriTemplateLevels.ADDITIONAL.test(
        "{?var*,list*,keys*}"
                to "?var&value&list=red&list=green&list=blue&semi=%3B&dot=.&comma=%2C&text=sample"
    )

    @Test
    fun modifiers() = UriTemplateLevels.ADDITIONAL.test(
        "{?var*:3,list*:2,keys*:2}"
                to "?var&val&list=re&list=gr&list=bl&semi=%3B&dot=.&comma=%2C&text=sa"
    )

    @Test
    @Ignore
    fun modifiersOrger() = UriTemplateLevels.ADDITIONAL.test(
        "{?var:3*,list:2*,keys:2*}"
                to "?var=value&semi=%3B&dot=.&comma=%2C&text=sam&list=re,gr,bl"
    )

    @Test
    fun wholeURL() =
        UriTemplateLevels.ADDITIONAL.test(
            "http://www{.domain*}:{port}{/path*}/sub{?var,hello,missing}&flag{&list,keys*}&par=value{#fragment}"
                    to """
                        |http://www.sample.com:8080
                        |/foo/bar/sub
                        |?var=value&hello=Hello%20World%21&flag&list=red,green,blue&semi=%3B&dot=.&comma=%2C&text=sample&par=value
                        |#section
                        |""".trimMargin().replace("\n", "")
        )

    @Test
    @Ignore
    fun buildURN() =
        UriTemplateLevels.URN.test(
            "{urn}{:nid,nss}"
                    to "foo:bar:abc,defg"
        )

}

enum class UriTemplateLevels(val lable: String, val variables: Map<String, Any?>) {
    LEVEL1(
        "Level 1", mapOf(
            "var" to "value",
            "hello" to "Hello World!",
            "mix" to "colon:slash/question?ampersent&pound#semi;dot.cyrАБВequal=",
        )
    ),
    LEVEL2(
        "Level 2", mapOf(
            "var" to "value",
            "hello" to "Hello World!",
            "path" to "/foo/bar"
        )
    ),
    LEVEL3(
        "Level 3", mapOf(
            "var" to "value",
            "hello" to "Hello World!",
            "empty" to "",
            "path" to "/foo/bar",
            "x" to "1024",
            "y" to "768"
        )
    ),
    LEVEL4(
        "Level 4", mapOf(
            "var" to "value",
            "hello" to "Hello World!",
            "path" to "/foo/bar",
            "list" to listOf("red", "green", "blue"),
            "keys" to mapOf("semi" to ";", "dot" to ".", "comma" to ",")
        )
    ),
    ADDITIONAL(
        "Additional",
        mapOf(
            "null" to null,
            "reserved" to ":/?#[]@!$&'()*+,;=",
            "cyrillic" to "АБВГДЕйка",
            "tai" to "ภาษาไทย",
            "domain" to listOf("sample", "com"),
            "port" to 8080,
            "path" to listOf("foo", "bar"),
            "var" to "value",
            "list" to listOf("red", "green", "blue"),
            "hello" to "Hello World!",
            "keys" to mapOf("semi" to ";", "dot" to ".", "comma" to ",", "text" to "sample"),
            "fragment" to "section",
        ),
    ),
    URN("URN template",
        mapOf(
            "urn" to "foo",
            "nid" to "bar",
            "nss" to listOf("abc", "defg")
        )
    ),
    ;

    fun test(data: Pair<String, String>) {
        val (template, expected) = data
        assertEquals(
            expected, UriTemplate(template).expand(variables),
            "${lable} template $template"
        )
    }
}
