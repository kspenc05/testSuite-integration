javac TranscriptReader.java
java  TranscriptReader ./test_inputs/case7.txt |&tee -a > ./output/case7_output
echo "RUNNING TEST CASE #7: Running the program with a file with no courses in it"

if cmp -s ./output/case7_output ./expected/case_7.expected
then
    echo "SUCCESS+:"
else
    echo "FAILURE-:"
fi