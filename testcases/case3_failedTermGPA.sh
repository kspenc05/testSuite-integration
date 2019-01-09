./make_stub.py ./GPAcalculator/GPAcalculator.java ./stubs/getTermGPA.txt ./originals/getTermGPA.txt
javac TranscriptReader.java
echo "RUNNING TEST CASE #3: Stubbing getTermGPA() in GPAcalculator.java, so it returns -1"
java  TranscriptReader sampledata.txt |& tee -a > ./output/case3_output
./make_stub.py ./GPAcalculator/GPAcalculator.java ./stubs/getTermGPA.txt ./originals/getTermGPA.txt

if cmp -s ./output/case3_output ./expected/case_3.expected
then
    echo "SUCCESS+:"
else
    echo "FAILURE-:"
fi