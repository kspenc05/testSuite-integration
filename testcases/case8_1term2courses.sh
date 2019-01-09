javac TranscriptReader.java
java  TranscriptReader ./test_inputs/case8.txt |&tee -a > ./output/case8_output
echo "RUNNING TEST CASE #8: Running the program with a file which has a single"
echo "term, which contains in it 2 courses"

if cmp -s ./output/case8_output ./expected/case_8.expected
then
    echo "SUCCESS+:"
else
    echo "FAILURE-:"
fi