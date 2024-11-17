#!/bin/bash

dir=/tmp/shell-java
jar=$dir/codecrafters-shell.jar

function print_prompt() {
  mvn -B package -Ddir=$dir
  printf 'Running test for Stage #008 (Print a prompt)\n'
  out=$(printf '\n' | exec java -jar "$jar" "$@" | sed s/'\s'//g)
  if [[ $out != "$ " ]] ; then
    printf 'Expected prompt $<space>, got %s\nTest Failed' "$out"
    exit 1
  fi
  printf '%s\n' "$out"
  printf 'Test Passed\n'
}

$1