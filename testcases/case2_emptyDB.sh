./make_stub.py ./GPAdb/Parser.java ./stubs/readTRemptyDb.txt ./originals/readTR.txt 
javac TranscriptReader.java
echo "RUNNING TEST CASE #2: Stubbing readTranscriptFile() in Parser.java, so it returns empty database object"
java  TranscriptReader sampledata.txt |& tee -a > ./output/case2_output
./make_stub.py ./GPAdb/Parser.java ./stubs/readTRemptyDb.txt ./originals/readTR.txt

if cmp -s ./output/case2_output ./expected/case_2.expected
then
    echo "SUCCESS+:"
else
    echo "FAILURE-:"
fi