package utilities;

import org.apache.commons.lang.RandomStringUtils;

public class PatternGenerator {
	public static String orderReferenceNumber() {
		String referenceNumber = "";
		
		referenceNumber = RandomStringUtils.randomAlphabetic(3).toUpperCase() + "-";
		
		for(int i=0;i<8;i++) 
			referenceNumber += (int)Math.floor(Math.random()*(10));
		
		return referenceNumber;
	}
}
