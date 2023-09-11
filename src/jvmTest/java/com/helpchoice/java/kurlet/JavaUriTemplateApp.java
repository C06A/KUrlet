package com.helpchoice.java.kurlet;

import com.github.hal4j.uritemplate.URITemplate;
import com.helpchoice.kotlin.kurlet.UriTemplate;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JavaUriTemplateApp {
    public static void main(String[] args) {
        new JavaUriTemplateApp().building_URL();
    }

    void building_URL() {
        test("http://www{.domain}/servlet{/path*}{.ext}{?var,col*,map*}{&var,col,map}{#frag}",
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
                }},
                "http://www.localhost,com/servlet/path/subpath.xml"
                        + "?var=variable&col=c1&col=c2&m1=v1&m2=v2&var=variable&col=c1,c2&map=m1,v1,m2,v2#anker");
    }

    void test(String template, Map<String, Object> data, String expected) {
        String result = new UriTemplate(template).expand(data);

        if(expected.equals(result)) {
            System.out.println("Correct result:" + result);
        } else {
            System.out.println("Invalid result:" + result);
            System.out.println("Expecte result:" + expected);
        }
    }
}
