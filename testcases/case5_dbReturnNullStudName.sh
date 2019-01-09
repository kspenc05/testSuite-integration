./make_stub.py ./GPAdb/Database.java ./stubs/returnNoName.txt ./originals/getStudentName.txt
javac TranscriptReader.java
echo "RUNNING TEST CASE #5: stubbing getStudentName() in Database.java, so that it returns null"
java  TranscriptReader sampledata.txt |&tee -a > ./output/case5_output
./make_stub.py ./GPAdb/Database.java ./stubs/returnNoName.txt ./originals/getStudentName.txt
javac ./GPAdb/Database.java

if cmp -s ./output/case5_output ./expected/case_5.expected
then
    echo "SUCCESS+:"
else
    echo "FAILURE-:"
fi