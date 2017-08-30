package com.helpchoice.kotlin.urlet.impl

import com.helpchoice.kotlin.urlet.Placeholder
import com.helpchoice.kotlin.urlet.UriTempl


/**
 * Implements the substitution into UriTemplate according to https://tools.ietf.org/html/rfc6570
 *
 */
class UriTemplate(template: String): UriTempl(template) {

    override fun makePlaceholder(prefix: String, holder: String?): Placeholder {
        return PlaceholderJS(prefix, holder)
    }
}
