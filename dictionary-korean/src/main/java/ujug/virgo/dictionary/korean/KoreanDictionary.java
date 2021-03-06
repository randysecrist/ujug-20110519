package ujug.virgo.dictionary.korean;

import ujug.virgo.dictionary.DictionaryService;

public class KoreanDictionary implements DictionaryService {
	
	public KoreanDictionary() {
		super();
	}

	@Override
	public boolean checkWord(String word) {
		String[] words = {
				"바보", "안녕"
		};
		
		for (String dictWord: words) {
			if (dictWord.equalsIgnoreCase(word)) {
				return true;
			}
		}
		
		return false;
	}
}
