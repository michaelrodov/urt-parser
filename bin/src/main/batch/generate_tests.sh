#!/usr/bin/env bash

java -ea -classpath bin/target/classes:/home/rodov/bin/randoop-3.0.10/randoop-all-3.0.10.jar randoop.main.Main gentests --testclass=logparser.Games --testsperfile=20 --junit-package-name=games --junit-output-dir=bin/src/test/java/logparser
