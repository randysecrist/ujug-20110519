package ujug.virgo.dictionary.web;

import java.io.ByteArrayInputStream;
import java.net.URLDecoder;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ujug.virgo.dictionary.DictionaryService;

@Controller 
public class RestHandler {
	
	private Logger log = LoggerFactory.getLogger(RestHandler.class);

	/*
	 * Injected Dictionary Service
	 */
	private List<DictionaryService> dictionaries;
	public void setDictionaries(List<DictionaryService> dictionaries) {
		this.dictionaries = dictionaries;
	}
	
	/**
	 * Access with something like:
	 * http://localhost:8080/dictionary/dict/word/foo
	 * 
	 * Note, only works with ENGLISH.  (Limitation of REST & URI Encoding).
	 * 
	 * @param name The name of the word to look up.
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET, value = "/word/{name}")
	@ResponseBody
	public String lookupWordByName(@PathVariable("name") String name) {
		log.info("Looking up word ...");
		return checkWord(name);
	}
	
	/**
	 * Access using checkwork.html:
	 * http://localhost:8080/dictionary/checkword.html
	 * 
	 * @param body The full request body as submitted by HTTP POST.
	 * @return A quick and dirty string that indicates existence of a word.
	 */
	@RequestMapping(method=RequestMethod.POST, value = "/word")
	@ResponseBody
	public String lookupWordPost(@RequestBody String body) {
		log.info("Post Received for: " + body);

		ByteArrayInputStream bais = new ByteArrayInputStream(body.getBytes());
		
		Properties p = new Properties();
		try { p.load(bais); } catch (Throwable t) { t.printStackTrace(); }
		
		String name = p.getProperty("name");
		try { name = URLDecoder.decode(name, "UTF-8"); } catch (Throwable t) { t.printStackTrace(); }
		
		log.info("Post Received for \"name\" variable: " + name);
		return checkWord(name);
	}
	
	/**
	 * Performs the actual word check against a single word dictionary.
	 * @param name The name of the word to check.
	 * 
	 * @return A quick and dirty string that indicates existence of a word.
	 */
	private String checkWord(String name) {
		String wordXML = null;
		if (dictionaries == null || dictionaries.size() == 0) {
			wordXML = "<error>id = " + name + ", no dictionaries to service request</error>";
		}
		else {
			
			// for this demo, I am not swapping in new services while requests
			// happen.  However, in a real scenario, I would need a mutex or
			// other mechanism here to prevent problems with live list changes.
			boolean exists = false;
			for (DictionaryService d : dictionaries) {
				exists = d.checkWord(name);
				if (exists)
					break;
			}
			

			wordXML = "<exists>" + exists + "</exists>";
		}
		return wordXML;	
	}

}
