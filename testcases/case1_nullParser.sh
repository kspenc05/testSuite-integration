./make_stub.py ./GPAdb/Parser.java ./stubs/readTRnull.txt ./originals/readTR.txt
javac TranscriptReader.java
echo "RUNNING TEST CASE #1: Stubbing readTranscriptFile() in Parser.java, so it returns null"
java  TranscriptReader sampledata.txt |& tee -a > ./output/case1_output
./make_stub.py ./GPAdb/Parser.java ./stubs/readTRnull.txt ./originals/readTR.txt

if cmp -s ./output/case1_output ./expected/case_1.expected
then
    echo "SUCCESS+:"
else
    echo "FAILURE-:"
fi
