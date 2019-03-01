import java.awt.Font;
import java.util.Collections;
import java.util.Iterator;

import junit.framework.TestCase;
import edu.uwm.cs351.HTMLWordIterator;
import edu.uwm.cs351.Word;
import edu.uwm.cs351.util.Element;


public class TestHTMLIterator extends TestCase {
	Element e2 = new Element("title","Test");
	Element e1 = new Element("head",e2);
	Element e5 = new Element("em","First");
	Element e4 = new Element("h1",e5,"heading");
	Element e7 = new Element("b","bold,");
	Element e8 = new Element("i","italic.");
	Element e10 = new Element("em","bold");
	Element e11 = new Element("i","italic.");
	Element e9 = new Element("b",e10,e11);
	Element e13 = new Element("em","unusually");
	Element e15 = new Element("em","emphasis.");
	Element e14 = new Element("b","bold ",e15);
	Element e12 = new Element("em","Important: emphasis nests ",e13,"even\n",e14);
	Element e17 = new Element("font",Collections.singletonMap("size","-1"),"smaller,");
	Element e18 = new Element("font",Collections.singletonMap("size","40"),"large");
	Element e19 = new Element("font",Collections.singletonMap("size","+1"),"temporarily");
	Element e20 = new Element("b","increase");
	Element e21 = new Element("font",Collections.singletonMap("size","5"),"very small.");
	Element e16 = new Element("font",Collections.singletonMap("size","-1"),"Sometimes however, the font becomes ",e17,"although ",e18,"words can occur and\nwe can ",e19,e20,"the size or make it\n",e21);
	Element e6 = new Element("p","A paragraph of words, some ",e7,"others ",e8,"We also have some in ",e9,e12,e16);
	Element e22 = new Element("h3","Note:");
	Element e26 = new Element("li","Bullets can be italic");
	Element e28 = new Element("em","plain");
	Element e27 = new Element("li","A ",e28,"text stands out.");
	Element e30 = new Element("i","italic");
	Element e29 = new Element("li","But an ",e30,"test does not.");
	Element e32 = new Element("b","Bold");
	Element e31 = new Element("li",e32,"text is obvious.");
	Element e25 = new Element("ul",e26,e27,new Element("script","This should not appear"),e29,e31);
	Element e24 = new Element("em","We have the following points:\n",e25);
	Element e33 = new Element("br");
	Element e36 = new Element("li");
	Element e37 = new Element("li");
	Element e38 = new Element("li");
	Element e39 = new Element("b","Bold");
	Element e35 = new Element("ol",e36,"Unfortunately numbered lists are hard to handle.\n",e37,"So just treat them as though un-numbered.\n",e38,e39,"text does not stand out.\n");
	Element e34 = new Element("b",e35);
	Element e23 = new Element("p",e24,e33,e34);
	Element e3 = new Element("body",new Element("style","This also should never appear"),e4,e6,e22,e23);
	Element e0 = new Element("html",e1,e3);

	Font font0 = new Font("Serif",Font.BOLD|Font.ITALIC,36);
	Font font1 = new Font("Serif",Font.BOLD,20);
	Font font2 = new Font("Serif",Font.ITALIC,20);
	Font font3 = new Font("Serif",Font.BOLD|Font.ITALIC,20);
	Font font4 = new Font("Serif",Font.PLAIN,18);
	Font font5 = new Font("Serif",Font.PLAIN,16);
	Font font6 = new Font("Serif",Font.PLAIN,40);
	Font font7 = new Font("Serif",Font.BOLD,18);
	Font font8 = new Font("Serif",Font.PLAIN,5);

	Word[] words = new Word[] {
		new Word("Test",HTMLWordIterator.INITIAL_FONT),
		null,
		null,
		null,
		null,
		new Word("First",font0),
		new Word("heading",HTMLWordIterator.HEADER_FONTS[1]),
		null,
		new Word("A",HTMLWordIterator.INITIAL_FONT),
		new Word("paragraph",HTMLWordIterator.INITIAL_FONT),
		new Word("of",HTMLWordIterator.INITIAL_FONT),
		new Word("words,",HTMLWordIterator.INITIAL_FONT),
		new Word("some",HTMLWordIterator.INITIAL_FONT),
		new Word("bold,",font1),
		new Word("others",HTMLWordIterator.INITIAL_FONT),
		new Word("italic.",font2),
		new Word("We",HTMLWordIterator.INITIAL_FONT),
		new Word("also",HTMLWordIterator.INITIAL_FONT),
		new Word("have",HTMLWordIterator.INITIAL_FONT),
		new Word("some",HTMLWordIterator.INITIAL_FONT),
		new Word("in",HTMLWordIterator.INITIAL_FONT),
		new Word("bold",font3),
		new Word("italic.",font3),
		new Word("Important:",font2),
		new Word("emphasis",font2),
		new Word("nests",font2),
		new Word("unusually",HTMLWordIterator.INITIAL_FONT),
		new Word("even",font2),
		new Word("bold",font3),
		new Word("emphasis.",font1),
		new Word("Sometimes",font4),
		new Word("however,",font4),
		new Word("the",font4),
		new Word("font",font4),
		new Word("becomes",font4),
		new Word("smaller,",font5),
		new Word("although",font4),
		new Word("large",font6),
		new Word("words",font4),
		new Word("can",font4),
		new Word("occur",font4),
		new Word("and",font4),
		new Word("we",font4),
		new Word("can",font4),
		new Word("temporarily",HTMLWordIterator.INITIAL_FONT),
		new Word("increase",font7),
		new Word("the",font4),
		new Word("size",font4),
		new Word("or",font4),
		new Word("make",font4),
		new Word("it",font4),
		new Word("very",font8),
		new Word("small.",font8),
		null,
		null,
		new Word("Note:",HTMLWordIterator.HEADER_FONTS[3]),
		null,
		new Word("We",font2),
		new Word("have",font2),
		new Word("the",font2),
		new Word("following",font2),
		new Word("points:",font2),
		null,
		null,
		new Word("\u2022",font2),
		new Word("Bullets",font2),
		new Word("can",font2),
		new Word("be",font2),
		new Word("italic",font2),
		null,
		new Word("\u2022",font2),
		new Word("A",font2),
		new Word("plain",HTMLWordIterator.INITIAL_FONT),
		new Word("text",font2),
		new Word("stands",font2),
		new Word("out.",font2),
		null,
		new Word("\u2022",font2),
		new Word("But",font2),
		new Word("an",font2),
		new Word("italic",font2),
		new Word("test",font2),
		new Word("does",font2),
		new Word("not.",font2),
		null,
		new Word("\u2022",font2),
		new Word("Bold",font3),
		new Word("text",font2),
		new Word("is",font2),
		new Word("obvious.",font2),
		null,
		null,
		null,
		new Word("\u2022",font1),
		new Word("Unfortunately",font1),
		new Word("numbered",font1),
		new Word("lists",font1),
		new Word("are",font1),
		new Word("hard",font1),
		new Word("to",font1),
		new Word("handle.",font1),
		null,
		new Word("\u2022",font1),
		new Word("So",font1),
		new Word("just",font1),
		new Word("treat",font1),
		new Word("them",font1),
		new Word("as",font1),
		new Word("though",font1),
		new Word("un-numbered.",font1),
		null,
		new Word("\u2022",font1),
		new Word("Bold",font1),
		new Word("text",font1),
		new Word("does",font1),
		new Word("not",font1),
		new Word("stand",font1),
		new Word("out.",font1),
	};

	public void testWords() {
		Iterator<Word> it = new HTMLWordIterator(e0);
		int done = 0;
		for (Word expected : words) {
			++done;
			assertTrue("Expected word" + done + ": "+expected,it.hasNext());
			Word found = it.next();
			assertEquals("Expected word"+done,expected,found);

		}
		if (it.hasNext()) {
			assertFalse("Expected all done (after " + done + "), but got " + it.next(),true);
		}
	}

	private Element makeParagraph() {
		Element result = new Element("p");
		result.addContent("Lorem ipsum dolor sit amet,"); 
		result.addContent("consectetur adipisicing elit,");
		result.addContent("sed do eiusmod tempor incididunt");
		result.addContent(new Element("em","ut"));
		result.addContent("labore et dolore magna aliqua.");
		result.addContent(new Element("b","Ut"));
		result.addContent("enim ad minim veniam,");
		result.addContent("quis nostrud exercitation ullamco laboris nisi");
		result.addContent(new Element("i","ut aliquip ex ea commodo consequat."));
		result.addContent("Duis aute irure dolor in reprehenderit in voluptate velit");
		result.addContent("esse cillum dolore eu fugiat nulla pariatur.");
		result.addContent("Excepteur sint occaecat cupidatat non proident,");
		result.addContent("sunt in culpa qui officia deserunt mollit anim id est laborum.");
		return result;
	}
	
	private Element makeList() {
		Element result = new Element("ul");
		result.addContent(new Element("li",makeParagraph(),makeParagraph(),makeParagraph()));
		result.addContent(new Element("li",makeParagraph(),makeParagraph(),makeParagraph()));
		result.addContent(new Element("li",makeParagraph(),makeParagraph(),makeParagraph()));
		result.addContent(new Element("li",makeParagraph(),makeParagraph(),makeParagraph()));
		result.addContent(new Element("li",makeParagraph(),makeParagraph(),makeParagraph()));
		result.addContent(new Element("li",makeParagraph(),makeParagraph(),makeParagraph()));
		result.addContent(new Element("li",makeParagraph(),makeParagraph(),makeParagraph()));
		result.addContent(new Element("li",makeParagraph(),makeParagraph(),makeParagraph()));
		result.addContent(new Element("li",makeParagraph(),makeParagraph(),makeParagraph()));
		result.addContent(new Element("li",makeParagraph(),makeParagraph(),makeParagraph()));
		result.addContent(new Element("li",makeParagraph(),makeParagraph(),makeParagraph()));
		return result;
	}
	
	private void addSection(Element e, int n) {
		if (n > 4) {
			e.addContent(new Element("p",makeList()));
		} else {
		    e.addContent(new Element("h" + n, "Heading Words"));
		    e.addContent(makeParagraph());
		    addSection(e,n+1);
		    addSection(e,n+1);
		    addSection(e,n+1);
		}
	}

	private Element makeHuge() {
		Element body = new Element("body");
		addSection(body,1);
		return new Element("html",new Element("head"),body);
	}
	
	private static final int TIMES = 100000;
	
	public void testEfficiency() {
		try {
			assert false;
			assertTrue(true);
		} catch (AssertionError e) {
			System.err.println("You must disable assertions to run this test.");
			System.err.println("Go to Run > Run Configurations. Select the 'Arguments' tab");
			System.err.println("Then remove '-ea' from the VM Arguments box.");
			assertFalse("Assertions must be off to run efficiency test.",true);
		}
		
		Element html = makeHuge();
		for (int i=0; i < TIMES; ++i) {
			Iterator<Word> it = new HTMLWordIterator(html);
			assertNull(it.next());
			assertNull(it.next());
			assertNull(it.next());
			assertNull(it.next());
			assertEquals(new Word("Heading",HTMLWordIterator.HEADER_FONTS[1]),it.next());
			assertEquals(new Word("Words",HTMLWordIterator.HEADER_FONTS[1]),it.next());
			assertNull(it.next());
			assertEquals(new Word("Lorem",HTMLWordIterator.INITIAL_FONT),it.next());
		}
	}
}
