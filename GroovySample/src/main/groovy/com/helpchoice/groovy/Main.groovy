package com.helpchoice.groovy

import com.helpchoice.kotlin.kurlet.UriTemplate

class Main {
    static void main(String[] args) {
        UriTemplate template = new UriTemplate("http://www{.domain}{/path}{?var:5,col,map}{&var*:2,col:1*,map*")
        println(template.expand(
                [
                        "domain": ["localhost", "com"],
                        "path"  : ["path", "subpath"],
                        "ext"   : "xml",
                        "var"   : "variable",
                        "col"   : ["c1", "c2"],
                        "map"   : [
                                "m1": "v1",
                                "m2": "v2",
                        ],
                        "frag"  : "anker",
                ],
        ))
    }
}
