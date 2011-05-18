package ujug.virgo.dictionary.english;

import ujug.virgo.dictionary.DictionaryService;

public class EnglishDictionary implements DictionaryService {
	
	public EnglishDictionary() {
		super();
	}
	
	@Override
	public boolean checkWord(String word) {
		String[] words = {
				"some", "people", "call", "me", "randy",
				"others", "call", "me", "ryan", "foo", "bar"
		};
		
		for (String dictWord: words) {
			if (dictWord.equalsIgnoreCase(word)) {
				return true;
			}
		}

		return false;
	}
}
