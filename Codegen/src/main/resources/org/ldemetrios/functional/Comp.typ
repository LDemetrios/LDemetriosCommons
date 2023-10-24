#import "../../../common.typ": *
#import "../../../rules.typ": *
#show : mode

package org.ldemetrios.functional;;;;

fun \<A> comp(): (A) -> A = { it } ;;
fun \<A, B> comp(f0 : (A) -> (B)): (A) -> B = f0 ;;
#rep(it => [
  fun <#alphjoin(it + 1, jt => [#jt], upper: true)> comp(#rep(
    jt => [f#jt : (#ALPH(jt)) -> (#ALPH(jt + 1))], to: it
  )): (A) -> #ALPH(it) = {
    #rep(
      jt => [f#jt\(], from: (it - 1), to: -1, step: -1, sep: ""
    )it#rep(
      jt => ")", to: it, sep: ""
    )
  }
], from: 2, sep: [;; ])