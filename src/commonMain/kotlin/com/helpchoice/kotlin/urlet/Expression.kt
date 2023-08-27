package com.helpchoice.kotlin.urlet

const val UNRESERVED = "-._~ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
const val RESERVED = ":/?#[]@!$&'()*+,;="

/**
 * Holds a single substitution placeholder with prefix literal in front of
 * it
 */
class Expression(private val prefix: String, placeholder: String?) {
    private val type: Char?
    private val placeholders: List<Placeholder>

    init {
        (placeholder
            ?.run {
                if (first() in "/?&+#;.") {
                    first() to drop(1)
                } else {
                    null to this
                }.let { (type, variable) ->
                    type to variable.split(',')
                        .fold(listOf<Placeholder>()) { list, varName ->
                            varName
                                .split(':', limit = 2)
                                .run {
                                    val multiplier = this[0].last() == '*'
                                    val name = this[0].run {
                                        if (multiplier) dropLast(1) else this
                                    }

                                    list + Placeholder(
                                        name,
                                        run { if (size > 1) this[1].toInt() else null },
                                        multiplier
                                    )
                                }
                        }
                }
            } ?: (null to listOf()))
            .apply {
                type = first
                placeholders = second
            }
    }

    private fun pctEncode(c: Char): String = "$c".encodeToByteArray().fold("") { acc, b ->
        val u: UByte = b.toUByte()
        acc + if (u <= 0xF.toUByte()) {
            "%0${u.toString(16).uppercase()}"
        } else {
            "%${u.toString(16).uppercase()}"
        }
    }

    fun appendTo(buffer: StringBuilder, with: Map<String, Any?>) {
        buffer.append(prefix)

        var (separator, preparator) = when (type) {
            null -> "," to ""
            '?' -> "&" to if (buffer.toString().contains('?')) "&" else "?"
            '+' -> "," to ""
            '#' -> "," to "$type"
            else -> "$type" to "$type"
        }

        placeholders.forEach { varDeclaration ->
            val variable = with[varDeclaration.name]

            fun encode(value: String, limit: Int?): String {
                var str = value
                str = str.substring(0, minOf(str.length, limit ?: str.length))
                return str.toCharArray().fold("") { encoded, c ->
                    if (c in UNRESERVED
                        || (type != null && type in "+#" && c in RESERVED)
                    ) {
                        "${encoded}$c"
                    } else {
                        "${encoded}${pctEncode(c)}"
                    }
                }
            }

            when {
                variable is Collection<*> -> {
                    if (variable.isNotEmpty()) {
                        val (prefix, infix) = when (type) {
                            null, '+' -> "" to ","
                            '#' -> preparator to ","
                            ';', '&', '?' -> "$preparator${varDeclaration.name}=" to
                                    if (varDeclaration.multiplier) "$separator${varDeclaration.name}=" else ","

                            else -> preparator to if (varDeclaration.multiplier) separator else ","
                        }

                        variable.mapNotNull {
                            encode(it.toString(), varDeclaration.limit)
                        }.joinTo(buffer, infix, prefix)
                    }
                }

                variable is Map<*, *> -> {
                    if (variable.isNotEmpty()) {
                        val mapped = variable.mapNotNull {
                            val str = encode(it.value.toString(), varDeclaration.limit)
                            "${it.key}${if (varDeclaration.multiplier) "=" else ","}$str"
                        }
                        if (varDeclaration.multiplier) {
                            mapped.joinTo(buffer, separator, preparator)
                        } else {
                            mapped.joinTo(
                                buffer,
                                ",",
                                preparator + if (type != null && ";?&".contains(type)) "${varDeclaration.name}=" else ""
                            )
                        }
                    }
                }

                variable != null -> {
                    var str = encode(variable.toString(), varDeclaration.limit)
                    buffer.append(preparator)

                    type?.let {
                        if (";?&".contains(type)) {
                            buffer.append(varDeclaration.name)
                            if (type != ';' || str.isNotEmpty()) {
                                buffer.append(if (varDeclaration.multiplier) separator else "=")
                            }
                        }
                    }

                    buffer.append(str)
                }
            }

            if (type == null || "+#?".contains(type)) {
                preparator = if (variable != null) separator else preparator
            }
        }
    }

    fun getNames(): Iterable<String> = placeholders.map { it.name }

    private class Placeholder(val name: String, val limit: Int?, val multiplier: Boolean)
}