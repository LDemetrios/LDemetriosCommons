#import "../../../common.typ": *
#import "../../../rules.typ": *
#show : mode
package org.ldemetrios.functional;;;;

fun \<T> conjLazy(list: Sequence\<T>): Sequence\<T> = list;;

#rep(
  it => [
    fun \<T> conjLazy(list: Sequence\<T>,
    #alphjoin(it, jt => [#jt : T])) : Sequence\<T> = listOf(list, listOf(#alphjoin(it, jt => [#jt])).asSequence()).asSequence().flatMap { it }
  ],
  from : 1,
  sep: [;; ],
)
