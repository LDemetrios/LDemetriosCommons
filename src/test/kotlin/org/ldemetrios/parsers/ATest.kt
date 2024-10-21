package org.ldemetrios.parsers

import io.kotest.matchers.shouldBe
import org.ldemetrios.utilities.Either


private fun <C, T> checkParser(
    parser: MeaningfulParser<C, T>,
    input: List<C>,
    ignoreResultsDifferences: Boolean = false
): Either<ErrorLevel, Result<T, C>> {
    val parseRes = parser.parse(input)
    val tracer = Tracer()
    val traceRes = parser.trace(input, tracer)
    tracer.printTo()
    if (!ignoreResultsDifferences) {
        traceRes shouldBe parseRes
    }
    return parseRes
}

fun <C, T> expectParse(
    parser: MeaningfulParser<C, T>,
    input: List<C>,
    expectedResult: T,
    expectedRest: List<C>,
    ignoreResultsDifferences: Boolean = false
) {
    val parseRes = checkParser(parser, input, ignoreResultsDifferences)
    assert(parseRes.isRight()) { "Parser returned `${parseRes.left()}` error" }
    parseRes.right().parsed shouldBe expectedResult
    parseRes.right().rest shouldBe expectedRest
}

fun <T> expectParse(
    parser: MeaningfulParser<Char, T>,
    input: String,
    expectedResult: T,
    expectedRest: String,
    ignoreResultsDifferences: Boolean = false
) {
    val parseRes = checkParser(parser, input.asList(), ignoreResultsDifferences)
    assert(parseRes.isRight()) { "Parser returned `${parseRes.left()}` error" }
    parseRes.right().parsed shouldBe expectedResult
    parseRes.right().rest.joinToString("") shouldBe expectedRest
}

fun <C, T> expectParse(
    parser: MeaningfulParser<C, T>,
    input: List<C>,
    resultVerifier: (T) -> Boolean,
    expectedRest: List<C>,
    ignoreResultsDifferences: Boolean = false
) {
    val parseRes = checkParser(parser, input, ignoreResultsDifferences)
    assert(parseRes.isRight()) { "Parser returned `${parseRes.left()}` error" }
    assert(resultVerifier(parseRes.right().parsed)) { "Parser returned `${parseRes.right().parsed}`, not passed verifier $resultVerifier" }
    parseRes.right().rest shouldBe expectedRest
}

fun <T> expectParse(
    parser: MeaningfulParser<Char, T>,
    input: String,
    resultVerifier: (T) -> Boolean,
    expectedRest: String,
    ignoreResultsDifferences: Boolean = false
) {
    val parseRes = checkParser(parser, input.asList(), ignoreResultsDifferences)
    assert(parseRes.isRight()) { "Parser returned `${parseRes.left()}` error" }
    assert(resultVerifier(parseRes.right().parsed)) { "Parser returned `${parseRes.right().parsed}`, not passed verifier $resultVerifier" }
    parseRes.right().rest.joinToString("") shouldBe expectedRest
}

fun <C, T> expectedError(
    parser: MeaningfulParser<C, T>,
    input: List<C>,
    errorLevel: ErrorLevel,
    ignoreResultsDifferences: Boolean = false
) {
    val parseRes = checkParser(parser, input, ignoreResultsDifferences)
    assert(parseRes.isLeft()) { "Parser successfully parsed `${parseRes.right()}`, and left ${parseRes.right().rest} " }
    parseRes.left() shouldBe errorLevel
}

fun <T> expectedError(
    parser: MeaningfulParser<Char, T>,
    input: String,
    errorLevel: ErrorLevel,
    ignoreResultsDifferences: Boolean = false
) {
    val parseRes = checkParser(parser, input.asList(), ignoreResultsDifferences)
    assert(parseRes.isLeft()) {
        "Parser successfully parsed `${parseRes.right().parsed}`, and left ${parseRes.right().rest.joinToString("")}"
    }
    parseRes.left() shouldBe errorLevel
}



