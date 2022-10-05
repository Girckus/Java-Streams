import java.util.DoubleSummaryStatistics;
import java.util.IntSummaryStatistics;
import java.util.LongSummaryStatistics;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class CollectorsDemo2 {

	public static void main(String[] args) {
		Random rand = new Random();
		
		Stream<Double> streamDouble = Stream.generate(() -> rand.nextDouble()).limit(10000);
		Stream<Long> streamLong = Stream.generate(() -> rand.nextLong()).limit(10000);
		Stream<Integer> streamInt = Stream.generate(() -> rand.nextInt()).limit(10000);
		
		DoubleSummaryStatistics statisticsDouble = streamDouble.collect(Collectors.summarizingDouble(Double::doubleValue));
		LongSummaryStatistics statisticsLong = streamLong.collect(Collectors.summarizingLong(Long::longValue));
		IntSummaryStatistics statisticsInt = streamInt.collect(Collectors.summarizingInt(Integer::intValue));
		
		
		System.out.println(statisticsDouble);
		System.out.println(statisticsLong);
		System.out.println(statisticsInt);
	}

}
