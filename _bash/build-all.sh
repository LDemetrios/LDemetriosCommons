#!/bin/bash

modules=$(cat _bash/Modules.lst)
for module in $modules
do
  echo "Building $module"
  mvn  clean install -pl "$module"
done