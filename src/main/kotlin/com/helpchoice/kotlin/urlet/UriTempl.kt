package com.helpchoice.kotlin.urlet

/**
 * Implements the substitution into UriTemplate according to https://tools.ietf.org/html/rfc6570
 * <p/>
 * This abstract class get implememnted separetely for JVM and JS
 */
abstract class UriTempl(private val template: String) {
    private val placeholders: MutableCollection<Placeholder> = mutableListOf()

    init {
        template.split('}').filter { it.isNotEmpty() }.forEach {
                val prefVar = it.split('{')
            if(prefVar.size > 1) {
                placeholders += makePlaceholder(prefVar[0], prefVar[1])
            } else {
                placeholders += makePlaceholder(prefVar[0], null)
            }
        }
    }

    override fun toString(): String {
        return template
    }

    fun toString(with: Map<String, Any?>): String {
        val out = StringBuilder()
        placeholders.forEach {
            it.appendTo(out, with)
        }
        return out.toString()
    }

    abstract fun makePlaceholder(prefix: String, holder: String?): Placeholder
}
