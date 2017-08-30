package com.helpchoice.kotlin.urlet.impl

import com.helpchoice.kotlin.urlet.Expression

external fun encodeURIComponent(str: String): String

/**
 * Holds a single substitution placeholder with leading literal in front of it
 *
 *
 */
class PlaceholderJS(prefix: String, placeholder: String?): Expression(prefix, placeholder) {
    override fun encode(str: String): String {
        return encodeURIComponent(str)
    }
}

