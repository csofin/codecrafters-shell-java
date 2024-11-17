#!/bin/bash

dir=/tmp/shell-java
jar=$dir/codecrafters-shell.jar

mvn -B package -Ddir=$dir

function print_prompt() {
  printf 'Running test for Stage #008 (Print a prompt)\n'
  out=$(printf '\n' | exec java -jar "$jar" "$@" | awk '{print $1;}')
  if [[ $out != "$" ]] ; then
    printf 'Expected prompt $, got %s\nTest Failed' "$out"
    exit 1
  fi
  printf '%s\n' "$out"
  printf 'Test Passed\n'
}

function handle_missing_commands() {
  printf 'Running test for Stage #CZ2 (Handle missing commands)\n'
  out=$(printf 'command' | exec java -jar "$jar" "$@")
  expected="command: command not found"
  if [[ ! $out =~ $expected ]] ; then
    printf 'Expected %s, got %s\nTest Failed' "$expected" "$out"
    exit 1
  fi
  printf '%s\n' "$out"
  printf 'Test Passed\n'
}

function test() {
  print_prompt
  printf '\n'
  handle_missing_commands
}

if [ $# -eq 0 ]; then
  test
fi

$1