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

function exit() {
  printf 'Running test for Stage #PN5 (The exit builtin)\n'
  printf 'exit 0' | exec java -jar "$jar" "$@"
  out=$?
  if [ "$out" -ne 0 ]; then
    printf 'Expected exit with status 0, got %s\nTest Failed' "$out"
    exit 1
  fi
  printf 'Program exited with status 0\nTest Passed\n'
}

function echo() {
  printf 'Running test for Stage #IZ3 (The echo builtin)\n'
  phrase="apple orange pear"
  out=$(printf 'echo %s' "$phrase" | exec java -jar "$jar" "$@" | head -1)
  if [[ ! $out =~ $phrase ]] ; then
    printf 'Expected %s, got %s\nTest Failed' "$phrase" "$out"
    exit 1
  fi
  printf 'Got %s\nTest Passed\n' "$out"
}

function type() {
  printf 'Running test for Stage #EZ5 (The type builtin: builtins)\n'
  out=$(printf 'type exit' | exec java -jar "$jar" "$@" | head -1)
  expected="exit is a shell builtin"
  if [[ ! $out =~ $expected ]] ; then
    printf 'Expected %s, got %s\nTest Failed' "$expected" "$out"
    exit 1
  fi
  printf 'Got %s\nTest Passed\n' "$expected"
}

function test() {
  print_prompt
  printf '\n'
  handle_missing_commands
  printf '\n'
  repl
  printf '\n'
  exit
  printf '\n'
  echo
  printf '\n'
  type
}

if [ $# -eq 0 ]; then
  test
fi

$1