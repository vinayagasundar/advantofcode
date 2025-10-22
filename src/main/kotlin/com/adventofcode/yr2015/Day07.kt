package com.adventofcode.yr2015

import com.adventofcode.readFileFromResources

sealed interface Operator {
    val output: String

    data class Assignment(override val output: String, val value: UShort) : Operator
    data class VariableAssignment(val input: String, override val output: String) : Operator
    data class BitwiseAnd(val inputWireA: String, val inputWireB: String, override val output: String) : Operator
    data class BitwiseOr(val inputWireA: String, val inputWireB: String, override val output: String) : Operator
    data class BitwiseNot(val inputWire: String, override val output: String) : Operator
    data class BitwiseLShift(val inputWire: String, override val output: String, val shiftValue: UShort) : Operator
    data class BitwiseRShift(val inputWire: String, override val output: String, val shiftValue: UShort) : Operator
}

fun main() {
    val instructions = readFileFromResources("2015/day07.txt").orEmpty().lineSequence()

    fun String.mapToOperator(): Operator {
        return when {
            contains("NOT") -> {
                replace("NOT ", "").replace("-> ", "").split(" ")
                    .let { (input, output) -> Operator.BitwiseNot(input, output) }
            }

            contains("AND") -> {
                replace("AND ", "").replace("-> ", "").split(" ")
                    .let { (inputA, inputB, output) -> Operator.BitwiseAnd(inputA, inputB, output) }
            }

            contains("OR") -> {
                replace("OR ", "").replace("-> ", "").split(" ")
                    .let { (inputA, inputB, output) -> Operator.BitwiseOr(inputA, inputB, output) }
            }

            contains("LSHIFT") -> {
                replace("LSHIFT ", "").replace("-> ", "").split(" ")
                    .let { (input, value, output) -> Operator.BitwiseLShift(input, output, value.toUShort()) }
            }

            contains("RSHIFT") -> {
                replace("RSHIFT ", "").replace("-> ", "").split(" ")
                    .let { (input, value, output) -> Operator.BitwiseRShift(input, output, value.toUShort()) }
            }

            else -> {
                replace("-> ", "").split(" ").let { (value, output) ->
                    val updatedValue = value.toUShortOrNull()
                    updatedValue?.let { Operator.Assignment(output, it) } ?: Operator.VariableAssignment(
                        value, output
                    )
                }
            }
        }
    }

    fun List<Operator>.getOperatorForOutput(wire: String): Operator {
        return first { operator -> operator.output == wire }
    }

    val wireOutput = mutableMapOf<String, UShort>()

    fun findWireOutput(): UShort {
        val operations = instructions.map { it.mapToOperator() }.filter {
            if (it is Operator.Assignment) {
                if (wireOutput[it.output] == null) {
                    wireOutput[it.output] = it.value
                }
                false
            } else {
                true
            }
        }.toList()

        fun getValue(wire: String): UShort {
            wire.toUShortOrNull()?.let { return it }

            val value = wireOutput[wire]
            if (value != null) return value

            val operator = operations.getOperatorForOutput(wire)

            return when (operator) {
                is Operator.Assignment -> {
                    throw IllegalStateException()
                }

                is Operator.VariableAssignment -> {
                    getValue(operator.input)
                }

                is Operator.BitwiseAnd -> {
                    val inputA = getValue(operator.inputWireA)
                    val inputB = getValue(operator.inputWireB)
                    inputA and inputB
                }

                is Operator.BitwiseOr -> {
                    val inputA = getValue(operator.inputWireA)
                    val inputB = getValue(operator.inputWireB)
                    inputA or inputB
                }

                is Operator.BitwiseNot -> {
                    val input = getValue(operator.inputWire)
                    input.inv()
                }

                is Operator.BitwiseLShift -> {
                    val input = getValue(operator.inputWire)
                    (input.toInt() shl operator.shiftValue.toInt()).toUShort()
                }

                is Operator.BitwiseRShift -> {
                    val input = getValue(operator.inputWire)
                    (input.toInt() shr operator.shiftValue.toInt()).toUShort()
                }
            }
                .also { value -> wireOutput[operator.output] = value }
        }

        return getValue("a")
    }

    val resultForOne = findWireOutput()
    println("Solve 1 $resultForOne")

    wireOutput.clear()
    wireOutput["b"] = resultForOne

    println("Solve 2 ${findWireOutput()}")


}

//d: 72
//e: 507
//f: 492
//g: 114
//h: 65412
//i: 65079
//x: 123
//y: 456
