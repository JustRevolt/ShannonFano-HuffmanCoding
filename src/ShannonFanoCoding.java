import java.util.*;
import java.util.stream.Stream;

public class ShannonFanoCoding {

    private Map<Character, Double> probabilityOfSymbols = new LinkedHashMap<Character, Double>();
    private Map<Character, String> symbolsCode = new LinkedHashMap<Character, String>();

    public ShannonFanoCoding(Map<Character, Double> probabilityOfSymbols) {
        this.probabilityOfSymbols = probabilityOfSymbols;
    }

    Map<Character, String> coding() {
        getSymbolsCodes(probabilityOfSymbols);
        return symbolsCode;
    }

    private void getSymbolsCodes(Map<Character, Double> probabilityOfSymbols) {
        probabilityOfSymbols = sortByValue(probabilityOfSymbols);
        Map<Character, Double> reverseProbabilityOfSymbols = sortByValueReverse(probabilityOfSymbols);

        Set<Character> probabilityOfSymbolsSet = probabilityOfSymbols.keySet();
        Iterator<Character> iter =  probabilityOfSymbolsSet.iterator();
        Set<Character> reverseProbabilityOfSymbolsSet = reverseProbabilityOfSymbols.keySet();
        Iterator<Character> reverseIter =  reverseProbabilityOfSymbolsSet.iterator();

        int size = probabilityOfSymbols.size();
        int startSize = 0;
        int endSize = 0;
        Double startSum = 0.0;
        Double endSum = 0.0;

        Map<Character, Double> newOneMap = new LinkedHashMap<Character, Double>();
        Map<Character, Double> newZeroMap = new LinkedHashMap<Character, Double>();

        while (startSize + endSize != size){
            if (startSum > endSum){
                Character key = reverseIter.next();
                endSum += reverseProbabilityOfSymbols.get(key);
                String oneCode = symbolsCode.getOrDefault(key,"") + "1";
                symbolsCode.put(key,oneCode);

                newOneMap.put(key, reverseProbabilityOfSymbols.get(key));
                endSize += 1;

            } else {
                Character key = iter.next();
                startSum += probabilityOfSymbols.get(key);
                String zeroCode = symbolsCode.getOrDefault(key,"") + "0";
                symbolsCode.put(key,zeroCode);

                newZeroMap.put(key, probabilityOfSymbols.get(key));
                startSize += 1;

            }
        }
        if (newOneMap.size() > 1) getSymbolsCodes(newOneMap);
        if (newZeroMap.size() > 1) getSymbolsCodes(newZeroMap);
    }

    private static Map<Character, Double>  sortByValueReverse(Map<Character, Double> map) {
        for (Map.Entry<Character, Double> value: map.entrySet()){
            map.put(value.getKey(), -value.getValue());
        }
        Map<Character,Double> result = new LinkedHashMap<>();
        Stream<Map.Entry<Character, Double>> st = map.entrySet().stream();

        st.sorted(Comparator.comparing(e -> e.getValue()))
                .forEach(e ->result.put(e.getKey(),e.getValue()));

        for (Map.Entry<Character, Double> value: map.entrySet()){
            map.put(value.getKey(), -value.getValue());
        }

        for (Map.Entry<Character, Double> value: result.entrySet()){
            result.put(value.getKey(), -value.getValue());
        }

        return result;
    }

    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        Map<K, V> result = new LinkedHashMap<>();
        Stream<Map.Entry<K, V>> st = map.entrySet().stream();

        st.sorted(Comparator.comparing(e -> e.getValue()))
                .forEach(e -> result.put(e.getKey(), e.getValue()));

        return result;
    }
}