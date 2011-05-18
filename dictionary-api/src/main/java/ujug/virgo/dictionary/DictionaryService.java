package ujug.virgo.dictionary;

/**
 * A simple dictionary service used to demonstrate fine grained
 * service swapping at the osgi bundle level.
 * 
 * @author Randy Secrist
 */
public interface DictionaryService {

	boolean checkWord(String word);

}