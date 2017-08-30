package com.helpchoice.kotlin.urlet.impl

import com.helpchoice.kotlin.urlet.Expression
import java.net.URLEncoder

/**
 * Holds a single substitution placeholder with leading literal in front of it
 *
 *
 */
class ExpressionJvm(prefix: String, placeholder: String?): Expression(prefix, placeholder) {
    override fun encode(str: String): String {
        return URLEncoder.encode(str, Charsets.UTF_8.name())
    }
}