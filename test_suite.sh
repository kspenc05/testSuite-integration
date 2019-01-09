#!/bin/bash

if [ ! -d "output" ]; then
  mkdir output
fi

javac TranscriptReader.java
echo "Test suite 2: Integration Testing: These tests are designed to find out"
echo "the system handles errors from integrated components effectively"

bash ./testcases/case1:nullParser.sh
bash ./testcases/case2:emptyDB.sh
bash ./testcases/case3:failedTermGPA.sh
bash ./testcases/case4:failedAddingGrade.sh
bash ./testcases/case5:dbReturnNullStudName.sh

echo "System Testing: These tests are designed to find out if the system handles"
echo "typical use cases from the user when ran"

bash ./testcases/case6:noFileExists.sh
bash ./testcases/case7:noCourses.sh
bash ./testcases/case8:1term2courses.sh
bash ./testcases/case9:3terms2courses.sh
bash ./testcases/case10:noterm.sh