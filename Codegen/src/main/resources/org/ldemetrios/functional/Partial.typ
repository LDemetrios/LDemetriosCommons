#import "../../../common.typ": *
#import "../../../rules.typ": *
#show : mode
package org.ldemetrios.functional;;;;
typealias U = Unit;;;;
val Ø = Unit;;;;

#rep(
  n => [
    #rep(
      mask => [
        operator fun \<#alphjoin(n, jt => jt, upper:true), R> ((#alphjoin(n, jt => jt, upper:true)) -> R).get(
          #rep(argn => [#alph(argn) : #if(bit(mask, argn) == 1) { [U] } else { ALPH(argn) }], to:n)
        ) : (#rep(
                    argn => if(bit(mask, argn) == 1) { [#ALPH(argn), ] },
                    to:n,
                    sep:[]
                  )
            ) -> R = {
                #rep(
                  argn => if(bit(mask, argn) == 1) { [#alph(argn), ] },
                  to:n,
                  sep:[]
                ) -> this(#rep(argn => [#alph(argn)], to:n))
            }
      ],
      from:0,
      to:pow(2, n),
      sep:[;;],
    )
  ],
  from:1,
  to:8,
  sep:[;;;;],
)

