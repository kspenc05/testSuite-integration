
package GPAcalculator;

import java.lang.*;
import java.util.*;
import java.awt.*;
import GPAcalculator.*;


/**
 * Do the basic conversion back and forth work between letters, percentages
 * and GPA values
 */

public class GPAconverter {

	/**
	 * Convert from percentage to letter.
	 * @param percentageGrade a numeric grade, such as 78 (percent)
	 * @return a gradeletter string based on an integer grade [0-100]
	 */
	public static String
	getLetterForNumericGrade(int percentageGrade)
	{
		int i;

		if (percentageGrade < 0 || percentageGrade > 100)
			return null;

		for (i = 0; i < GPA_NUM_GRADE_RANGES; i++)
		{
			if (sGradeConversions[i].lo < percentageGrade 
					&& percentageGrade <= sGradeConversions[i].hi)
				return sGradeConversions[i].letter;
		}

		/* we should never get here */
		System.err.format("No letter available for percentage %d\n", percentageGrade);
		assert false : i;
		return null;
	}

	/**
	 * Convert from letter to GPA.
	 * @param letterGrade the grade letter to convert (e.g.; "B-")
	 * @return GPA from a letter grade
	 */
	public static float
	getGPAforLetterGrade(String letterGrade)
	{
		int i;

		for (i = 0; i < GPA_NUM_GRADE_RANGES; i++)
		{
			if (sGradeConversions[i].letter.compareTo(letterGrade) == 0)
				return sGradeConversions[i].gpa;
		}

		/* we got an invalid letter grade */
		System.err.format("Letter grade '%s' not an allowable grade\n", letterGrade);
		return java.lang.Float.POSITIVE_INFINITY;
	}

	/**
	 * Convert from letter grade partition index  to GPA.
	 * @param letterIndex the index [0-13] of the letter grade
	 * @return GPA based on index of letter grade
	 */
	public static float
	getGPAforLetterIndex(int letterIndex)
	{
		return sGradeConversions[letterIndex].gpa;
	}

	/**
	 * Convert from percentage to GPA.
	 * @param percentageGrade a numeric grade, such as 78 (percent)
	 * @return GPA based on a numeric percentage grade
	 */
	public static float
	getGPAforNumericGrade(int percentageGrade)
	{
		return getGPAforLetterGrade(getLetterForNumericGrade(percentageGrade));
	}


	/** A+ is index zero */
	public static final int	GPA_A_PLUS		= 0x00;
	public static final int	GPA_A			= 0x01;
	public static final int	GPA_A_MINUS		= 0x02;
	public static final int	GPA_B_PLUS		= 0x03;
	public static final int	GPA_B			= 0x04;
	public static final int	GPA_B_MINUS		= 0x05;
	public static final int	GPA_C_PLUS		= 0x06;
	public static final int	GPA_C			= 0x07;
	public static final int	GPA_C_MINUS		= 0x08;
	public static final int	GPA_D_PLUS		= 0x09;
	public static final int	GPA_D			= 0x10;
	public static final int	GPA_D_MINUS		= 0x11;
	public static final int	GPA_F			= 0x12;

	/** the total number of grade ranges */
	public static final int	GPA_NUM_GRADE_RANGES		= 13;


	private static GPAconversion[] sGradeConversions = null;
	
	static {
		sGradeConversions = new GPAconversion[GPA_NUM_GRADE_RANGES];

			/*	 Description		GPA		Letter		Low	High	*/
		sGradeConversions[0] = new GPAconversion(
				"Outstanding",		4.3f,	"A+",		90,	100	);
		sGradeConversions[1] = new GPAconversion(
				"Excellent",		4.0f,	"A",		85,	89	);
		sGradeConversions[2] = new GPAconversion(
				"Very Good",		3.7f,	"A-",		80,	84	);
		sGradeConversions[3] = new GPAconversion(
				"Good",				3.3f,	"B+",		77,	79	);
		sGradeConversions[4] = new GPAconversion(
				"Good",				3.0f,	"B",		73,	76	);
		sGradeConversions[5] = new GPAconversion(
				"Good",				2.7f,	"B-",		70,	72	);
		sGradeConversions[6] = new GPAconversion(
				"Satisfactory",		2.3f,	"C+",		67,	69	);
		sGradeConversions[7] = new GPAconversion(
				"Satisfactory",		2.0f,	"C",		63,	66	);
		sGradeConversions[8] = new GPAconversion(
				"Satisfactory",		1.7f,	"C-",		60,	62	);
		sGradeConversions[9] = new GPAconversion(
				"Marginal Pass",	1.3f,	"D+",		57,	59	);
		sGradeConversions[10] = new GPAconversion(
				"Marginal Pass",	1.0f,	"D",		53,	56	);
		sGradeConversions[11] = new GPAconversion(
				"Marginal Pass",	0.7f,	"D-",		50,	52	);
		sGradeConversions[12] = new GPAconversion(
				"Failure",			0.0f,	"F",		0,	49	);
	}

}


class GPAconversion {
	String description;
	float gpa;
	String letter;
	int lo;
	int hi;

	GPAconversion(String description, float gpa, String letter, int lo, int hi)
	{
		this.description = description;
		this.gpa = gpa;
		this.letter = letter;
		this.lo = lo;
		this.hi = hi;
	}
}

