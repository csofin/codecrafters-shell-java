#!/bin/bash

dir=/tmp/shell-java
jar=$dir/codecrafters-shell.jar

mvn -B package -Ddir=$dir

function print_prompt() {
  printf 'Running test for Stage #008 (Print a prompt)\n'
  out=$(printf '\n' | exec java -jar "$jar" "$@" | awk '{print $1;}')
  if [[ ! $out =~ $ ]] ; then
    printf 'Expected prompt $, got %s\nTest Failed' "$out"
    exit 1
  fi
  printf 'Got prompt $\nTest Passed\n'
}

function handle_missing_commands() {
  printf 'Running test for Stage #CZ2 (Handle missing commands)\n'
  out=$(printf 'command' | exec java -jar "$jar" "$@" | head -1)
  expected="command: command not found"
  if [[ ! $out =~ $expected ]] ; then
    printf 'Expected %s, got %s\nTest Failed' "$expected" "$out"
    exit 1
  fi
  printf 'Got %s\nTest Passed\n' "$out"
}

function repl() {
  printf 'Running test for Stage #FF0 (REPL)\n'
  out=$(printf 'command' | exec java -jar "$jar" "$@")
  expected="command: command not found"
  line_1=$(echo "$out" | head -1)
  if [[ ! $line_1 =~ $expected ]] ; then
    printf 'Expected %s, got %s\nTest Failed' "$expected" "$out"
    exit 1
  fi
  line_2=$(echo "$out" | head -2 | tail -1)
  if [[ ! $line_2 =~ $ ]] ; then
    printf 'Expected prompt $, got %s\nTest Failed' "$out"
    exit 1
  fi
  printf 'Got %s\nTest Passed\n' "$out"
}

function test() {
  print_prompt
  printf '\n'
  handle_missing_commands
  printf '\n'
  repl
}

if [ $# -eq 0 ]; then
  test
fi

$1