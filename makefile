##
## Makefile to create jar file
##

it :
	@ echo ""
	@ echo "Targets are clean, doc and jar"
	@ echo ""

jar : GPAcalculator.mf
	javac GPAcalculator/*.java
	if [ -d GPAcalculator/src ] ; then rm -rf GPAcalculator/src ; fi
	mkdir GPAcalculator/src
	cp GPAcalculator/*.java GPAcalculator/src
	jar cmf GPAcalculator.mf ../GPAcalculator.jar \
			GPAcalculator/src/*.java GPAcalculator/*.class \
			makefile README.txt

classdocs doc :
	if [ -d classdocs ] ; then rm -rf classdocs ; fi
	mkdir classdocs
	javadoc -d classdocs *.java */*.java

../CIS4150-F17-TS2.zip zip : classdocs
	zip -r ../CIS4150-F17-TS2.zip README.md *.txt *.java */*.java makefile classdocs

clean : 
	- rm -f *.class
	- rm -f GPAcalculator/*.class
	- rm -f GPAdb/*.class
	- rm -f TextTokenizer/*.class
	- rm -rf classdocs

