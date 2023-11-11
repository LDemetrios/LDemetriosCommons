#import "../../../common.typ": *
#import "../../../rules.typ": *
#show : mode
package org.ldemetrios.functional;;;;

#rep(
    it => [
        fun \<T> equal(
            #alphjoin(it, jt => [#jt : T])
        ) : Boolean = #rep(jt => [#alph(jt - 1) == #alph(jt)], from:1, to:it, sep:[ && ])
    ],
    from:2,
    sep:[;;]
)
