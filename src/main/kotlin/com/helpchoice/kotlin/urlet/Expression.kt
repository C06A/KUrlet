package com.helpchoice.kotlin.urlet

/**
 * Holds a single substitution placeholder with leading literal in front of it
 *
 *
 */
abstract class Expression(private val prefix: String, placeholder: String?) {
    val type: Char?
    var names: Collection<Triple<String, Int?, Char?>> = listOf()

    init {
        if (placeholder == null) {
            type = null
        } else {
            var holder = placeholder
            if (holder[0] in "/?&@+#;.") {
                type = holder[0]
                holder = placeholder.substring(1)
            } else {
                type = null
            }

            holder.split(',').map { variable: String ->
                val multiplier = if (variable.last() == '*') '*' else null
                var name = variable
                multiplier?.let {
                    name = variable.dropLast(1)
                }
                val splits = name.split(':')
                if (splits.size > 1) {
                    names += Triple(splits[0], splits[1].toInt(), multiplier)
                } else {
                    names += Triple(splits[0], null, multiplier)
                }
            }
        }
    }

    fun appendTo(buffer: StringBuilder, with: Map<String, Any?>) {
        buffer.append(prefix)
        var separator = """${type ?: ""}"""
        var preparator = separator

        when (type) {
            null -> {
                separator = ","
                preparator = ""
            }
            '?' -> {
                separator = "&"
                preparator = if (buffer.toString().contains('?')) "&" else "?"
            }
            '+' -> {
                separator = ","
                preparator = ""
            }
            '#' -> {
                separator = ","
            }
        }

        for (varDeclaration in names) {
            val variable = with[varDeclaration.first]

            fun incode(value: String, limit: Int?): String {
                var str = value
                str = str?.substring(0, minOf(str.length, limit ?: str.length))
                str = if (type == null || !"+#".contains(type)) {
                    encode(str).replace("+", "%20")
                } else {
                    str.replace(" ", "%20")
                }
                return str
            }

            when {
                variable is Collection<*> -> {
                    if (variable.isNotEmpty()) {
                        var prefix = "$type"
                        var infix = separator
                        when (type) {
                            null, '+' -> {
                                prefix = ""
                                infix = ","
                            }
                            '#' -> {
                                infix = ","
                            }
                            ';', '&' -> {
                                prefix = "$type${varDeclaration.first}="
                                infix = prefix
                            }
                            '?' -> {
                                prefix = "?${varDeclaration.first}="
                                infix = "&${varDeclaration.first}="
                            }
                            else -> {
                                infix = prefix
                            }
                        }
                        if (varDeclaration.third != '*') {
                            infix = ","
                        }

                        variable.mapNotNull {
                            incode(it.toString(), varDeclaration.second)
                        }.joinTo(buffer, infix, prefix)
                    }
                }
                variable is Map<*, *> -> {
                    if (variable.isNotEmpty()) {
                        val mapped = variable.mapNotNull {
                            val str = incode(it.value.toString(), varDeclaration.second)
                            "${it.key}${if (varDeclaration.third == '*') "=" else ","}$str"
                        }
                        if (varDeclaration.third == '*') {
                            mapped.joinTo(buffer, separator, preparator)
                        } else {
                            mapped.joinTo(buffer, ","
                                    , preparator + if (type != null && ";?&".contains(type)) "${varDeclaration.first}=" else "")
                        }
                    }
                }
                variable != null -> {
                    var str = incode(variable.toString(), varDeclaration.second)
                    buffer.append(preparator)

                    type?.let {
                        if (";?&".contains(type)) {
                            buffer.append(varDeclaration.first)
                            if (type != ';' || str.isNotEmpty()) {
                                buffer.append(if (varDeclaration.third == '*') separator else "=")
                            }
                        }
                    }

                    buffer.append(str)
                }
            }

            if (type == null || "+#?".contains(type)) {
                preparator = if(variable != null) separator else preparator
            }
        }
    }

    fun getNames(): Iterable<String> {
        return names.map { name: Triple<String, Int?, Char?> ->
            name.first
        }
    }

    abstract fun encode(str: String): String
}