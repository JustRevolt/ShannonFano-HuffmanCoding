import java.util.LinkedHashMap;
import java.util.Map;

class HuffmanCoding {

    private Map<Character, Double> probabilityOfSymbols = new LinkedHashMap<Character, Double>();
    private Map<Character, String> symbolsCode = new LinkedHashMap<Character, String>();

    HuffmanCoding(Map<Character, Double> probabilityOfSymbols){
        this.probabilityOfSymbols =probabilityOfSymbols;
    }

    Map<Character, String> coding(){
        HuffmanTree huffmanTree = HuffmanTree.buildHuffmanTree(probabilityOfSymbols);
        getSymbolsCodes(huffmanTree.root, "");
        return symbolsCode;
    }

     private void getSymbolsCodes(HuffmanTree.Node root, String code){
        if (root.leftChild != null){
            String newCode = code+"0";
            getSymbolsCodes(root.leftChild, newCode);
        }
        if (root.rightChild != null){
            String newCode = code+"1";
            getSymbolsCodes(root.rightChild, newCode);
        }
        if (root.leftChild == null && root.rightChild == null){
            symbolsCode.put(root.character, code);
        }
    }
}
