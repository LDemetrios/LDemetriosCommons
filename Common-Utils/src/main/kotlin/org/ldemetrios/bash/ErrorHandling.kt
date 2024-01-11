package org.ldemetrios.bash

import kotlinx.coroutines.channels.Channel
import org.ldemetrios.bash.sugar.div

//fun <I, O, E, Ex : Throwable> Aqueduct<I, O, E, Ex>.redirect() = this / {
//    val errorStream = this.error as Channel<E>
//}