#import "../../../common.typ": *
#import "../../../rules.typ": *
#show : mode
package org.ldemetrios.functional;;;;

fun str() : String = \"\";;
fun \<A> str(a : A) : String = a.toString();;

#rep(
    it => [
        fun \<#alphjoin(it, jt => [#jt], upper:true)> str(#rep(jt => [#alph(jt) : #ALPH(jt)], to:it)) : String \{;;
            val sb = StringBuilder();;
            #alphjoin(it, jt => [sb.append(#jt)], sep:[;; ]);;
            return sb.toString();;
        };;
    ],
    from:2,
    sep:[;; ]
)
