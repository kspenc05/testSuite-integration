Kent Spence
0872780
________________________________________________________________________________
There should be these folders: expected, originals, stubs, test_inputs,
testcases, all of the folders for the original source
code. 

    _________________________________________________________________________________
    The stubs folder contains every stub that I want to insert into the java code,
    for each of the first 5 test cases in my suite.

    _______________________________________________________________________________
    The originals folder contains every original function which is being replaced by 
    a stub during the stub tests that are run, tests 1-5. 

    ________________________________________________________________________________
    The test_inputs folder contains every test file necessary in order to run tests
    7-10. These are all copies of sampledata.txt which has been included with the 
    java source code which have been modified to suit my testing needs.

    __________________________________________________________________________________
    The expected folder contains every expected output necessary for all of the tests.
    Each file in this folder is what I would expect the output to be,
    these will each be compared with the actual output of the program.

    _____________________________________________________________________________________
    The testcases folder contains every single test case being run, these can be run 
    separately, refer to TEST-INSTRUCTIONS.

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Files not in folders are make_stub.py, makefile, sampledata.txt, this README,
test_suite.sh, Test_suite2_report.pdf, TEST-INSTRUCTIONS.txt, TranscriptReader.java,
and TranscriptReader.class. These files are all included here, as well as the 
java class files of the actual source code. 

    make_stub.py is used to create the stubs in each java file, if run once, it adds
    the stub, if ran again it removes the stub it previously added, and adds the
    original function back in.

    makefile is the makefile that was provided for the java source code that needs to be 
    tested.

    sampledata.txt is the sample input file used for tests 1-5. 

    Test_suite2_report.pdf is my report file. 

    TEST-INSTRUCTIONS.txt contains the instructions required for running my test suite.

    README.md is the file you are reading currently. 

$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
This assignment is 100% done.