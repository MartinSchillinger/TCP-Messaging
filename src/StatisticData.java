
public class StatisticData {
	
	private static int minLength = 2147483647;
	private static int maxLength = 0;
	
	private static int minWords = 2147483647;
	private static int maxWords= 0;
	
	private static int lastWordCount = 0;
	
	/**
	 * Updates min and max values of received packets
	 * @param String
	 */
	public static void update(String str)
	{
			
		//Set min/max length values
		minLength = (str.length() < minLength)? str.length() : minLength ;
		maxLength = (str.length() > maxLength)? str.length() : maxLength ;
		
		String[] words = str.split("\\s+");
		
		//Set min/max word count values
		minWords = (words.length < minWords)? words.length : minWords ;
		maxWords = (words.length > maxWords)? words.length : maxWords ;
		
		lastWordCount = words.length;
	}


	public static int getMinLength() {
		return minLength;
	}


	public static int getMaxLength() {
		return maxLength;
	}


	public static int getMinWords() {
		return minWords;
	}


	public static int getMaxWords() {
		return maxWords;
	}


	public static int getLastWordCount() {
		return lastWordCount;
	}

}
