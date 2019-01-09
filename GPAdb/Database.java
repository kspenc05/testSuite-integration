
package GPAdb;

import java.util.*;
import java.io.*;
import java.text.*;

import TextTokenizer.*;
import GPAcalculator.*;
import GPAdb.*;


/**
 * A class to extract words from a text document.
 */
public class Database
{
	protected String studentName = null;
	protected String studentID = null;
	protected ArrayList<TermInfo> termList = new ArrayList<TermInfo>();
	protected GPAcalculator calculator = null;

	/**
	 * Constructor.
	 * Create an empty Database which will be populated by the parser.
	 * @param studentName string for full name of student
	 * @param studentID string for ID value (probably a number) of student
	 */
	public Database(String studentName, String studentID)
	{
		this.studentName = studentName;
		this.studentID = studentID;
		calculator = new GPAcalculator(this.studentName, this.studentID);
	}

	/**
	 * Return the stored Student Name.  Nothing valid will be in this
	 * until the file is read.
	 *
	 * @return stored student name field.
	 */
	public String getStudentName() { return studentName; }

	/**
	 * Return the stored Student ID.  Nothing valid will be in this
	 * until the file is read.
	 *
	 * @return stored student ID field.
	 */
	public String getStudentID() { return studentID; }

	/**
	 * Return the stored number of terms.  Nothing valid will be in this
	 * until the file is read.
	 *
	 * @return number of terms seen
	 */
	public int getNumberOfTerms() {
		return this.termList.size();
	}

	/**
	 * Return the name of the indicated term.  Nothing valid will be in this
	 * until the file is read.
	 *
	 * @param i the term to reference
	 * @return term name
	 */
	public String getTermName(int i) { return termList.get(i).termName; }

	/**
	 * Return the stored number of courses in a given term.
	 * Nothing valid will be in this until the file is read.
	 *
	 * @param termIndex term index (in [0 ... getNumberOfTerms())
	 * @return number of courses in the given term
	 *
	 * @see GPAdb.Database#getNumberOfTerms()
	 */
	public int getNumberOfCourses(int termIndex) {
		return this.termList.get(termIndex).courses.size();
	}

	/**
	 * Return the name of a given course.
	 * Nothing valid will be in this until the file is read.
	 *
	 * @param termIndex (in [0 ... getNumberOfTerms())
	 * @param courseIndex (in [0 ... getNumberOfCourses(int i))
	 * @return course name
	 *
	 * @see GPAdb.Database#getNumberOfTerms()
	 * @see GPAdb.Database#getNumberOfCourses(int i)
	 */
	public String getCourseNameWithinTerm(int termIndex, int courseIndex) {
		return this.termList.get(termIndex).courses.get(courseIndex).name;
	}

	/**
	 * Return the grade letter acheived in a given course.
	 * Nothing valid will be in this until the file is read.
	 *
	 * @param termIndex (in [0 ... getNumberOfTerms())
	 * @param courseIndex (in [0 ... getNumberOfCourses(int i))
	 * @return grade letter
	 *
	 * @see GPAdb.Database#getNumberOfTerms()
	 * @see GPAdb.Database#getNumberOfCourses(int i)
	 */
	public String getCourseGradeLetterWithinTerm(int termIndex, int courseIndex) {
		return this.termList.get(termIndex).courses.get(courseIndex).gradeLetter;
	}

	/**
	 * Return whether percentage grade was recorded for this course.
	 * Nothing valid will be in this until the file is read.
	 *
	 * @param termIndex (in [0 ... getNumberOfTerms())
	 * @param courseIndex (in [0 ... getNumberOfCourses(int i))
	 * @return whether a percentage grade exists for this course
	 *
	 * @see GPAdb.Database#getNumberOfTerms()
	 * @see GPAdb.Database#getNumberOfCourses(int i)
	 */
	public boolean hasCourseGradePercentageWithinTerm(
				int termIndex,
				int courseIndex
			) {
		return this.termList.get(
					termIndex
				).courses.get(
						courseIndex
					).gradePercent >= 0;
	}

	/**
	 * Return the percentage grade acheived in a given course (if given);
	 * -1 will be returned if not given
	 * Nothing valid will be in this until the file is read.
	 *
	 * @param termIndex (in [0 ... getNumberOfTerms())
	 * @param courseIndex (in [0 ... getNumberOfCourses(int i))
	 * @return percentage grade
	 *
	 * @see GPAdb.Database#getNumberOfTerms()
	 * @see GPAdb.Database#getNumberOfCourses(int i)
	 */
	public int getCourseGradePercentageWithinTerm(int termIndex, int courseIndex)
	{
		return this.termList.get(
					termIndex
				).courses.get(
						courseIndex
					).gradePercent;
	}

	/*
	 * @return GPA calculated for the indicated term
	 * @param term ID
	 */
	public float getTermGPA(int termIndex)
	{
		return calculator.getTermGPA(getTermName(termIndex));
	}

	/*
	 * @return GPA calculated for all terms
	 */
	public float getOverallGPA()
	{
		return calculator.getOverallGPA();
	}

	/**
	 * Add in another term worth of information.
	 *
	 * @param term information
	 */
	protected void addTerm(TermInfo term) {
		termList.add(term);
		for (int i = 0; i < term.courses.size(); i++) {
			calculator.addLetterGrade(
						term.termName,
						term.courses.get(i).gradeLetter
					);
		}
	}
}

