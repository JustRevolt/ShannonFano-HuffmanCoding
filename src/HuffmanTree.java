import java.util.*;

class HuffmanTree implements Comparable<HuffmanTree> {

    Node root;

    private HuffmanTree(Node root) {
        this.root = root;
    }

    static class Node {
        // Частота символа
        Double probability;
        // Символ
        Character character;
        // Левый потомок узла
        Node leftChild;
        // Правый потомок узла
        Node rightChild;

        Node(Double priority, Character character) {
            this.probability = priority;
            this.character = character;
        }

        Node(HuffmanTree left, HuffmanTree right) {
            probability = left.root.probability + right.root.probability;
            leftChild = left.root;
            rightChild = right.root;
        }

        @Override
        public String toString() {
            return "[id=" + probability + ", data =" + character + "]";
        }
    }

    static HuffmanTree buildHuffmanTree(Map<Character, Double> probabilityOfSymbols) {
        PriorityQueue<HuffmanTree> trees = new PriorityQueue<>();
        Set<Character> value = probabilityOfSymbols.keySet();
        for (Character i : value) {
            trees.offer(new HuffmanTree(new Node(probabilityOfSymbols.get(i), i)));
        }
        while (trees.size() > 1) {
            HuffmanTree a = trees.poll();
            HuffmanTree b = trees.poll();
            trees.offer(new HuffmanTree(new Node(a, b)));
        }
        return trees.poll();
    }


    @Override
    public int compareTo(HuffmanTree tree) {
        return (int) (root.probability*1000) - (int) (tree.root.probability*1000);
    }
}
