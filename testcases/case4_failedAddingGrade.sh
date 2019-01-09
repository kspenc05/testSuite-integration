./make_stub.py ./GPAcalculator/GPAcalculator.java ./stubs/addLetterGradeCalc.txt ./originals/addLetterGradeCalc.txt
javac TranscriptReader.java
echo "RUNNING TEST CASE #4: stubbing addLetterGrade() in calculator.java, so that it returns -1"
java  TranscriptReader sampledata.txt |&tee -a > ./output/case4_output
./make_stub.py ./GPAcalculator/GPAcalculator.java ./stubs/addLetterGradeCalc.txt ./originals/addLetterGradeCalc.txt

if cmp -s ./output/case4_output ./expected/case_4.expected
then
    echo "SUCCESS+:"
else
    echo "FAILURE-:"
fi