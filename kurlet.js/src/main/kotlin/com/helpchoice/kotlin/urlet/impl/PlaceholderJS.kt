package com.helpchoice.kotlin.urlet

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

