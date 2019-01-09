
package GPAcalculator;

import java.lang.*;
import java.util.*;
import java.awt.*;
import GPAcalculator.*;

/**
 * Do the basic conversion back and forth work
 */

public class GPAcalculator {

	public static final int GPA_MAX_COURSES_PER_TERM	= 6;
	public static final int GPA_MAX_TERMS_PER_STUDENT	= 32;

	private class TermInfo {
		private String termName;
		private int nCourses;
		private String[] letterGrades = new String[GPA_MAX_COURSES_PER_TERM];
	};

	private String studentName = null;
	private String studentID = null;
	private int nTerms;
	private TermInfo[] terms = null;

	/**
	 * Constructor.  Initialize with zeros.
	 * @param studentName full name of student
	 * @param studentID student ID number
	 */
	public GPAcalculator(String studentName, String studentID)
	{
		this.nTerms = 0;
		terms = new TermInfo[GPA_MAX_TERMS_PER_STUDENT];
		for (int i = 0; i < GPA_MAX_TERMS_PER_STUDENT; i++)
		{
			terms[i] = new TermInfo();
		}

		this.studentName = studentName;
		this.studentID = studentID;
	}

	/**
	 * Add a letter grade to the list
	 * @param term string describing the term, e.g.; "Fall 2017"
	 * @param lettergrade a grade letter, such as "B+"
	 * @return negative on failure
	 */
	public int addLetterGrade(
			String term,
			String lettergrade)
	{
		int i, found = (-1);

		/** search for other uses of this term */
		for (i = 0 ; i < GPA_MAX_TERMS_PER_STUDENT && i < this.nTerms; i++)
		{
			if (this.terms[i].termName.equals(term))
			{
				found = i;
				break;
			}
		}

		/** if this term is new, add it */
		if (found < 0)
		{
			if (this.nTerms >= GPA_MAX_TERMS_PER_STUDENT)
			{
				return -1;
			}
			found = this.nTerms++;
			this.terms[found].termName = term;
		}

		/** we have now found the right term, so add the course */
		this.terms[found].letterGrades[
					this.terms[found].nCourses++
				] = lettergrade;

		return found;
	}

	/**
	 * Add a numeric grade to the list
	 * @param term string describing the term, e.g.; "Fall 2017"
	 * @param percentageGrade a numeric grade, such as 78 (percent)
	 * @return negative on failure
	 */
	public int addNumericGrade(
			String term,
			int percentageGrade)
	{
		String letterGrade;

		letterGrade = GPAconverter.getLetterForNumericGrade(percentageGrade);

		return ( this.addLetterGrade(term, letterGrade) );
	}

	/*
	 * @return GPA calculated for the matching term
	 * @param term string describing the term, e.g.; "Fall 2017"
	 * @return GPA calculated for this term
	 */
	public float getTermGPA(String term)
	{
		int i, found = (-1);
		float gpaSummation;

		/** search for other uses of this term */
		for (i = 0 ; i < this.nTerms; i++)
		{
			if (this.terms[i].termName.equals(term))
			{
				found = i;
				break;
			}
		}

		/** if this term is not found, GPA is negative infinity */
		if (found < 0)
		{
			return java.lang.Float.POSITIVE_INFINITY;
		}

		/** calculate the average over this term */
		gpaSummation = GPAconverter.getGPAforLetterGrade(
					this.terms[found].letterGrades[0]
				);
		for (i = 1; i < this.terms[found].nCourses; i++)
		{
			gpaSummation += GPAconverter.getGPAforLetterGrade(
						this.terms[found].letterGrades[i]
					);
		}

		return gpaSummation / (float) this.terms[found].nCourses;
	}

	/*
	 * @return overall GPA, calculated across all terms
	 */
	public float getOverallGPA()
	{
		float gpaSummation;
		int i;

		gpaSummation = this.getTermGPA(this.terms[0].termName);
		for (i = 1; i < this.nTerms; i++)
		{
			gpaSummation += this.getTermGPA(this.terms[i].termName);
		}
		return gpaSummation / (float) this.nTerms;
	}

}
