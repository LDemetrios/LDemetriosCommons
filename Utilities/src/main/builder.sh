#!/bin/bash

rm -r kotlin-generated

sources=$(find resources -name "*.typ" | awk '($0 != "resources/common.typ" && $0 != "resources/rules.typ")')
cp resources/buildrules.typ resources/rules.typ

for source in $sources; do
  pref=$(awk '{print substr($0, 11, length($0) - 14)}' <<< "$source")


  dir=$(echo "$pref" | awk -F '/' '{
                                        if(NF > 1){
                                           out = $1;
                                           for (i=2; i<NF; i++) { out = out "/" $i }
                                           print out
                                        }
                                    }')

  if [[ $dir == "" ]] ; then
    continue
  fi

  file=$(echo "$pref" | awk -F '/' '{ print $NF }')

  mkdir -p "kotlin-generated/$dir"

  ./typst-txt c --root ./resources $source --format txt
  #./typst-txt c --root ./resources $source --format pdf

  cat "./resources/$pref.txt" | awk -F ';;' '{ for (i=1; i<=NF; i++) { print $i } }' > tmp
  rm "./resources/$pref.txt"
  mv -T tmp "kotlin-generated/$pref.kt"
done
