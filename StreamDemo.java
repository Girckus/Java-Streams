import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import static java.util.stream.Collectors.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class StreamDemo {

    
// ========================================================
// DEFAULT METHODS
// ========================================================

    
    /**
     * Create a string that consists of the first letters of each
     * word in the input list.
     */
    @Test
    public void ex01_accumulateFirstLetters() {
        List<String> input = Arrays.asList(
            "alfa", "bravo", "charlie", "delta", "echo", "foxtrot");
        
        StringBuilder sb = new StringBuilder();
        input.forEach(s -> sb.append(s.charAt(0)));
        String result = sb.toString();
        
        assertEquals("abcdef", result);
    }
    /* Hint
     * Use Iterable.forEach().
     */
        
    /**
     * Remove the words that have odd lengths from the list.
     */
    @Test
    public void ex02_removeOddLengthWords() {
        List<String> list = new ArrayList<>(Arrays.asList(
            "alfa", "bravo", "charlie", "delta", "echo", "foxtrot"));
        
        list.removeIf(s -> (s.length() & 1) == 1);
        
        assertEquals("[alfa, echo]", list.toString());
    }
    /* Hint
     * Use Collection.removeIf().
     */
    
    /**
     * Replace every word in the list with its upper case equivalent.
     */
    @Test
    public void ex03_upcaseAllWords() {
        List<String> list = new ArrayList<>(Arrays.asList(
            "alfa", "bravo", "charlie", "delta", "echo", "foxtrot"));
        
        list.replaceAll(s -> s.toUpperCase());
        
        assertEquals("[ALFA, BRAVO, CHARLIE, DELTA, ECHO, FOXTROT]", list.toString());
    }
    /* Hint:
     * Use List.replaceAll().
     */

    
    /**
     * Convert every key-value pair of a map into a string and append them all
     * into a single string, in iteration order.
     */
    @Test
    public void ex04_stringifyMap() {
        Map<String, Integer> input = new TreeMap<>();
        input.put("c", 3);
        input.put("b", 2);
        input.put("a", 1);
        
        StringBuilder sb = new StringBuilder();
        input.forEach((k, v) -> sb.append(String.format("%s%s", k, v)));
        String result = sb.toString();
        
        assertEquals("a1b2c3", result);
    }
    /* Hint:
     * Use Map.forEach()
     */

    
    /**
     * Given a list of words, create a map whose keys are the first letters of
     * each words, and whose values are the sum of the lengths of those words.
     */
    @Test @Ignore
    public void ex05_mapOfStringLengths() {
        List<String> list = Arrays.asList(
            "aardvark", "bison", "capybara",
            "alligator", "bushbaby", "chimpanzee",
            "avocet", "bustard", "capuchin");
        Map<String, Integer> result = new TreeMap<>();

        list.forEach(s -> result.merge(s.substring(0, 1), s.length(), Integer::sum));
        
        assertEquals("{a=23, b=20, c=26}", result.toString());
    }
    /* Hint:
     * Use Map.merge() within Iterable.forEach().
     */

    
// ========================================================
// SIMPLE STREAM PIPELINES
// ========================================================

    
    /**
     * Given a list of words, create an output list that contains
     * only the odd-length words, converted to upper case.
     */
    @Test
    public void ex06_upcaseOddLengthWords() {
        List<String> input = new ArrayList<>(Arrays.asList(
            "alfa", "bravo", "charlie", "delta", "echo", "foxtrot"));
        
        List<String> result = input.stream()
        		 				   .filter(str -> (str.length() % 2) == 1)
        		 				   .map(String::toUpperCase)
        		 				   .collect(Collectors.toList());

        assertEquals("[BRAVO, CHARLIE, DELTA, FOXTROT]", result.toString());
    }
    /* Hint 1:
     * Use filter() and map().
     */
    /* Hint 2:
     * Use Use collect() to create the result list.
     */


    /**
     * Join the second letters of words 1 through 4 of the list (inclusive,
     * counting from zero), separated by commas, into a single string.
     */
    @Test
    public void ex07_joinStreamRange() {
        List<String> input = new ArrayList<>(Arrays.asList(
            "alfa", "bravo", "charlie", "delta", "echo", "foxtrot"));

        String result = "";
        
        result = input.stream()
        			  .skip(1)
		              .limit(4)
		              .map(str -> str.substring(1, 2))
		              .collect(Collectors.joining(","));
        
        assertEquals("r,h,e,c", result);
    }
    /* Hint 1:
     * Use Stream.skip() and Stream.limit().
     */
    /* Hint 2:
     * Use Use Collectors.joining().
     */


    /**
     * Count the number of lines in the text file. (Remember to
     * use the BufferedReader named "reader" that has already been
     * opened for you.)
     * 
     * @throws IOException
     */ 
    @Test
    public void ex08_countLinesInFile() throws IOException {
        long count = reader.lines()
        		           .count();
        
        assertEquals(14, count);
    }
    /* Hint 1:
     * Use BufferedReader.lines() to get a stream of lines.
     */
    /* Hint 2:
     * Use Use Stream.count().
     */


    /**
     * Find the length of the longest line in the text file.
     * 
     * @throws IOException 
     */
    @Test
    public void ex09_findLengthOfLongestLine() throws IOException {
        int longestLength = reader.lines()
        						  .mapToInt(String::length)
        						  .max()
        						  .getAsInt();
        
        assertEquals(53, longestLength);
    }
    /* Hint 1:
     * Use Stream.mapToInt() to convert to IntStream.
     */
    /* Hint 2:
     * Look at java.util.OptionalInt to get the result. 
     */


    /**
     * Find the longest line in the text file.
     * 
     * @throws IOException 
     */
    @Test
    public void ex10_findLongestLine() throws IOException {
        String longest = reader.lines()
        					   .max(Comparator.comparingInt(String::length))
        					   .get();
        
        assertEquals("Feed'st thy light's flame with self-substantial fuel,", longest);
    }
    /* Hint 1:
     * Use Stream.max() with a Comparator.
     */
    /* Hint 2:
     * Use static methods on Comparator to help create a Comparator instance.
     */


    /**
     * Select the set of words from the input list whose length is greater than
     * to the word's position (starting from zero) in the list.
     */
    @Test
    public void ex11_selectByLengthAndPosition() {
        List<String> input = new ArrayList<>(Arrays.asList(
            "alfa", "bravo", "charlie", "delta", "echo", "foxtrot", "golf", "hotel"));
        
        List<String> result = input.stream()
        		                   .mapToInt(input::lastIndexOf)
        		                   .filter(i -> input.get(i).length() > i)
        		                   .mapToObj(input::get)
        		                   .collect(Collectors.toList());
        
        assertEquals("[alfa, bravo, charlie, delta, foxtrot]", result.toString());
    }
    /* Hint:
     * Instead of a stream of words (Strings), run an IntStream of positions. 
     */
    
    
    /**
     * Given two lists of Integer, compute a third list where each element is the
     * difference between the corresponding elements of the two input lists
     * (first minus second).
     */
    @Test
    public void ex12_listDifference() {
        List<Integer> one = Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6, 5, 3);
        List<Integer> two = Arrays.asList(2, 7, 1, 8, 2, 8, 1, 8, 2, 8);
        
        List<Integer> result = IntStream.range(0, one.size())
        								.map(index -> one.get(index)-two.get(index))
        								.boxed()
        								.collect(Collectors.toList());
        
        assertEquals("[1, -6, 3, -7, 3, 1, 1, -2, 3, -5]", result.toString());
    }
    /* Hint 1:
     * Run an IntStream of list positions (indexes).
     */
    /* Hint 2:
     * Deal with boxed Integers either by casting or by using mapToObj(). 
     */
    

// ========================================================
// INTERMEDIATE STREAM PIPELINES
// ========================================================
    
    
    /**
     * Convert a list of strings into a list of characters.
     */
    @Test
    public void ex13_stringsToCharacters() {
        List<String> input = Arrays.asList("alfa", "bravo", "charlie");
        
        List<Character> result = input.stream()
        		                 .flatMap(str -> str.chars().mapToObj(i -> (char)i))
        		                 .map(c -> (Character)c)
        		                 .collect(Collectors.toList());
        							  
        
        assertEquals("[a, l, f, a, b, r, a, v, o, c, h, a, r, l, i, e]", result.toString());
        assertTrue(result.stream().allMatch(x -> x instanceof Character));
    }
    /* Hint 1:
     * Use Stream.flatMap().
     */
    /* Hint 2:
     * Pay attention to the return type of String.chars() and boxing conversion.
     */
    

    /**
     * Collect all the words from the text file into a list.
     * Use String.split(REGEXP) to split a string into words.
     * REGEXP is defined at the bottom of this file.
     * 
     * @throws IOException
     */
    @Test
    public void ex14_listOfAllWords() throws IOException {
        List<String> output = reader.lines()
        					  .flatMap(line -> Stream.of(line.split(REGEXP)))
        					  .collect(Collectors.toList());
        
        assertEquals(
            Arrays.asList(
                "From", "fairest", "creatures", "we", "desire", "increase",
                "That", "thereby", "beauty's", "rose", "might", "never", "die",
                "But", "as", "the", "riper", "should", "by", "time", "decease",
                "His", "tender", "heir", "might", "bear", "his", "memory",
                "But", "thou", "contracted", "to", "thine", "own", "bright", "eyes",
                "Feed'st", "thy", "light's", "flame", "with", "self", "substantial", "fuel",
                "Making", "a", "famine", "where", "abundance", "lies",
                "Thy", "self", "thy", "foe", "to", "thy", "sweet", "self", "too", "cruel",
                "Thou", "that", "art", "now", "the", "world's", "fresh", "ornament",
                "And", "only", "herald", "to", "the", "gaudy", "spring",
                "Within", "thine", "own", "bud", "buriest", "thy", "content",
                "And", "tender", "churl", "mak'st", "waste", "in", "niggarding",
                "Pity", "the", "world", "or", "else", "this", "glutton", "be",
                "To", "eat", "the", "world's", "due", "by", "the", "grave", "and", "thee"),
            output);
    }
    /* Hint:
     * Use Stream.flatMap().
     */
    

    /**
     * Read the words from the text file, and create a list containing the words
     * of length 8 or longer, converted to lower case, and sorted alphabetically.
     * 
     * @throws IOException 
     */
    @Test
    public void ex15_longLowerCaseSortedWords() throws IOException {
        List<String> output = reader.lines()
							  .flatMap(line -> Stream.of(line.split(REGEXP)))
							  .filter(str -> str.length() >= 8)
							  .map(str -> str.toLowerCase())
							  .sorted()
							  .collect(Collectors.toList());
        
        assertEquals(
            Arrays.asList(
                "abundance", "beauty's", "contracted", "creatures",
                "increase", "niggarding", "ornament", "substantial"),
            output);
    }
    /* Hint:
     * Use Stream.sorted().
     */
    
    
    /**
     * Read the words from the text file, and create a list containing the words
     * of length 8 or longer, converted to lower case, and sorted reverse alphabetically.
     * (Same as above except for reversed sort order.)
     * 
     * @throws IOException 
     */
    @Test
    public void ex16_longLowerCaseReverseSortedWords() throws IOException {
        List<String> result = reader.lines()
				  			  .flatMap(line -> Stream.of(line.split(REGEXP)))
				  			  .filter(str -> str.length() >= 8)
							  .map(str -> str.toLowerCase())
							  .sorted(Comparator.reverseOrder())
							  .collect(Collectors.toList());
				  			  
        assertEquals(
            Arrays.asList(
                "substantial", "ornament", "niggarding", "increase",
                "creatures", "contracted", "beauty's", "abundance"),
            result);
    }
    /* Hint:
     * Use Comparator.reverseOrder().
     */

    
    /**
     * Read words from the text file, and sort unique, lower-cased words by length,
     * then alphabetically within length, and place the result into an output list.
     * 
     * @throws IOException 
     */
    @Test
    public void ex17_sortedLowerCaseDistinctByLengthThenAlphabetically() throws IOException {
        List<String> result = reader.lines()
        							.flatMap(line -> Stream.of(line.split(REGEXP)))
        							.map(String::toLowerCase)
        							.distinct()
        							.sorted(Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder()))
        							.collect(Collectors.toList());

        assertEquals(
            Arrays.asList(
                "a", "as", "be", "by", "in", "or", "to", "we",
                "and", "art", "bud", "but", "die", "due", "eat", "foe",
                "his", "now", "own", "the", "thy", "too", "bear", "else",
                "eyes", "from", "fuel", "heir", "lies", "only",
                "pity", "rose", "self", "that", "thee", "this", "thou",
                "time", "with", "churl", "cruel", "flame", "fresh", "gaudy",
                "grave", "might", "never", "riper", "sweet", "thine",
                "waste", "where", "world", "bright", "desire", "famine",
                "herald", "mak'st", "making", "memory", "should", "spring",
                "tender", "within", "buriest", "content", "decease",
                "fairest", "feed'st", "glutton", "light's", "thereby", "world's", "beauty's",
                "increase", "ornament", "abundance", "creatures", "contracted", "niggarding",
                "substantial"),
            result);
    }
    /* Hint 1:
     * Use Stream.distinct().
     */
    /* Hint 2:
     * Use Comparator.thenComparing().
     */

    
    /**
     * Count the total number of words and the number of distinct, lower case
     * words in the text file, in one pass.
     */
    @Test
    public void ex18_countTotalAndDistinctWords() {
        long distinctCount = 0;
        long totalCount = 0;
        
        LongAdder adder = new LongAdder();
        distinctCount =
            reader.lines()
                  .flatMap(line -> Stream.of(line.split(REGEXP)))
                  .map(String::toLowerCase)
                  .peek(s -> adder.increment())
                  .distinct()
                  .count();
        totalCount = adder.longValue();
        
        assertEquals("distinct count", 81, distinctCount);
        assertEquals("total count", 107, totalCount);
    }
    /* Hint 1:
     * Use Stream.peek().
     */
    /* Hint 2:
     * Use LongAdder or AtomicLong/AtomicInteger to allow peek() to have side effects.
     */
    
    
// ========================================================
// ADVANCED STREAMS: REDUCTION, COLLECTORS, AND GROUPING
// ========================================================

    
    /**
     * Compute the value of 21!, that is, 21 factorial. This value is larger than
     * Long.MAX_VALUE, so you must use BigInteger.
     */
    @Test
    public void ex19_bigFactorial() {
        BigInteger result = BigInteger.ONE;
        result = LongStream.rangeClosed(2, 21)
        		 .mapToObj(BigInteger::valueOf)
        		 .reduce(BigInteger.ONE, (m, n) -> m.multiply(n));
                        
        assertEquals(new BigInteger("51090942171709440000"), result);
    }
    /* Hint:
     * Use LongStream and reduction.
     */
    
    
    /**
     * Get the last word in the text file.
     * 
     * @throws IOException
     */
    @Test
    public void ex20_getLastWord() throws IOException {
        String result = reader.lines()
        				.flatMap(line -> Stream.of(line.split(REGEXP)))
						.reduce((str1, str2) -> str2)
						.get();

        assertEquals("thee", result);
    }
    /* Hint:
     * Use Stream.reduce().
     */
    

    /**
     * Categorize the words from the text file into a map, where the map's key
     * is the length of each word, and the value corresponding to a key is a
     * list of words of that length. Don't bother with uniqueness or lower-
     * casing the words.
     * 
     * @throws IOException
     */
    @Test
    public void ex21_mapLengthToWordList() throws IOException {
        Map<Integer, List<String>> result = reader.lines()
        										  .flatMap(line -> Stream.of(line.split(REGEXP)))
        		                                  .collect(groupingBy(String::length));
        
        assertEquals(10, result.get(7).size());
        assertEquals(new HashSet<>(Arrays.asList("beauty's", "increase", "ornament")), new HashSet<>(result.get(8)));
        assertEquals(new HashSet<>(Arrays.asList("abundance", "creatures")), new HashSet<>(result.get(9)));
        assertEquals(new HashSet<>(Arrays.asList("contracted", "niggarding")), new HashSet<>(result.get(10)));
        assertEquals(Arrays.asList("substantial"), result.get(11));
        assertFalse(result.containsKey(12));
    }
    /* Hint:
     * Use Collectors.groupingBy().
     */

    
    /**
     * Categorize the words from the text file into a map, where the map's key
     * is the length of each word, and the value corresponding to a key is a
     * count of words of that length. Don't bother with uniqueness or lower-
     * casing the words. This is the same as the previous exercise except
     * the map values are the count of words instead of a list of words.
     * 
     * @throws IOException
     */
    @Test
    public void ex22_mapLengthToWordCount() throws IOException {
        Map<Integer, Long> result = reader.lines()
        							.flatMap(line -> Stream.of(line.split(REGEXP)))
        							.collect(groupingBy(String::length, counting()));

        assertEquals( 1L, (long)result.get(1));
        assertEquals(11L, (long)result.get(2));
        assertEquals(28L, (long)result.get(3));
        assertEquals(21L, (long)result.get(4));
        assertEquals(16L, (long)result.get(5));
        assertEquals(12L, (long)result.get(6));
        assertEquals(10L, (long)result.get(7));
        assertEquals( 3L, (long)result.get(8));
        assertEquals( 2L, (long)result.get(9));
        assertEquals( 2L, (long)result.get(10));
        assertEquals( 1L, (long)result.get(11));
        
        IntSummaryStatistics stats = result.keySet().stream().mapToInt(i -> i).summaryStatistics();
        assertEquals("min key",  1, stats.getMin());
        assertEquals("max key", 11, stats.getMax());
    }
    /* Hint 1:
     * Use the "downstream" overload of Collectors.groupingBy().
     */
    /* Hint 2:
     * Use Collectors.counting().
     */

    
    /**
     * Gather the words from the text file into a map, accumulating a count of
     * the number of occurrences of each word. Don't worry about upper case and
     * lower case. Extra challenge: implement two solutions, one that uses
     * groupingBy() and the other that uses toMap().
     * 
     * @throws IOException
     */
    @Test
    public void ex23_wordFrequencies() throws IOException {
        Map<String, Long> result = reader.lines()
										 .flatMap(line -> Stream.of(line.split(REGEXP)))
										 .collect(groupingBy(String::toString, counting()));
        
        assertEquals(2L, (long)result.get("tender"));
        assertEquals(6L, (long)result.get("the"));
        assertEquals(1L, (long)result.get("churl"));
        assertEquals(2L, (long)result.get("thine"));
        assertEquals(1L, (long)result.get("world"));
        assertEquals(4L, (long)result.get("thy"));
        assertEquals(3L, (long)result.get("self"));
        assertFalse(result.containsKey("lambda"));
    }
    /* Hint 1:
     * For Collectors.groupingBy(), consider that each word needs to
     * be categorized by itself. 
     */
    /* Hint 2:
     * For Collectors.toMap(), the first occurrence of a word should be mapped to 1.
     */

    
    /**
     * Given a stream of strings, accumulate (collect) them into the result string
     * by inserting the input string at both the beginning and end. For example, given
     * input strings "x" and "y" the result should be "yxxy". Note: the input stream
     * is a parallel stream, so you MUST write a proper combiner function to get the
     * correct result.
     */
    @Test
    public void ex24_insertBeginningAndEnd() {
        Stream<String> input = Arrays.asList(
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
            "k", "l", "m", "n", "o", "p", "q", "r", "s", "t")
            .parallelStream();
        
        String result = input.collect(StringBuilder::new,
                (sb, s) -> sb.insert(0, s).append(s),
                (sb1, sb2) -> {
                    int half = sb2.length() / 2;
                    sb1.insert(0, sb2.substring(0, half));
                    sb1.append(sb2.substring(half));
                })
                .toString();
        
        assertEquals("tsrqponmlkjihgfedcbaabcdefghijklmnopqrst", result);
    }
    /* Hint 1:
     * The combiner function must take its second argument and merge
     * it into the first argument, mutating the first argument.
     */
    /* Hint 2:
     * The second argument to the combiner function happens AFTER the first
     * argument in encounter order, so the second argument needs to be split
     * in half and prepended/appended to the first argument.
     */
    
    /**
     * Find any member which name starts with "A"
     */
    @Test
    public void ex25_mermbersNameA() {
        Stream<String> input = Arrays.asList(
            "Amitabh", "Shekhar", "Aman", "Rahul", "Shahrukh", "Salman", "Yana", "Lokesh")
            .stream();
        
        boolean result = input.filter(str -> str.startsWith("A")).findAny().isPresent();
        
        assertEquals(true, result);
    }
    
    /**
     * Check if all members have names with 5 letter
     */
    @Test
    public void ex26_mermbersName5() {
        Stream<String> input = Arrays.asList(
            "Amita", "Shekh", "Amant", "Rahul", "Shahr", "Salma", "Yanar", "Lokes")
            .stream();
        
        boolean result = input.allMatch(str -> str.length() == 5);
        
        assertEquals(true, result);
    }
    
    /**
     * Check if there is a member which name starts with "Sha"
     */
    @Test
    public void ex27_mermbersNameSha() {
        Stream<String> input = Arrays.asList(
            "Amitabh", "Shekhar", "Aman", "Rahul", "Shahrukh", "Salman", "Yana", "Lokesh")
            .stream();
        
        boolean result = input.anyMatch(str -> str.startsWith("Sha"));
        
        assertEquals(true, result);
    }
    
    /**
     * Check if there is a member which name starts with "Zag"
     */
    @Test
    public void ex28_noMermbersNameZag() {
        Stream<String> input = Arrays.asList(
            "Amitabh", "Shekhar", "Aman", "Rahul", "Shahrukh", "Salman", "Yana", "Lokesh")
            .stream();
        
        boolean result = input.noneMatch(str -> str.startsWith("Zag"));
        
        assertEquals(true, result);
    }
    
    /**
     * Find the first member which name starts with "R"
     */
    @Test
    public void ex29_fisrtMermbersNameR() {
        Stream<String> input = Arrays.asList(
            "Amitabh", "Shekhar", "Aman", "Rahul", "Shahrukh", "Salman", "Yana", "Lokesh")
            .stream();
        
        String result = input.filter(str -> str.startsWith("R")).findFirst().get();
        
        assertEquals("Rahul", result);
    }
    
    /**
     * Print all the words in of the file. One word per line
     */
    @Test
    public void ex30_oneWordPerLine() {
    	reader.lines()
		      .flatMap(line -> Stream.of(line.split(REGEXP)))
		 	  .forEach(str -> System.out.println(str));
    }

// ========================================================
// END OF EXERCISES -- CONGRATULATIONS!
// TEST INFRASTRUCTURE IS BELOW
// ========================================================u

    static final String REGEXP = "[- .:,]+"; // for splitting into words

    private BufferedReader reader;

    @Before
    public void z_setUpBufferedReader() throws IOException {
        reader = Files.newBufferedReader(
                Paths.get("SonnetI.txt"), StandardCharsets.UTF_8);
    }

    @After
    public void z_closeBufferedReader() throws IOException {
        reader.close();
    }
}