#!/usr/bin/python
import sys

with open(sys.argv[2]) as stub_file, \
    open(sys.argv[3]) as orig_file, open(sys.argv[1]) as modified:
    stub = stub_file.read()
    original = orig_file.read()
    lines = modified.read()

with open(sys.argv[1], 'w') as edited_file:
    if(lines.find(original) != -1):
        lines = lines.replace(original, stub)
    elif(lines.find(stub) != -1):   
        lines = lines.replace(stub, original)
#    else:
#        print "nothing"

    edited_file.write(lines)