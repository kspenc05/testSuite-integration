import java.util.*;
import java.io.*;
import java.text.*;

import GPAdb.*;

/**
 * Read and print a formatted version of the transcript document
 */
public class TranscriptReader
{
	/**
	 * Mainline for program
	 * @param args command line arguments
	 */
	public static void main(String [] args)
	{
		PrintStream out = System.out;
		
		for (int i = 0; i < args.length; i++) {
			System.out.format("Processing argument '%s'\n", args[i]);
			if (args[i].charAt(0)== '-') {
				System.err.format("Unknown option '%s' -- ignoring\n", args[i]);
			} else {
				processFile(out, args[i]);
			}
		}

		System.exit(0);
	}

	/**
	 * process the data in one file, printing to standard output
	 *
	 * @param out output stream upon which data is placed
	 * @param filename to open and read data from
	 * @return success or failure on processing
	 */
	public static boolean processFile(PrintStream out, String filename)
	{
		GPAdb.Parser parser = null;
		GPAdb.Database db = null;

		try {
			parser = new GPAdb.Parser(filename);
		} catch (FileNotFoundException e) {
			System.err.format("Failed opening file '%s'\n", filename);
			return false;
		}

		try {
			out.format("Reading '%s':\n", filename);

			db = parser.readTranscriptFile();

			out.format("Student \'%s\' (%s):\n\n",
					db.getStudentName(), db.getStudentID());

			out.format("%d terms\n", db.getNumberOfTerms());
			for (int i = 0; i < db.getNumberOfTerms(); i++) {
				out.format("Term %s:\n", db.getTermName(i));
				for (int j = 0; j < db.getNumberOfCourses(i); j++) {
					if (db.hasCourseGradePercentageWithinTerm(i,j)) {
						out.format("    %s\t%-2s (%d %%)\n",
								db.getCourseNameWithinTerm(i,j),
								db.getCourseGradeLetterWithinTerm(i,j),
								db.getCourseGradePercentageWithinTerm(i,j));
					} else {
						out.format("    %s\t%-2s\n",
								db.getCourseNameWithinTerm(i,j),
								db.getCourseGradeLetterWithinTerm(i,j));
					}
				}
				out.format("    Term GPA : %.2f\n", db.getTermGPA(i));
			}

			out.format("\n    Overall GPA : %.2f\n", db.getOverallGPA());

		} catch (ParseException pe) {
			System.err.format("Failed at line %d on exception: %s\n",
					pe.getErrorOffset(), pe.toString());
			return false;
		}
		return true;
	}

}

