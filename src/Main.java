import dnl.utils.text.table.TextTable;
import org.apache.commons.lang.ArrayUtils;

import java.io.*;
import java.util.*;

//E:\University\3course\AppliedMath\lab2\program\src\War_and_Peace
public class Main {

    public static void main(String[] args) throws IOException {
        char[] text = connectToFile();

        TextAnalyzer textAnalyzer = new TextAnalyzer(text);
        textAnalyzer.analyse();

        HuffmanCoding huffmanMethod = new HuffmanCoding(textAnalyzer.getProbabilityOfSymbols());
        Map<Character, String> huffmanSymbolsCode = huffmanMethod.coding();

        char[] huffmanCode = codingText(huffmanSymbolsCode, text);

        TextAnalyzer huffmanCodeAnalaser = new TextAnalyzer(huffmanCode);
        huffmanCodeAnalaser.analyse();

       System.out.println("-----huffmanSymbolsCode-----");
       printResults(huffmanCodeAnalaser, huffmanSymbolsCode, textAnalyzer.getProbabilityOfSymbols());


        ShannonFanoCoding shannonFanoCoding = new ShannonFanoCoding(textAnalyzer.getProbabilityOfSymbols());
        Map<Character, String> shannonSymbolsCode = shannonFanoCoding.coding();

        char[] shannonCode = codingText(shannonSymbolsCode, text);

        TextAnalyzer shannonCodeAnalaser = new TextAnalyzer(shannonCode);
        shannonCodeAnalaser.analyse();


        System.out.println("-----shannonSymbolsCode-----");
        printResults(shannonCodeAnalaser, shannonSymbolsCode, textAnalyzer.getProbabilityOfSymbols());


    }


    private static char[] codingText(Map<Character, String> symbolsCode, char[] text) throws IOException {
        char [] code = new char[0];
        for (char symbol: text) {
            char[] symbolCode = symbolsCode.get(symbol).toCharArray();
            char[] newCode = new char[code.length+symbolCode.length];
            newCode = ArrayUtils.addAll(code, symbolCode);
            code = newCode;
        }
        return code;
    }


    private static char[] connectToFile(){
        String fileName = "error";
        byte[] buffer = new byte[0];
        char[] text;
        boolean connection = false;
        Scanner in = new Scanner(System.in);

        while (!connection) {
            System.out.println("Enter file name for analysis:");
            try {
                fileName = in.nextLine();
                FileInputStream fis = new FileInputStream(fileName);
                buffer = new byte[fis.available()];
                fis.read(buffer, 0, fis.available());
                connection = true;
                fis.close();
            } catch (FileNotFoundException e) {
                System.out.println("!!!File with name \"" + fileName + "\" not found!!!");
                System.out.println("!!!Try again!!!");
                connection = false;
            } catch (IOException e) {
                System.out.println("!!!Error connecting to file with name \"" + fileName + "\"!!!");
                System.out.println("!!!Try again!!!");
                connection = false;
            } catch (NoSuchElementException e){
                System.out.println("-----Closing program-----");
                System.exit(0);
            }
        }

        text = new char[buffer.length];

        for (int i = 0; i<buffer.length; i++) {
            text[i] = (char) buffer[i];
        }
        System.out.println(fileName);
        return text;
    }

    private static void printResults(Map<Character, String> codes, Map<Character, Double> probabilityOfSymbols){
        for (Map.Entry<Character, String> value: codes.entrySet()) {
            String key;
            if (value.getKey() == 0){
                key = "\"\"" + "\t";
            } else if (value.getKey() == 13){
                key = "\\r" + "\t";
            } else if (value.getKey() == 10){
                key = "\\n" + "\t";
            } else if (value.getKey() == 32){
                key = "space";
            } else {
                key = String.valueOf(value.getKey()) + "\t";
            }
            System.out.println(key + "\t" + probabilityOfSymbols.get(value.getKey()) + "\t" + value.getValue().length() + "\t"  + value.getValue());
        }
    }


    private static void printResults(TextAnalyzer analyzer, Map<Character, String> codes, Map<Character, Double> probabilityOfSymbols){


        System.out.println("Text entropy: "+analyzer.getTextEntropy());
        System.out.println("-----Characters of symbols-----");

        String[] header = {"Symbol", "Probability", "Code", "Length" };
        String[][] data = new String[codes.size()][4];
        int i = 0;

        for (Map.Entry<Character, String> value: codes.entrySet()) {
            String key;
            if (value.getKey() == 0){
                key = "\"\"";
            } else if (value.getKey() == 13){
                key = "\\r";
            } else if (value.getKey() == 10){
                key = "\\n";
            } else if (value.getKey() == 32){
                key = "space";
            } else {
                key = String.valueOf(value.getKey());
            }
            data[i][0] = key;
            data[i][1] = String.valueOf(probabilityOfSymbols.get(value.getKey()));
            data[i][2] = value.getValue();
            data[i][3] = String.valueOf(value.getValue().length());
            i++;
        }

        TextTable tt1 = new TextTable(header,data);
        tt1.printTable();
        System.out.println();

    }


}




