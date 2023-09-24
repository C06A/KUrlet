package com.helpchoice.java;

import java.util.HashMap;
import java.util.LinkedList;

import com.helpchoice.kotlin.kurlet.UriTemplate;

public class Main {
    public static void main(String[] args) {
        UriTemplate template = new UriTemplate("http://www{.domain}{/path}{?var:5,col,map}{&var*:2,col:1*,map*");
        System.out.println(template.expand(
                new HashMap<String, Object>() {{
                    put("domain", new LinkedList() {{
                        add("localhost");
                        add("com");
                    }});
                    put("path", new LinkedList<String>() {{
                        add("path");
                        add("subpath");
                    }});
                    put("ext", "xml");
                    put("var", "variable");
                    put("col", new LinkedList<String>() {{
                        add("c1");
                        add("c2");
                    }});
                    put("map", new HashMap<String, String>() {{
                        put("m1", "v1");
                        put("m2", "v2");
                    }});
                    put("frag", "anker");
                }}
        ));
    }
}
