package com.helpchoice.kotlin.urlet.impl

import com.helpchoice.kotlin.urlet.Placeholder
import com.helpchoice.kotlin.urlet.UriTempl
import java.net.URL

/**
 * Implements the substitution into UriTemplate according to https://tools.ietf.org/html/rfc6570
 *
 */
class UriTemplate(template: String): UriTempl(template) {

    fun toURL(with: Map<String, Any?>): URL {
        return URL(toString(with))
    }

    override fun makePlaceholder(prefix: String, holder: String?): Placeholder {
        return PlaceholderJvm(prefix, holder)
    }
}
