
package GPAdb;

import java.util.*;
import java.io.*;
import java.text.*;

import TextTokenizer.*;
import GPAcalculator.*;


/**
 * A purely utility class used within the database
 */
public class TermInfo {
	protected String termName = null;
	protected ArrayList<CourseInfo> courses = new ArrayList<CourseInfo>();
	protected TermInfo(String name) { termName = name; }
}

