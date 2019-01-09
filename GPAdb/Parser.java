
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
public class Parser
{
	protected Database db = null;
	protected TextTokenizer.WordExtractor tokenizer = null;
	protected Token pushedToken = null;

	/**
	 * Constructor.
	 * Create a Parser which will read its words from the supplied file.
	 * @param filename of file to open
	 * @throws FileNotFoundException on error
	 */
	public Parser(String filename) throws FileNotFoundException
	{
		tokenizer = new TextTokenizer.WordExtractor(filename);
	}

	/**
	 * Constructor.
	 * Create a Parser which will read its words from the supplied buffer
	 * @param instream open input stream to parse from
	 */
	public Parser(InputStream instream)
	{
		tokenizer = new TextTokenizer.WordExtractor(instream);
	}

	/**
	 * Read the contents of the file into memory
	 *
	 * @return success or failures
	 * @throws ParseException if parser grammer is violated by input
	 */
	public Database readTranscriptFile() throws ParseException
	{
		TermInfo term;
		Token token;


		if ((token = expectString()) == null)	return null;
		String studentName = token.stringvalue;

		if ((token = expectInteger()) == null)	return null;
		String studentID = token.stringvalue;
		this.db = new Database(studentName, studentID);

		while ((term = readTerm()) != null) {
			this.db.addTerm(term);
		}

		if (tokenizer.isAtEOF())
		{
			try {
				tokenizer.close();
			} catch (IOException e) {
				// do nothing
			}
		}

		return db;
	}

	private TermInfo readTerm() throws ParseException
	{
		Token t = null;
		TermInfo currentTerm = null;
		CourseInfo currentCourse = null;
		int index;

		// read and discard "term" marker; exception will fire
		// if it is not there
		t = expectTermString();
		if (t == null)	return null;
		if (tokenizer.isAtEOF()) return null;

		// now read the term info
		t = expectString();
		if (t == null) {
			throw new ParseException(
					"Unexpected EOF in term description",
					tokenizer.getLineNumber());
		}

		String currentTermName = t.stringvalue;

		currentTerm = null;
		for (int i = this.db.termList.size()-1; i >= 0; i--) {
			if (this.db.termList.get(i).termName.compareTo(currentTermName) == 0) {
				currentTerm = this.db.termList.get(i);
				break;
			}
		}

		if (currentTerm == null)
			currentTerm = new TermInfo(t.stringvalue);


		while ((currentCourse = readCourse()) != null) {
			currentTerm.courses.add(currentCourse);
		}

		return currentTerm;
	}

	private CourseInfo readCourse() throws ParseException
	{
		Token t = null;
		String courseName;
		CourseInfo currentCourse;

		// read "course" marker, and push back anything that
		// doesn't math, returning NULL
		t = expectString();
		if (t == null)	return null;

		if ( t.stringvalue.compareTo("course") != 0) {
			pushToken(t);
			return null;
		}

		// now read the course name
		t = expectString();
		if (t == null) {
			throw new ParseException(
					"Unexpected EOF in course description",
					tokenizer.getLineNumber());
		}
		courseName = t.stringvalue;

		t = getToken();
		if (t == null) {
			throw new ParseException(
					"Unexpected EOF in course description",
					tokenizer.getLineNumber());
		}
		if (t.type == Token.TOK_INT) {
			currentCourse = new CourseInfo(courseName,
					GPAconverter.getLetterForNumericGrade(t.intvalue),
					t.intvalue);
		} else {
			currentCourse = new CourseInfo(courseName, t.stringvalue);
		}

		return currentCourse;
	}


	private Token expectString() throws ParseException
	{
		Token t = getToken();
		if (t != null && t.type != Token.TOK_STRING) {
			throw new ParseException(
					String.format("Expected string in file at [%s]\n", t.stringvalue),
					tokenizer.getLineNumber());
		}
		return t;
	}

	private Token expectInteger() throws ParseException
	{
		Token t = getToken();
		if (t != null && t.type != Token.TOK_INT) {
			throw new ParseException(
					String.format("Expected integer in file at [%s]\n", t.stringvalue),
					tokenizer.getLineNumber());
		}
		return t;
	}

	private Token expectTermString() throws ParseException
	{
		Token t = getToken();
		if (t == null)	return null;
		if (t.type != Token.TOK_STRING || t.stringvalue.compareTo("term") != 0) {
			throw new ParseException(
					String.format("Expected 'term' in file at [%s]\n", t.stringvalue),
					tokenizer.getLineNumber());
		}
		return t;
	}

	private void pushToken(Token lastToken)
	{
		pushedToken = lastToken;
	}

	private Token getToken()
	{
		Token result = null;

		if (pushedToken != null) {
			result = pushedToken;
			pushedToken = null;
			return result;
		}

		String word = tokenizer.getNextWord();
		if (word == null) return null;

		result = new Token(word);

		try {
			int intvalue = Integer.parseInt(word);
			result.setAsInt(intvalue);
		} catch (NumberFormatException e) {
			// do nothing
		}

		return result;
	}

}

class Token {
	static final int TOK_STRING = 0x01;
	static final int TOK_INT = 0x02;

	public String stringvalue = null;
	public int intvalue;
	public int type = -1;

	public Token(String stringvalue)
	{
		this.stringvalue = stringvalue;
		this.type = TOK_STRING;
	}

	public void setAsInt(int intvalue)
	{
		this.intvalue = intvalue;
		this.type = TOK_INT;
	}

}

