
package GPAdb;

import java.util.*;
import java.io.*;
import java.text.*;

import TextTokenizer.*;
import GPAcalculator.*;


/**
 * A utility class used within the database
 */
public class CourseInfo
{
	protected String name = null;
	protected String gradeLetter = null;
	protected int gradePercent = (-1);

	protected CourseInfo(String name, String letter, int number) {
		this.name = name;
		this.gradeLetter = letter;
		this.gradePercent = number;
	}
	protected CourseInfo(String name, String letter) {
		this.name = name;
		this.gradeLetter = letter;
		this.gradePercent = (-1);
	}

	/**
	 * @return the name of the course
	 */
	public String getName() { return this.name; }

	/**
	 * @return whether a percentage has been stored
	 */
	public boolean hasGradePercentage() { return this.gradePercent >= 0; }

	/**
	 * @return the grade percentage than has been stored, or -1 if none
	 */
	public int getGradePercentage() { return this.gradePercent; }

	/**
	 * @return the grade letter than has been stored
	 */
	public String getGradeLetter() { return this.gradeLetter; }

	public String toString() {
		return "{"
				+ (name == null ? "NULL NAME" : name)
				+ ":" + (gradeLetter == null ? "NULL GRADE" : gradeLetter)
				+ ":(" + gradePercent + ")}";
	}
}

