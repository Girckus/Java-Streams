import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CollectorsDemo3 {
	
    public static void main(String[] args) {  
    	List<Integer> list1 = IntStream.of(2, 4, 6, 8, 10, 12)
                                      .boxed()
                                      .collect(Collectors.filtering(i -> i % 4 == 0, Collectors.toList()));

    	System.out.println(list1);
    	
    	System.out.println();
    	System.out.println();
    	
    	List<Integer> list2 = Stream.of(List.of(1, 2, 3, 4), List.of(5, 6, 7, 8))
                                    .collect(Collectors.flatMapping( l -> l.stream()
                                                                           .filter(i -> i % 2 == 0),Collectors.toList()));

    	System.out.println(list2);
    	
    	System.out.println();
    	System.out.println();
    	
    	List<Integer> list3 = IntStream.range(1, 5)
                					  .boxed()
                					  .collect(Collectors.toUnmodifiableList());
    	
    	System.out.println(list3);
    	System.out.println(list3.getClass().getName());
    	
    	System.out.println();
    	System.out.println();
    	
    	Map<Integer, Double> map =
                IntStream.range(1, 5)
                         .boxed()
                         .collect(Collectors.toUnmodifiableMap(
                                 i -> i,
                                 i -> Math.pow(i, 3))
                         );
    	
        System.out.println(map);
        System.out.println(map.getClass().getTypeName());
    	
        System.out.println();
        System.out.println();
        
        Map<Integer, List<String>> map2 =
                Stream.of("rover", "joyful", "depth", "hunter")
                      .collect(Collectors.toUnmodifiableMap(
                              String::length,
                              List::of,
                              CollectorsDemo3::mergeFunction)
                      );
        System.out.println(map2);
        
        Set<Integer> set = IntStream.range(1, 5)
                					.boxed()
                					.collect(Collectors.toUnmodifiableSet());
        System.out.println(set);
        System.out.println(set.getClass().getTypeName());
        
        System.out.println();
        System.out.println();
        
        var result = Stream.of("Devoxx", "Voxxed Days", "Code One", "Basel One", "Angular Connect")
        		           .collect(Collectors.teeing(  Collectors.filtering(n -> n.contains("xx"), Collectors.toList()),
        		        		   						Collectors.filtering(n -> n.endsWith("One"), Collectors.toList()),
        		        		   						(List<String> list1, List<String> list2) -> List.of(list1, list2) ));
        System.out.println(result);
    }
    
    private static List<String> mergeFunction(List<String> l1, List<String> l2) {
        List<String> list = new ArrayList<>();
        list.addAll(l1);
        list.addAll(l2);
        return list;
    }
	
}