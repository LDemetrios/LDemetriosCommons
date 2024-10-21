#import "../../../common.typ": *
#import "../../../rules.typ": *
#show : mode
#import "../utilities/Tuples.typ": *

#let cn = common-name
#let id = (it) => [#it]

package org.ldemetrios.parsers;;;;

import org.ldemetrios.utilities.\*;;;;


\@JvmName(\"times_x_s\");;
operator fun \<Ch, A> NonSequentialParser\<Ch, A>.times(next: SkipParser\<Ch, \*>) = Sequential\<Ch, A, Monad\<A>>(listOf(this, next), ::Monad);;
\@JvmName(\"times_x_c\");;
operator fun \<Ch, A> NonSequentialParser\<Ch, A>.times(next: CutParserT\<Ch>) = Sequential\<Ch, A, Monad\<A>>(listOf(this, next), ::Monad);;
\@JvmName(\"times_x_x\");;
operator fun \<Ch, #cn, A: #cn, B: #cn> NonSequentialParser\<Ch, A>.times(next: MeaningfulParser\<Ch, B>) = Sequential\<Ch, #cn, Dyad\<#cn, A, B>>(listOf(this, next), ::Dyad);;
;;
\@JvmName(\"times_s_s\");;
operator fun \<Ch> SkipParser\<Ch, \*>.times(next: SkipParser\<Ch, \*>) = Sequential\<Ch, Nothing, Nullad\<Nothing>>(listOf(this, next), ::Nullad);;
\@JvmName(\"times_s_c\");;
operator fun \<Ch> SkipParser\<Ch, \*>.times(next: CutParserT\<Ch>) = Sequential\<Ch, Nothing, Nullad\<Nothing>>(listOf(this, next), ::Nullad);;
\@JvmName(\"times_s_x\");;
operator fun \<Ch, Z> SkipParser\<Ch, \*>.times(next: MeaningfulParser\<Ch, Z>) = Sequential\<Ch, Z, Monad\<Z>>(listOf(this, next), ::Monad);;
;;
\@JvmName(\"times_c_s\");;
operator fun \<Ch> CutParserT\<Ch>.times(next: SkipParser\<Ch, \*>) = Sequential\<Ch, Nothing, Nullad\<Nothing>>(listOf(this, next), ::Nullad);;
\@JvmName(\"times_c_c\");;
operator fun \<Ch> CutParserT\<Ch>.times(next: CutParserT\<Ch>) = Sequential\<Ch, Nothing, Nullad\<Nothing>>(listOf(this, next), ::Nullad);;
\@JvmName(\"times_c_x\");;
operator fun \<Ch, Z> CutParserT\<Ch>.times(next: MeaningfulParser\<Ch, Z>) = Sequential\<Ch, Z, Monad\<Z>>(listOf(this, next), ::Monad);;
;;
\@JvmName(\"times_0_s\");;
operator fun \<Ch, A> Sequential\<Ch, A, Nullad\<A>>.times(next: SkipParser\<Ch, \*>) = Sequential(parsers + next, composer);;
\@JvmName(\"times_0_c\");;
operator fun \<Ch, A> Sequential\<Ch, A, Nullad\<A>>.times(next: CutParserT\<Ch>) = Sequential(parsers + next, composer);;
\@JvmName(\"times_0_x\");;
operator fun \<Ch, Z, A> Sequential\<Ch, Z, Nullad\<Z>>.times(next: MeaningfulParser\<Ch, A>) = Sequential\<Ch, A, Monad\<A>>(parsers + next, ::Monad);;
  ;;
\@JvmName(\"times_1_s\");;
operator fun \<Ch, A> Sequential\<Ch, A, Monad\<A>>.times(next: SkipParser\<Ch, \*>) = Sequential(parsers + next, composer);;
\@JvmName(\"times_1_c\");;
operator fun \<Ch, A> Sequential\<Ch, A, Monad\<A>>.times(next: CutParserT\<Ch>) = Sequential(parsers + next, composer);;
\@JvmName(\"times_1_x\");;
operator fun \<Ch, #cn, A: #cn, B: #cn> Sequential\<Ch, A, Monad\<A>>.times(next: MeaningfulParser\<Ch, B>) = Sequential\<Ch, #cn, Dyad\<#cn, A, B>>(parsers + next, ::Dyad);;;;

#for n in range(2, up-to + 1) [
    \@JvmName(\"times_#n;_s\");;
    operator fun \<Ch, #cn, #join-params(0, n, it => [#it : #cn])>
        Sequential\<Ch, #cn, #tuple-name(n)>
        .times(next: SkipParser\<Ch, \*>) = Sequential(parsers + next, composer);;
    \@JvmName(\"times_#n;_c\");;
    operator fun \<Ch, #cn, #join-params(0, n, it => [#it : #cn])>
        Sequential\<Ch, #cn, #tuple-name(n)>
        .times(next: CutParserT\<Ch>) = Sequential(parsers + next, composer);;
    \@JvmName(\"times_#n;_x\");;
    operator fun \<Ch, #cn, #join-params(0, n + 1, it => [#it : #cn])>
        Sequential\<Ch, #cn, #tuple-name(n)>
        .times(next: MeaningfulParser\<Ch, #typearg-names.at(n)>) =
        #if (n != up-to) [
            Sequential\<Ch, #cn, #tuple-name(n + 1)>(parsers + next, ::#tuple-names.at(n+1));;;;
        ] else [
            Sequential\<Ch, #cn, Myriad\<#cn>>(parsers + next, ::Myriad);;;;
        ]
]
;;;;

\@JvmName(\"times_m_s\");;
operator fun \<Ch, #cn> Sequential\<Ch, #cn, Myriad\<#cn>>.times(next: SkipParser\<Ch, \*>) = Sequential(parsers + next, composer);;
\@JvmName(\"times_m_c\");;
operator fun \<Ch, #cn> Sequential\<Ch, #cn, Myriad\<#cn>>.times(next: CutParserT\<Ch>) = Sequential(parsers + next, composer);;
\@JvmName(\"times_m_x\");;
operator fun \<Ch, #cn> Sequential\<Ch, #cn, Myriad\<#cn>>.times(next: MeaningfulParser\<Ch, #cn>) = Sequential\<Ch, #cn, Myriad\<#cn>>(parsers + next, ::Myriad);;;;
