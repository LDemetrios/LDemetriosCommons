#import "../../../common.typ": *
#import "../../../rules.typ": *
#show : mode
package org.ldemetrios.functional;;;;

fun \<T> conj(list: List\<T>): List\<T> = list;;
#rep(
  it => [
    fun \<T> conj(list: List\<T>,
    #alphjoin(it, jt => [#jt : T])) : List\<T> = list + listOf(#alphjoin(it, jt => [#jt]))
  ],
  from : 1,
  sep: [;; ],
)