javac TranscriptReader.java
java  TranscriptReader |&tee -a > ./output/case6_output
echo "RUNNING TEST CASE #6: Running the program with no file given"

if cmp -s ./output/case6_output ./expected/case_6.expected
then
    echo "SUCCESS+:"
else
    echo "FAILURE-:"
fi