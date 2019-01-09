javac TranscriptReader.java
java  TranscriptReader ./test_inputs/case10.txt |&tee -a > ./output/case10_output
echo "RUNNING TEST CASE #10: Running the program with a file which has three terms,"
echo "but one word 'term' has been removed. So it is missing a single term string"

if cmp -s ./output/case10_output ./expected/case_10.expected
then
    echo "SUCCESS+:"
else
    echo "FAILURE-:"
fi