#!/bin/bash

#cp resources/previewrules.typ resources/rules.typ

sources=$(find resources -name "*.typ")

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

  mkdir -p "kotlin/$dir"

  converter/typst c --root ./resources $source --format pdf
done

