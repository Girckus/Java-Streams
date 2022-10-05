import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class StreamDemo2 {

    public static void main(String[] args) {
    	fibonacci(20);
    	System.out.println();
    	generate(10);
    	System.out.println();
    	reducing1(10);
    	System.out.println();
    	reducing2(10);
    	System.out.println();
    	reducing3(10);
    	System.out.println();
    	collecting(100);
    	System.out.println();
    	builder(100);
    	System.out.println();
    	concat();
    	System.out.println();
    	flatMap1();
    	System.out.println();
    	flatMap2();
    	System.out.println();
    	flatMap3();
    	System.out.println();
    	map1();
    	System.out.println();
    	map2();
    	System.out.println();
    	map3();
    	System.out.println();
    	empty();
    	System.out.println();
    	forEachOrdered();
    	System.out.println();
    	max();
    	System.out.println();
    	min();
    	System.out.println();
    	of1();
    	System.out.println();
    	of2();
    	System.out.println();
    	toArray1();
    	System.out.println();
    	toArray2();
    	System.out.println();
    	collecting2();
    	System.out.println();
    	dropWhile();
    	System.out.println();
    	generate();
    	System.out.println();
    	iterate();
    	System.out.println();
    	iterate2();
    	System.out.println();
    	ofNullable();
		System.out.println();
    	peek();
		System.out.println();
    	takeWhile2();
		System.out.println();
    	mapMulti();
		System.out.println();
    	mapMultiToDouble();
		System.out.println();
    	mapMultiToInt();
		System.out.println();
    	mapMultiToLong();
    }

    private static void fibonacci(int max) {
    	Stream.iterate(new int[]{0,1}, f -> new int[]{f[1], f[0] + f[1]})
    	      .limit(max)
    	      .forEach(t -> System.out.print("(" + t[0] + "," + t[1] + ")"));
    }

    private static void generate(int max) {
    	Stream.generate(Math::random)
    		  .limit(max)
    		  .forEach(System.out::println);
    }
    
    private static void reducing1(int max) {
    	Double mult = Stream.generate(Math::random)
		      .limit(max)
		      .reduce((d1, d2) -> d1*d2)
		      .get();
    	
    	System.out.println(mult);
    }
    
    private static void reducing2(int max) {
    	Double mult = Stream.generate(Math::random)
		      .limit(max)
		      .reduce(1.0, (d1, d2) -> d1*d2);
    	
    	System.out.println(mult);
    }
    
    private static void reducing3(int max) {
    	Long mult = Stream.generate(Math::random)
		      .limit(max)
		      .reduce(0L, (l ,d) -> l + Double.valueOf(d*10).longValue(), (d1, d2) -> d1+d2);
    	System.out.println(mult);
    }
    
    private static void builder(int max) {
    	Stream.Builder<Integer> builder = Stream.builder();
    	
    	for(int i = 0; i < max; i++) builder.add(i);
    	
    	Stream<Integer> stream = builder.build();
    	stream.forEach(System.out::println);
    }
    
    private static void concat() {
    	List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> numbers1 = Arrays.asList(1, 2, 3, 4, 5);
        
        Stream<Integer> s = Stream.concat(numbers.stream(), numbers1.stream()); 
                         
        System.out.println(s.count());
    }
    
    private static void flatMap1() {
    	List<String> stringList = Arrays.asList("1.2","2.2","3","4","5");

        Double soma = stringList.stream()
			        			.flatMapToDouble(n-> DoubleStream.of(Double.parseDouble(n)) )
			        			.sum();
        
        System.out.println(soma);
    }
    
    private static void flatMap2() {
    	List<String> stringList = Arrays.asList("1","2","3","4","5");

        Integer soma = stringList.stream()
				                  .flatMapToInt(n-> IntStream.of(Integer.parseInt(n)) )
				                  .sum();
        
        System.out.println(soma);
    }
    
    private static void flatMap3() {
    	List<String> stringList = Arrays.asList("1","2","3","4","5");

    	Long soma = stringList.stream()
    						  .flatMapToLong(n-> LongStream.of(Long.parseLong(n)) )
					          .sum();
        
    	System.out.println(soma);
    }
    
    private static void map1() {
    	List<String> stringList = Arrays.asList("1.2","2.2","3","4","5");

        stringList.stream()
                  .mapToDouble(n-> Double.parseDouble(n) )
                  .filter(n-> n%2 == 0)
                  .forEach(System.out::println);
    }
    
    private static void map2() {
    	List<String> stringList = Arrays.asList("1","2","3","4","5");

        stringList.stream()
                  .mapToInt(n-> Integer.parseInt(n) )
                  .filter(n-> n%2 == 0)
                  .forEach(System.out::println);
    }
    
    private static void map3() {
    	List<String> stringList = Arrays.asList("1","2","3","4","5");

        stringList.stream()
	               .mapToLong(n-> Long.parseLong(n) )
	               .filter(n-> n%2 == 0)
	               .forEach(System.out::println);
    }
    
    private static void empty() {
    	Stream<String> s = Stream.empty();
        s.limit(10)
         .forEach(System.out::println); 
    }
    
    private static void forEachOrdered() {
    	List<String> stringList = Arrays.asList("2","1","3","4");

        stringList.stream()      
                  .forEachOrdered(System.out::println);
    }
    
    private static void max() {
    	List<String> stringList = Arrays.asList("2","1","3","4");

        Optional<String> m = stringList.stream().max(Comparator.reverseOrder());
        if(m.isPresent()){
        	System.out.println(m.get());  
        }else{
        	System.out.println("No Value");
        };
    }
    
    private static void min() {
    	List<String> stringList = Arrays.asList("2","1","3","4");

        Optional<String> m = stringList.stream().min(Comparator.reverseOrder());
        if(m.isPresent()){
        	System.out.println(m.get());  
        }else{
        	System.out.println("No Value");
        }
    }
    
    private static void of1() {
    	Stream<String> s = Stream.of("a");
        s.limit(10)
         .forEach(System.out::println);
    }
    
    private static void of2() {
    	Stream<String> s = Stream.of("a","b");
        s.limit(10)
         .forEach(System.out::println);
    }
    
    private static void toArray1() {
    	List<String> stringList = Arrays.asList("2","1","3","4");

        Object[] n = stringList.stream().toArray();
        System.out.println(Arrays.toString(n));
    }
    
    private static void toArray2() {
    	List<Integer> lista = new ArrayList<Integer>();
    	
    	for(int i=0; i<10; i++) lista.add(i);

    	Integer[] n = lista.stream().toArray(Integer[]::new);
    	System.out.println(Arrays.toString(n));
    }
    
    private static Boolean isPrime(List<Integer> list, Integer number) {
		int root = (int) Math.sqrt((double) number);
		return takeWhile(list, i -> i <= root).stream().noneMatch(p -> number % p == 0);
	}

	private static List<Integer> takeWhile(List<Integer> list, Predicate<Integer> number) {
		int i = 0;
		
		for(Integer num : list) {
			if(!number.test(num)) {
				return list.subList(0, i);
			}
			i++;
		}
		
		return list;
	}
    
    private static void collecting(int i) {
    	Stream<Integer> stream = IntStream.rangeClosed(2, i).boxed();
		
		Map<Boolean, List<Integer>> mapa = stream.collect(
			() -> { 
				HashMap<Boolean, List<Integer>> mapaSuplier = new HashMap<Boolean, List<Integer>>();
				mapaSuplier.put(true, new ArrayList<Integer>());
				mapaSuplier.put(false, new ArrayList<Integer>());
				
				return mapaSuplier;
			},
			(Map<Boolean, List<Integer>> mapaAccomulator, Integer number) -> {
				mapaAccomulator.get(isPrime(mapaAccomulator.get(true), number)).add(number);
			},
			(Map<Boolean, List<Integer>> map1, Map<Boolean, List<Integer>> map2) -> {
				map1.get(true).addAll(map2.get(true));
				map1.get(false).addAll(map2.get(false));
			}
		);
		
		System.out.println(mapa.get(true));
		System.out.println(mapa.get(false));
		
	}
    
    private static void collecting2() {
	    List<String> list = Arrays.asList("Mukesh", "Vishal", "Amar");
	    String result = list.stream()
	    		            .collect(StringBuilder::new,
	    		                    (response, element) -> response.append(" ").append(element),
	    		                    (response1, response2) -> response1.append(",").append(response2.toString()))
	    		            .toString();
	    System.out.println("Result: " + result);
	}
    
    private static void dropWhile() { 
        Stream<Integer> stream  = Stream.of(4, 4, 4, 5, 6, 7, 8, 9, 10); 
  
        List<Integer> list = stream.dropWhile(number -> (number / 4 == 1)) 
                                   .collect(Collectors.toList()); 
  
        System.out.println(list); 
    }
    
    private static void generate() { 
        Stream.generate(new Random()::nextInt) 
              .limit(5)
              .forEach(System.out::println);  
    }
    
    private static void iterate() {
        Stream<Long> tenNaturalNumbers = Stream.iterate(1L, n  ->  n  + 1)
        										.limit(10);

        tenNaturalNumbers.forEach(System.out::println);
    }
    
    private static void iterate2() {
        Stream<Long> tenNaturalNumbers = Stream.iterate(1L, n -> n != 10, n  ->  n  + 1);

        tenNaturalNumbers.forEach(System.out::println);
    }
    
    private static void ofNullable() { 
        // Create a stream with null 
        Stream<String> value1 = Stream.ofNullable(null); 
        
     // Create a stream with non null 
        Stream<String> value2 = Stream.ofNullable("Teste"); 
  
        // Print values 
        System.out.println("Values of Stream 1:"); 
        value1.forEach(System.out::println); 
        
        System.out.println("Values of Stream 2:"); 
        value2.forEach(System.out::println); 
    }
	
	private static void peek() {
        Stream<Integer> stream  = Stream.of(4, 4, 4, 5, 6, 7, 8, 9, 10); 
  
        List<Integer> list = stream.filer(number -> number > 5)
								   .peek(number -> System.out.println(number))
                                   .toList();
								   
		System.out.println(list); 
    }
	
	private static void takeWhile2() {
        Stream.of(1,2,3,4,5,6,7,8,9,10,9,8,7,6,5,4,3,2,1)
			   .takeWhile(i -> i < 4 )
			   .forEach(System.out::print);
    }
	
	private static void mapMulti() {
		Stream.of("Twix", "Snickers", "Mars")
			  .mapMulti((s, c) -> {
				c.accept(s.toUpperCase());
				c.accept(s.toLowerCase());
			  })
			  .forEach(System.out::println);
	}
	
	private static void mapMultiToDouble() {
		Stream.of(1.0, 2.0, 3.0);
			  .mapMultiToDouble((value, ic) -> {
				ic.accept(value);
				ic.accept(value + 0.1);
				ic.accept(value + 0.2);
			  })
			  .forEach(System.out::println);
	}
	
	private static void mapMultiToInt() {
		Stream.of(100, 200, 300);
			  .mapMultiToInt((value, ic) -> {
				ic.accept(value);
				ic.accept(value + 1);
				ic.accept(value + 2);
			  })
			  .forEach(System.out::println);
	}
	
	private static void mapMultiToLong() {
		Stream.of(100L, 200L, 300L);
			  .mapMultiToLong((value, ic) -> {
				ic.accept(value);
				ic.accept(value + 1L);
				ic.accept(value + 2L);
			  })
			  .forEach(System.out::println);
	}
}