
package TextTokenizer;

import java.util.*;
import java.io.*;

/**
 * A class to extract words from a text document.
 */
public class WordExtractor
{
	protected BufferedInputStream in = null;
	protected boolean hasSearchedForNextWord = false;
	protected boolean reachedEOF = false;
	protected String pendingWord = null;
	protected int pushedChar = 0;
	protected int lineNumber = 0;

	/**
	 * Constructor. Create a WordExtractor which will read its words from the
	 * supplied file.
	 * @param filename of file to open
	 * @throws FileNotFoundException on error
	 */
	public WordExtractor(String filename) throws FileNotFoundException
	{
		try {
			in = new BufferedInputStream(new FileInputStream(filename));
		} catch (SecurityException se) {
			throw new FileNotFoundException("Can't open file '" + filename + "'");
		}
	}

	/**
	 * Constructor. Create a WordExtractor which will read its words from the
	 * supplied open input stream
	 * @param instream open input stream to parse from
	 */
	public WordExtractor(InputStream instream)
	{
		in = new BufferedInputStream(instream);
	}

	/**
	 * Determines whether or not there are any more words in the
	 * file.  Useful as a means to check whether one should stop
	 * iterating over all of the words.
	 *
	 * @return whether or not there are any more words to read
	 */
	public boolean hasMoreWords()
	{
		if (hasSearchedForNextWord == false) {
			pendingWord = scanForNextWord();
			hasSearchedForNextWord = true;
		}

		if (hasSearchedForNextWord == true && pendingWord == null)
			return false;

		return true;
	}

	/**
	 * Reads the next part of the file to determine what the next
	 * "word" might be.  A "word" is defined as a series of consecutive
	 * characters made up of letters, hyphens, apostrophes or underscores.
	 *
	 * @return the next word in the file, or null if there are no more
	 */
	public String getNextWord()
	{
		String nextWord;

		if (hasMoreWords() == false) return null;

		nextWord = pendingWord;
		hasSearchedForNextWord = false;
		pendingWord = null;
		return nextWord;
	}

	/**
	 * Close down the underlying stream; used when processing is complete
	 * @throws IOException on error
	 */
	public void close() throws IOException {	in.close();	}

	/**
	 * @return EOF status
	 */
	public boolean isAtEOF() {	return reachedEOF; }

	/**
	 * @return current line number
	 */
	public int getLineNumber() {	return this.lineNumber; }

	/**
	 * Remember a character for later.  Private.
	 */
	private void pushChar(int c) {	pushedChar = c;	}

	/**
	 * Get the next logical character in the file.  Private.
	 */
	private int getNextChar()
	{
		int nextChar;

		if (reachedEOF == true)
			return (-1);

		if (pushedChar != 0) {
			nextChar = pushedChar;
			pushedChar = 0;
			return nextChar;
		}

		try {
			nextChar = in.read();
		} catch (IOException e) {
			nextChar = -1;
		}

		if (nextChar < 0)
			reachedEOF = true;
		return nextChar;
	}

	/**
	 * Determine where the next "word" begins and ends.  Called by
	 * the getNextWord() method above to do the actual work.  Private.
	 * @return the next word in the file
	 * @see getNextWord()
	 */
	private String scanForNextWord()
	{
		final int S_SKIP_LEADING = 0;	// states for variables below
		final int S_IN_LETTERS = 1;
		final int S_IN_QUOTE = 2;

		String curWord = "";
		int state = S_SKIP_LEADING;
		int aChar;

		while ((aChar = getNextChar()) > 0) {
			if (aChar == '"' ) {
				if (state != S_IN_QUOTE) {
					state = S_IN_QUOTE;
				} else {
					state = S_IN_LETTERS;
				}

			} else if (state == S_IN_QUOTE) {
				// append whatever we get, if we are in a quoted string
				curWord += (char) aChar;

			} else if (aChar == '\n') {
				lineNumber++;
				if (state != S_SKIP_LEADING) {
					pushChar(aChar);
					return curWord;
				}

			} else if (state == S_SKIP_LEADING) {
				if (! Character.isLetter(aChar) && ! Character.isDigit(aChar) )
					continue;
				state = S_IN_LETTERS;
				curWord += (char) aChar;

			} else if ( Character.isLetter(aChar) || Character.isDigit(aChar)
					|| aChar == '-' || aChar == '_' || aChar == '\'') {
				curWord += (char) aChar;

			} else {
				if (state != S_IN_QUOTE) {
					pushChar(aChar);
					return curWord;
				}
			}
		}

		/** we have reached EOF, so return null */
		return null;
	}
}

