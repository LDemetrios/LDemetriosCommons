#!/bin/bash

(
  cd Common-Utils || exit
  mvn clean install -U
  cd ..
)

(
  cd Klojure || exit
  mvn clean install -U
  cd ..
)

