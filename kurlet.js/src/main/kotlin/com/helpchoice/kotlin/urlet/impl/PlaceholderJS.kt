package com.helpchoice.kotlin.urlet.impl

import com.helpchoice.kotlin.urlet.Placeholder

external fun encodeURIComponent(str: String): String

/**
 * Holds a single substitution placeholder with leading literal in front of it
 *
 *
 */
class PlaceholderJS(prefix: String, placeholder: String?): Placeholder(prefix, placeholder) {
    override fun encode(str: String): String {
        return encodeURIComponent(str)
    }
}

