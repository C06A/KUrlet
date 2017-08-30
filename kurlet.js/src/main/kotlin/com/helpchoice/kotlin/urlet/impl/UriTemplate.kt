package com.helpchoice.kotlin.urlet


/**
 * Implements the substitution into UriTemplate according to https://tools.ietf.org/html/rfc6570
 *
 */
class UriTemplate(template: String): UriTempl(template) {

    override fun makePlaceholder(prefix: String, holder: String?): Placeholder {
        return PlaceholderJS(prefix, holder)
    }
}
