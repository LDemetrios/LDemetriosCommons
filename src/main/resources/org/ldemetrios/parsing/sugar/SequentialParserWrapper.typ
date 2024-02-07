#import "../../../../common.typ": *
#import "../../../../rules.typ": *
#import "../../utilities/Tuples.typ": *
/*#show : mode

package org.ldemetrios.parsing.sugar;;;;

import org.ldemetrios.parsing.\*;;
import org.ldemetrios.utilities.\*;;;;


#let joiner(n) = {
    [infix fun \<Ch, C]
    for i in range(0, n + 1) {
        [, #typearg-names.at(i) : C]
    }
    [\> SequentialParserWrapper\<C, Ch, #tuple-names.at(n)\<C]
    for i in range(0, n) {
        [, #typearg-names.at(i)]
    }
    [\>\>.andThen(that: Parser\<#typearg-names.at(n), Ch\>)=;;]
    [#tab SequentialParserWrapper\<C, Ch, #tuple-names.at(n + 1)\<C]
    for i in range(0, n + 1) {
        [, #typearg-names.at(i)]
    }
    [\>\>(this.parsers + that) { this.mapping(it) and (it.last() as #typearg-names.at(n)) };;;;]

    // Now others

    let receiver(chtype) = {
        [SequentialParserWrapper\<C, #chtype, #tuple-names.at(n)\<C]
        for i in range(0, n) { [, #typearg-names.at(i)] }
        [\>\>]
    }
    let receiver-typeargs = {
        [C]
        for i in range(0, n + 1) { [, #typearg-names.at(i) : C] }
    }
    [operator fun \<#receiver-typeargs\> #receiver("Char") .times(that: String) = this andThen  that.intoParser();;]
    [operator fun \<#receiver-typeargs\> #receiver("Char") .times(that: Regex) = this andThen  that.intoParser();;]
    [operator fun \<Ch, #receiver-typeargs\> #receiver("Ch") .times(that: (Ch) -> Boolean) = this andThen  that.intoParser();;]
    [operator fun \<Ch, #receiver-typeargs\> #receiver("Ch") .times(that: List\<Ch>) = this andThen  that.intoParser();;]
    [operator fun \<Ch, #receiver-typeargs\> #receiver("Ch") .times(that: Ch) = this andThen that.intoParser();;;;]
}

#for i in range(2, up-to - 1) {
    joiner(i)
}
*/