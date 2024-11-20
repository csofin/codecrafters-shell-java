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
  out=$(printf 'apple' | exec java -jar "$jar" "$@" | head -1)
  expected="apple: command not found"
  if [[ ! $out =~ $expected ]] ; then
    printf 'Expected %s, got %s\nTest Failed' "$expected" "$out"
    exit 1
  fi
  printf 'Got %s\nTest Passed\n' "$out"
}

function repl() {
  printf 'Running test for Stage #FF0 (REPL)\n'
  out=$(printf 'apple' | exec java -jar "$jar" "$@" | head -1)
  expected="apple: command not found"
  if [[ ! $out =~ $expected ]] ; then
    printf 'Expected %s, got %s\nTest Failed' "$expected" "$out"
    exit 1
  fi
  printf 'Got %s\nTest Passed\n' "$out"
}

function builtin_exit() {
  printf 'Running test for Stage #PN5 (The exit builtin)\n'
  printf 'exit 0' | exec java -jar "$jar" "$@"
  out=$?
  if [ "$out" -ne 0 ]; then
    printf 'Expected exit with status 0, got %s\nTest Failed' "$out"
    exit 1
  fi
  printf 'Program exited with status 0\nTest Passed\n'
}

function builtin_echo() {
  printf 'Running test for Stage #IZ3 (The echo builtin)\n'
  phrase="apple orange pear"
  out=$(printf 'echo %s' "$phrase" | exec java -jar "$jar" "$@" | head -1)
  if [[ ! $out =~ $phrase ]] ; then
    printf 'Expected %s, got %s\nTest Failed' "$phrase" "$out"
    exit 1
  fi
  printf 'Got %s\nTest Passed\n' "$out"
}

function builtin_type() {
  printf 'Running test for Stage #EZ5 (The type builtin: builtins)\n'
  out=$(printf 'type exit' | exec java -jar "$jar" "$@" | head -1)
  expected="exit is a shell builtin"
  if [[ ! $out =~ $expected ]] ; then
    printf 'Expected %s, got %s\nTest Failed' "$expected" "$out"
    exit 1
  fi
  printf 'Got %s\nTest Passed\n' "$expected"
}

function type_executable_files() {
  printf 'Running test for Stage #MG5 (The type builtin: executable files)\n'
  out=$(printf 'type cat' | exec java -DPATH="/bin/" -jar "$jar" "$@" | head -1)
  expected="cat is /bin/cat"
  if [[ ! $out =~ $expected ]] ; then
    printf 'Expected %s, got %s\nTest Failed' "$expected" "$out"
    exit 1
  fi
  printf 'Got %s\n' "$expected"
  out=$(printf 'type exit' | exec java -jar "$jar" "$@" | head -1)
  expected="exit is a shell builtin"
  if [[ ! $out =~ $expected ]] ; then
    printf 'Expected %s, got %s\nTest Failed' "$expected" "$out"
    exit 1
  fi
  printf 'Got %s\n' "$expected"
  out=$(printf 'apple' | exec java -jar "$jar" "$@" | head -1)
  expected="apple: command not found"
  if [[ ! $out =~ $expected ]] ; then
    printf 'Expected %s, got %s\nTest Failed' "$expected" "$out"
    exit 1
  fi
  printf 'Got %s\nTest Passed\n' "$expected"
}

function run_program() {
  printf 'Running test for Stage #IP1 (Run a program)\n'
  out=$(printf 'cat test_shell.sh' | exec java -DPATH="/bin" -jar "$jar" "$@" | head -1 | awk '{print $2;}')
  expected="#!/bin/bash"
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
  builtin_exit
  printf '\n'
  builtin_echo
  printf '\n'
  builtin_type
  printf '\n'
  type_executable_files
  printf '\n'
  run_program
}

if [ $# -eq 0 ]; then
  test
fi

$1