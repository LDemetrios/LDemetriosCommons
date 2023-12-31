@file:Suppress("unused")

package org.ldemetrios.klojure

import clojure.lang.IFn
import clojure.lang.RT

 val requirer = run {
    val req = RT.`var`(CLOJURE_CORE, "require")
    req.invoke(RT.readString(CLOJURE_STRING))
    req
}

 fun <T> resolve(name: String): T = resolve(CLOJURE_CORE, name)

@Suppress("UNCHECKED_CAST")
 fun <T> resolve(space: String, name: String): T = RT.`var`(space, name) as T

 const val CLOJURE_CORE = "clojure.core"
 const val CLOJURE_STRING = "clojure.string"

 val list = resolve<IFn>("list")
 val cons = resolve<IFn>("cons")
 val first = resolve<IFn>("first")
 val next = resolve<IFn>("next")
 val rest = resolve<IFn>("rest")
 val conj = resolve<IFn>("conj")
 val second = resolve<IFn>("second")
 val ffirst = resolve<IFn>("ffirst")
 val nfirst = resolve<IFn>("nfirst")
 val fnext = resolve<IFn>("fnext")
 val nnext = resolve<IFn>("nnext")
 val seq = resolve<IFn>("seq")
 val instance7 = resolve<IFn>("instance?")
 val seq7 = resolve<IFn>("seq?")
 val char7 = resolve<IFn>("char?")
 val string7 = resolve<IFn>("string?")
 val map7 = resolve<IFn>("map?")
 val vector7 = resolve<IFn>("vector?")
 val assoc = resolve<IFn>("assoc")
 val meta = resolve<IFn>("meta")
 val with_meta = resolve<IFn>("with-meta")
 val assert_valid_fdecl = resolve<IFn>("assert-valid-fdecl")
 val last = resolve<IFn>("last")
 val butlast = resolve<IFn>("butlast)")
 val to_array = resolve<IFn>("to-array")
 val cast = resolve<IFn>("cast")
 val vector = resolve<IFn>("vector")
 val vec = resolve<IFn>("vec")
 val hash_map = resolve<IFn>("hash-map")
 val hash_set = resolve<IFn>("hash-set")
 val sorted_map = resolve<IFn>("sorted-map")
 val sorted_map_by = resolve<IFn>("sorted-map-by")
 val sorted_set = resolve<IFn>("sorted-set")
 val sorted_set_by = resolve<IFn>("sorted-set-by")
 val nil7 = resolve<IFn>("nil?")
 val false7 = resolve<IFn>("false?")
 val true7 = resolve<IFn>("true?")
 val boolean7 = resolve<IFn>("boolean?")
 val not = resolve<IFn>("not")
 val some7 = resolve<IFn>("some?")
 val any7 = resolve<IFn>("any?")
 val str = resolve<IFn>("str")
 val symbol7 = resolve<IFn>("symbol?")
 val keyword7 = resolve<IFn>("keyword?")
 val symbol = resolve<IFn>("symbol")
 val gensym = resolve<IFn>("gensym")
 val keyword = resolve<IFn>("keyword")
 val find_keyword = resolve<IFn>("find-keyword")
 val spread = resolve<IFn>("spread")
 val apply = resolve<IFn>("apply")
 val vary_meta = resolve<IFn>("vary-meta")
 val chunk_buffer = resolve<IFn>("chunk-buffer")
 val chunk_append = resolve<IFn>("chunk-append")
 val chunk = resolve<IFn>("chunk")
 val chunk_first = resolve<IFn>(" chunk-first")
 val chunk_rest = resolve<IFn>("chunk-rest")
 val chunk_next = resolve<IFn>(" chunk-next")
 val chunk_cons = resolve<IFn>("chunk-cons")
 val chunked_seq7 = resolve<IFn>("chunked-seq?")
 val concat = resolve<IFn>("concat")
 val delay7 = resolve<IFn>("delay?")
 val force = resolve<IFn>("force")
 val identical7 = resolve<IFn>("identical?")
 val `=` = resolve<IFn>("=")
 val `not=` = resolve<IFn>("not=")
 val compare = resolve<IFn>("compare")
 val zero7 = resolve<IFn>("zero?")
 val count = resolve<IFn>("count")
 val int = resolve<IFn>("int")
 val nth = resolve<IFn>("nth")
 val l = resolve<IFn>("<")
 val `inc'` = resolve<IFn>("inc'")
 val inc = resolve<IFn>("inc")
 val reduce1 = resolve<IFn>("reduce1")
 val reverse = resolve<IFn>("reverse")
 val nary_inline = resolve<IFn>("nary-inline")
 val g17 = resolve<IFn>(">1?")
 val g07 = resolve<IFn>(">0?")
 val `+'` = resolve<IFn>("+'")
 val `+` = resolve<IFn>("+")
 val `times'` = resolve<IFn>("*'")
 val times = resolve<IFn>("*")
 val div = resolve<IFn>("/")
 val `-'` = resolve<IFn>("-'")
 val `-` = resolve<IFn>("-")
 val leq = resolve<IFn>("<=")
 val gr = resolve<IFn>(">")
 val geq = resolve<IFn>(">=")
 val eqeq = resolve<IFn>("==")
 val max = resolve<IFn>("max")
 val min = resolve<IFn>("min")
 val abs = resolve<IFn>("abs")
 val `dec'` = resolve<IFn>("dec'")
 val dec = resolve<IFn>("dec")
 val unchecked_inc_int = resolve<IFn>("unchecked-inc-int")
 val unchecked_inc = resolve<IFn>("unchecked-inc")
 val unchecked_dec_int = resolve<IFn>("unchecked-dec-int")
 val unchecked_dec = resolve<IFn>("unchecked-dec")
 val unchecked_negate_int = resolve<IFn>("unchecked-negate-int")
 val unchecked_negate = resolve<IFn>("unchecked-negate")
 val unchecked_add_int = resolve<IFn>("unchecked-add-int")
 val unchecked_add = resolve<IFn>("unchecked-add")
 val unchecked_subtract_int = resolve<IFn>("unchecked-subtract-int")
 val unchecked_subtract = resolve<IFn>("unchecked-subtract")
 val unchecked_multiply_int = resolve<IFn>("unchecked-multiply-int")
 val unchecked_multiply = resolve<IFn>("unchecked-multiply")
 val unchecked_divide_int = resolve<IFn>("unchecked-divide-int")
 val unchecked_remainder_int = resolve<IFn>("unchecked-remainder-int")
 val pos7 = resolve<IFn>("pos?")
 val neg7 = resolve<IFn>("neg?")
 val quot = resolve<IFn>("quot")
 val rem = resolve<IFn>("rem")
 val rationalize = resolve<IFn>("rationalize")
 val bit_not = resolve<IFn>("bit-not")
 val bit_and = resolve<IFn>("bit-and")
 val bit_or = resolve<IFn>("bit-or")
 val bit_xor = resolve<IFn>("bit-xor")
 val bit_and_not = resolve<IFn>("bit-and-not")
 val bit_clear = resolve<IFn>("bit-clear")
 val bit_set = resolve<IFn>("bit-set")
 val bit_flip = resolve<IFn>("bit-flip")
 val bit_test = resolve<IFn>("bit-test")
 val bit_shift_left = resolve<IFn>("bit-shift-left")
 val bit_shift_right = resolve<IFn>("bit-shift-right")
 val unsigned_bit_shift_right = resolve<IFn>("unsigned-bit-shift-right")
 val integer7 = resolve<IFn>("integer?")
 val even7 = resolve<IFn>("even?")
 val odd7 = resolve<IFn>("odd?")
 val int7 = resolve<IFn>("int?")
 val pos_int7 = resolve<IFn>("pos-int?")
 val neg_int7 = resolve<IFn>("neg-int?")
 val nat_int7 = resolve<IFn>("nat-int?")
 val double7 = resolve<IFn>("double?")
 val complement = resolve<IFn>("complement")
 val constantly = resolve<IFn>("constantly")
 val identity = resolve<IFn>("identity")
 val peek = resolve<IFn>("peek")
 val pop = resolve<IFn>("pop")
 val map_entry7 = resolve<IFn>("map-entry?")
 val contains7 = resolve<IFn>("contains?")
 val get = resolve<IFn>("get")
 val dissoc = resolve<IFn>("dissoc")
 val disj = resolve<IFn>("disj")
 val find = resolve<IFn>("find")
 val select_keys = resolve<IFn>("select-keys")
 val keys = resolve<IFn>("keys")
 val vals = resolve<IFn>("vals")
 val key = resolve<IFn>("key")
 val `val` = resolve<IFn>("val")
 val rseq = resolve<IFn>("rseq")
 val name = resolve<IFn>("name")
 val namespace = resolve<IFn>("namespace")
 val boolean = resolve<IFn>("boolean")
 val ident7 = resolve<IFn>("ident?")
 val simple_ident7 = resolve<IFn>("simple-ident?")
 val qualified_ident7 = resolve<IFn>("qualified-ident?")
 val simple_symbol7 = resolve<IFn>("simple-symbol?")
 val qualified_symbol7 = resolve<IFn>("qualified-symbol?")
 val simple_keyword7 = resolve<IFn>("simple-keyword?")
 val qualified_keyword7 = resolve<IFn>("qualified-keyword?")
 val check_valid_options = resolve<IFn>("check-valid-options")
 val remove_all_methods = resolve<IFn>("remove-all-methods")
 val remove_method = resolve<IFn>("remove-method")
 val prefer_method = resolve<IFn>("prefer-method")
 val methods = resolve<IFn>("methods")
 val get_method = resolve<IFn>("get-method")
 val prefers = resolve<IFn>("prefers")
 val push_thread_bindings = resolve<IFn>("push-thread-bindings")
 val pop_thread_bindings = resolve<IFn>("pop-thread-bindings")
 val get_thread_bindings = resolve<IFn>("get-thread-bindings")
 val find_var = resolve<IFn>("find-var")
 val binding_conveyor_fn = resolve<IFn>("binding-conveyor-fn")
 val setup_reference = resolve<IFn>("setup-reference")
 val agent = resolve<IFn>("agent")
 val send_via = resolve<IFn>("send-via")
 val send = resolve<IFn>("send")
 val send_off = resolve<IFn>("send-off")
 val release_pending_sends = resolve<IFn>("release-pending-sends")
 val add_watch = resolve<IFn>("add-watch")
 val remove_watch = resolve<IFn>("remove-watch")
 val agent_error = resolve<IFn>("agent-error")
 val restart_agent = resolve<IFn>("restart-agent")
 val error_handler = resolve<IFn>("error-handler")
 val error_mode = resolve<IFn>("error-mode")
 val agent_errors = resolve<IFn>("agent-errors")
 val clear_agent_errors = resolve<IFn>("clear-agent-errors")
 val shutdown_agents = resolve<IFn>("shutdown-agents")
 val ref = resolve<IFn>("ref")
 val deref_future = resolve<IFn>("deref-future")
 val deref = resolve<IFn>("deref")
 val atom = resolve<IFn>("atom")
 val get_validator = resolve<IFn>("get-validator")
 val commute = resolve<IFn>("commute")
 val alter = resolve<IFn>("alter")
 val ref_set = resolve<IFn>("ref-set")
 val ref_history_count = resolve<IFn>("ref-history-count")
 val ref_min_history = resolve<IFn>("ref-min-history")
 val ref_max_history = resolve<IFn>("ref-max-history")
 val ensure = resolve<IFn>("ensure")
 val volatile7 = resolve<IFn>("volatile?")
 val comp = resolve<IFn>("comp")
 val juxt = resolve<IFn>("juxt")
 val partial = resolve<IFn>("partial")
 val sequence = resolve<IFn>("sequence")
 val every7 = resolve<IFn>("every?")
 val not_every7 = resolve<IFn>("not-every?")
 val some = resolve<IFn>("some")
 val not_any7 = resolve<IFn>("not-any?")
 val map = resolve<IFn>("map")
 val mapcat = resolve<IFn>("mapcat")
 val filter = resolve<IFn>("filter")
 val remove = resolve<IFn>("remove")
 val reduced = resolve<IFn>("reduced")
 val reduced7 = resolve<IFn>("reduced?")
 val ensure_reduced = resolve<IFn>("ensure-reduced")
 val unreduced = resolve<IFn>("unreduced")
 val take = resolve<IFn>("take")
 val take_while = resolve<IFn>("take-while")
 val drop = resolve<IFn>("drop")
 val drop_last = resolve<IFn>("drop-last")
 val take_last = resolve<IFn>("take-last")
 val drop_while = resolve<IFn>("drop-while")
 val cycle = resolve<IFn>("cycle")
 val split_at = resolve<IFn>("split-at")
 val split_with = resolve<IFn>("split-with")
 val repeat = resolve<IFn>("repeat")
 val replicate = resolve<IFn>("replicate")
 val iterate = resolve<IFn>("iterate")
 val range = resolve<IFn>("range")
 val merge = resolve<IFn>("merge")
 val merge_with = resolve<IFn>("merge-with")
 val line_seq = resolve<IFn>("line-seq")
 val comparator = resolve<IFn>("comparator")
 val sort = resolve<IFn>("sort")
 val sort_by = resolve<IFn>("sort-by")
 val dorun = resolve<IFn>("dorun")
 val doall = resolve<IFn>("doall")
 val nthnext = resolve<IFn>("nthnext")
 val nthrest = resolve<IFn>("nthrest")
 val partition = resolve<IFn>("partition")
 val eval = resolve<IFn>("eval")
 val await = resolve<IFn>("await")
 val await_for = resolve<IFn>("await-for")
 val transient = resolve<IFn>("transient")
 val persistent1 = resolve<IFn>("persistent!")
 val conj1 = resolve<IFn>("conj!")
 val assoc1 = resolve<IFn>("assoc!")
 val dissoc1 = resolve<IFn>("dissoc!")
 val pop1 = resolve<IFn>("pop!")
 val disj1 = resolve<IFn>("disj!")
 val into1 = resolve<IFn>("into1")
 val into_array = resolve<IFn>("into-array")
 val array = resolve<IFn>("array")
 val `class` = resolve<IFn>("class")
 val type = resolve<IFn>("type")
 val num = resolve<IFn>("num")
 val long = resolve<IFn>("long")
 val float = resolve<IFn>("float")
 val double = resolve<IFn>("double")
 val short = resolve<IFn>("short")
 val byte = resolve<IFn>("byte")
 val char = resolve<IFn>("char")
 val unchecked_byte = resolve<IFn>("unchecked-byte")
 val unchecked_short = resolve<IFn>("unchecked-short")
 val unchecked_char = resolve<IFn>("unchecked-char")
 val unchecked_int = resolve<IFn>("unchecked-int")
 val unchecked_long = resolve<IFn>("unchecked-long")
 val unchecked_float = resolve<IFn>("unchecked-float")
 val unchecked_double = resolve<IFn>("unchecked-double")
 val number7 = resolve<IFn>("number?")
 val mod = resolve<IFn>("mod")
 val ratio7 = resolve<IFn>("ratio?")
 val numerator = resolve<IFn>("numerator")
 val denominator = resolve<IFn>("denominator")
 val decimal7 = resolve<IFn>("decimal?")
 val float7 = resolve<IFn>("float?")
 val rational7 = resolve<IFn>("rational?")
 val bigint = resolve<IFn>("bigint")
 val biginteger = resolve<IFn>("biginteger")
 val bigdec = resolve<IFn>("bigdec")
 val pr_on = resolve<IFn>("pr-on")
 val pr = resolve<IFn>("pr")
 val newline = resolve<IFn>("newline")
 val flush = resolve<IFn>("flush")
 val prn = resolve<IFn>("prn")
 val clj_print = resolve<IFn>("print")
 val clj_println = resolve<IFn>("println")
 val read = resolve<IFn>("read")
 val `read+string` = resolve<IFn>("read+string")
 val read_line = resolve<IFn>("read-line")
 val read_string = resolve<IFn>("read-string")
 val subvec = resolve<IFn>("subvec")
 val alength = resolve<IFn>("alength")
 val aclone = resolve<IFn>("aclone")
 val aget = resolve<IFn>("aget")
 val aset = resolve<IFn>("aset")
 val make_array = resolve<IFn>("make-array")
 val to_array_2d = resolve<IFn>("to-array-2d")
 val macroexpand_1 = resolve<IFn>("macroexpand-1")
 val macroexpand = resolve<IFn>("macroexpand")
 val create_struct = resolve<IFn>("create-struct")
 val struct_map = resolve<IFn>("struct-map")
 val struct = resolve<IFn>("struct")
 val accessor = resolve<IFn>("accessor")
 val load_reader = resolve<IFn>("load-reader")
 val load_string = resolve<IFn>("load-string")
 val set7 = resolve<IFn>("set?")
 val set = resolve<IFn>("set")
 val filter_key = resolve<IFn>("filter-key")
 val find_ns = resolve<IFn>("find-ns")
 val create_ns = resolve<IFn>("create-ns")
 val remove_ns = resolve<IFn>("remove-ns")
 val all_ns = resolve<IFn>("all-ns")
 val the_ns = resolve<IFn>("the-ns")
 val ns_name = resolve<IFn>("ns-name")
 val ns_map = resolve<IFn>("ns-map")
 val ns_unmap = resolve<IFn>("ns-unmap")
 val ns_publics = resolve<IFn>("ns-publics")
 val ns_imports = resolve<IFn>("ns-imports")
 val ns_interns = resolve<IFn>("ns-interns")
 val refer = resolve<IFn>("refer")
 val ns_refers = resolve<IFn>("ns-refers")
 val alias = resolve<IFn>("alias")
 val ns_aliases = resolve<IFn>("ns-aliases")
 val ns_unalias = resolve<IFn>("ns-unalias")
 val take_nth = resolve<IFn>("take-nth")
 val interleave = resolve<IFn>("interleave")
 val var_get = resolve<IFn>("var-get")
 val var_set = resolve<IFn>("var-set")
 val ns_resolve = resolve<IFn>("ns-resolve")
 val resolve = resolve<IFn>("resolve")
 val array_map = resolve<IFn>("array-map")
 val seq_to_map_for_destructuring = resolve<IFn>("seq-to-map-for-destructuring")
 val destructure = resolve<IFn>("destructure")
 val maybe_destructured = resolve<IFn>("maybe-destructured")
 val pr_str = resolve<IFn>("pr-str")
 val prn_str = resolve<IFn>("prn-str")
 val print_str = resolve<IFn>("print-str")
 val println_str = resolve<IFn>("println-str")
 val elide_top_frames = resolve<IFn>("elide-top-frames")
 val ex_info = resolve<IFn>("ex-info")
 val ex_data = resolve<IFn>("ex-data")
 val ex_message = resolve<IFn>("ex-message")
 val ex_cause = resolve<IFn>("ex-cause")
 val test = resolve<IFn>("test")
 val re_pattern = resolve<IFn>("re-pattern")
 val re_matcher = resolve<IFn>("re-matcher")
 val re_groups = resolve<IFn>("re-groups")
 val re_seq = resolve<IFn>("re-seq")
 val re_matches = resolve<IFn>("re-matches")
 val re_find = resolve<IFn>("re-find")
 val rand = resolve<IFn>("rand")
 val rand_int = resolve<IFn>("rand-int")
 val tree_seq = resolve<IFn>("tree-seq")
 val file_seq = resolve<IFn>("file-seq")
 val xml_seq = resolve<IFn>("xml-seq")
 val special_symbol7 = resolve<IFn>("special-symbol?")
 val var7 = resolve<IFn>("var?")
 val subs = resolve<IFn>("subs")
 val max_key = resolve<IFn>("max-key")
 val min_key = resolve<IFn>("min-key")
 val distinct = resolve<IFn>("distinct")
 val replace = resolve<IFn>("replace")
 val mk_bound_fn = resolve<IFn>("mk-bound-fn")
 val subseq = resolve<IFn>("subseq")
 val rsubseq = resolve<IFn>("rsubseq")
 val repeatedly = resolve<IFn>("repeatedly")
 val add_classpath = resolve<IFn>("add-classpath")
 val hash = resolve<IFn>("hash")
 val mix_collection_hash = resolve<IFn>("mix-collection-hash")
 val hash_ordered_coll = resolve<IFn>("hash-ordered-coll")
 val hash_unordered_coll = resolve<IFn>("hash-unordered-coll")
 val interpose = resolve<IFn>("interpose")
 val empty = resolve<IFn>("empty")
 val float_array = resolve<IFn>("float-array")
 val boolean_array = resolve<IFn>("boolean-array")
 val byte_array = resolve<IFn>("byte-array")
 val char_array = resolve<IFn>("char-array")
 val short_array = resolve<IFn>("short-array")
 val double_array = resolve<IFn>("double-array")
 val object_array = resolve<IFn>("object-array")
 val int_array = resolve<IFn>("int-array")
 val long_array = resolve<IFn>("long-array")
 val bytes7 = resolve<IFn>("bytes?")
 val seque = resolve<IFn>("seque")
 val class7 = resolve<IFn>("class?")
 val alter_var_root = resolve<IFn>("alter-var-root")
 val bound7 = resolve<IFn>("bound?")
 val thread_bound7 = resolve<IFn>("thread-bound?")
 val make_hierarchy = resolve<IFn>("make-hierarchy")
 val not_empty = resolve<IFn>("not-empty")
 val bases = resolve<IFn>("bases")
 val supers = resolve<IFn>("supers")
 val isa7 = resolve<IFn>("isa?")
 val parents = resolve<IFn>("parents")
 val ancestors = resolve<IFn>("ancestors")
 val descendants = resolve<IFn>("descendants")
 val derive = resolve<IFn>("derive")
 val underive = resolve<IFn>("underive")
 val distinct7 = resolve<IFn>("distinct?")
 val resultset_seq = resolve<IFn>("resultset-seq")
 val iterator_seq = resolve<IFn>("iterator-seq")
 val enumeration_seq = resolve<IFn>("enumeration-seq")
 val format = resolve<IFn>("format")
 val printf = resolve<IFn>("printf")
 val require = resolve<IFn>("require")
 val requiring_resolve = resolve<IFn>("requiring-resolve")
 val use = resolve<IFn>("use")
 val loaded_libs = resolve<IFn>("loaded-libs")
 val load = resolve<IFn>("load")
 val compile = resolve<IFn>("compile")
 val get_in = resolve<IFn>("get-in")
 val assoc_in = resolve<IFn>("assoc-in")
 val update_in = resolve<IFn>("update-in")
 val update = resolve<IFn>("update")
 val coll7 = resolve<IFn>("coll?")
 val list7 = resolve<IFn>("list?")
 val seqable7 = resolve<IFn>("seqable?")
 val ifn7 = resolve<IFn>("ifn?")
 val fn7 = resolve<IFn>("fn?")
 val associative7 = resolve<IFn>("associative?")
 val sequential7 = resolve<IFn>("sequential?")
 val sorted7 = resolve<IFn>("sorted?")
 val counted7 = resolve<IFn>("counted?")
 val empty7 = resolve<IFn>("empty?")
 val reversible7 = resolve<IFn>("reversible?")
 val indexed7 = resolve<IFn>("indexed?")
 val trampoline = resolve<IFn>("trampoline")
 val intern = resolve<IFn>("intern")
 val memoize = resolve<IFn>("memoize")
 val future7 = resolve<IFn>("future?")
 val future_done7 = resolve<IFn>("future-done?")
 val fnil = resolve<IFn>("fnil")
 val zipmap = resolve<IFn>("zipmap")
 val inst_ms = resolve<IFn>("inst-ms")
 val inst7 = resolve<IFn>("inst?")
 val uuid7 = resolve<IFn>("uuid?")
 val random_uuid = resolve<IFn>("random-uuid")
 val reduce = resolve<IFn>("reduce")
 val reduce_kv = resolve<IFn>("reduce-kv")
 val completing = resolve<IFn>("completing")
 val transduce = resolve<IFn>("transduce")
 val into = resolve<IFn>("into")
 val mapv = resolve<IFn>("mapv")
 val filterv = resolve<IFn>("filterv")
 val slurp = resolve<IFn>("slurp")
 val spit = resolve<IFn>("spit")
 val future_call = resolve<IFn>("future-call")
 val future_cancel = resolve<IFn>("future-cancel")
 val future_cancelled7 = resolve<IFn>("future-cancelled?")
 val pmap = resolve<IFn>("pmap")
 val pcalls = resolve<IFn>("pcalls")
 val clojure_version = resolve<IFn>("clojure-version")
 val promise = resolve<IFn>("promise")
 val deliver = resolve<IFn>("deliver")
 val flatten = resolve<IFn>("flatten")
 val group_by = resolve<IFn>("group-by")
 val partition_by = resolve<IFn>("partition-by")
 val frequencies = resolve<IFn>("frequencies")
 val reductions = resolve<IFn>("reductions")
 val rand_nth = resolve<IFn>("rand-nth")
 val partition_all = resolve<IFn>("partition-all")
 val splitv_at = resolve<IFn>("splitv-at")
 val partitionv = resolve<IFn>("partitionv")
 val partitionv_all = resolve<IFn>("partitionv-all")
 val shuffle = resolve<IFn>("shuffle")
 val map_indexed = resolve<IFn>("map-indexed")
 val keep = resolve<IFn>("keep")
 val keep_indexed = resolve<IFn>("keep-indexed")
 val bounded_count = resolve<IFn>("bounded-count")
 val every_pred = resolve<IFn>("every-pred")
 val some_fn = resolve<IFn>("some-fn")
 val with_redefs_fn = resolve<IFn>("with-redefs-fn")
 val realized7 = resolve<IFn>("realized?")
 val preserving_reduced = resolve<IFn>("preserving-reduced")
 val cat = resolve<IFn>("cat")
 val halt_when = resolve<IFn>("halt-when")
 val dedupe = resolve<IFn>("dedupe")
 val random_sample = resolve<IFn>("random-sample")
 val eduction = resolve<IFn>("eduction")
 val iteration = resolve<IFn>("iteration")
 val tagged_literal7 = resolve<IFn>("tagged-literal?")
 val tagged_literal = resolve<IFn>("tagged-literal")
 val reader_conditional7 = resolve<IFn>("reader-conditional?")
 val reader_conditional = resolve<IFn>("reader-conditional")
 val uri7 = resolve<IFn>("uri?")
 val add_tap = resolve<IFn>("add-tap")
 val remove_tap = resolve<IFn>("remove-tap")
 val update_vals = resolve<IFn>("update-vals")
 val update_keys = resolve<IFn>("update-keys")
 val parse_long = resolve<IFn>("parse-long")
 val parse_double = resolve<IFn>("parse-double")
 val parse_boolean = resolve<IFn>("parse-boolean")
 val NaN7 = resolve<IFn>("NaN?")
 val infinite7 = resolve<IFn>("infinite?")
