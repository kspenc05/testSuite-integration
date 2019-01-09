javac TranscriptReader.java
java  TranscriptReader ./test_inputs/case9.txt |&tee -a > ./output/case9_output
echo "RUNNING TEST CASE #9 (Nominal): Running the program with a file which has three terms,"
echo "and each term has 2 courses"

if cmp -s ./output/case9_output ./expected/case_9.expected
then
    echo "SUCCESS+:"
else
    echo "FAILURE-:"
fi