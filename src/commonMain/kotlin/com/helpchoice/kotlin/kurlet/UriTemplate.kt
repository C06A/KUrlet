package com.helpchoice.kotlin.kurlet

/**
 * Implements the substitution into UriTemplate according to https://tools.ietf.org/html/rfc6570
 * <p/>
 * This abstract class get implememnted separetely for JVM and JS
 */
class UriTemplate(private val template: String) {
    private val placeholders: MutableCollection<Expression> = mutableListOf()

    init {
        template.split('}').filter { it.isNotEmpty() }.forEach {
                val prefVar = it.split('{')
            if(prefVar.size > 1) {
                placeholders += Expression(prefVar[0], prefVar[1])
            } else {
                placeholders += Expression(prefVar[0], null)
            }
        }
    }

    override fun toString(): String {
        return template
    }

    fun expand(with: Map<String, Any?>): String {
        val out = StringBuilder()
        placeholders.forEach {
            it.appendTo(out, with)
        }
        return out.toString()
    }

    fun expand(vararg with: Pair<String, Any?>): String {
        return expand(mapOf(*with))
    }

    fun names(): Iterable<String> {
        return placeholders.flatMap { holder: Expression ->
            holder.getNames()
        }.distinct()
    }
}
