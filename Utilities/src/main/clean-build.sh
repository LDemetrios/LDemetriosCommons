#!/bin/bash

rm -r "kotlin-generated"
find resources -name "*.txt" | xargs -n 1 rm
