#let preambula = [
  \@file:Suppress(\"PackageDirectoryMismatch\", \"unused\", \"NAME_SHADOWING\", \"KotlinRedundantDiagnosticSuppress\", \"UNUSED_PARAMETER\", \"UNUSED_PARAMETER\", \"ObjectPropertyName\", \"NonAsciiCharacters\", \"RedundantLambdaArrow\", \"TrailingComma\")
]

#let pow(a, n) = {
  let i = 1
  for j in range(0, n) {
    i *= 2
  }
  i
}

#let bit(a, n) = {
  let res = a
  for i in range(0, n) {
    res = calc.quo(res, 2)
  }
  calc.rem(res, 2)
}

#let limit = 26
#let rep(body, from: 0, to: limit, step: 1, sep: ", ") = {
  let i = from
  while (step > 0 and i < to) or (step < 0 and i > to) {
    i += step
    body(i - step) + if (i == to) { [] } else { sep }
  }
}

#let alphabetize(num, upper) = {
  str.from-unicode(str.to-unicode(if (upper) { "A" } else { "a" }) + num)
}
#let alph(num) = alphabetize(num, false)
#let ALPH(num) = alphabetize(num, true)

#let alphjoin(cnt, body, upper: false, sep:[, ]) = {
  let i = 0
  while i < cnt {
    i += 1
    body(alphabetize(i - 1, upper)) + if (i == cnt) { [] } else { sep }
  }
}

