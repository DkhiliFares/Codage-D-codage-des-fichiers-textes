package codage;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Huffman {
    private static class Node implements Comparable<Node> {
        char character;
        int frequency;
        Node left, right;

        Node(char character, int frequency) {
            this.character = character;
            this.frequency = frequency;
        }

        @Override
        public int compareTo(Node other) {
            return this.frequency - other.frequency;
        }
    }

    private static Node root; // Declare root as a class-level variable

    public static String encode(String text) {
        HashMap<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : text.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (char c : frequencyMap.keySet()) {
            pq.offer(new Node(c, frequencyMap.get(c)));
        }

        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            Node parent = new Node('\0', left.frequency + right.frequency);
            parent.left = left;
            parent.right = right;
            pq.offer(parent);
        }

        root = pq.poll(); // Assign root after building the Huffman tree

        HashMap<Character, String> codeMap = new HashMap<>();
        buildCodeMap(root, "", codeMap);

        StringBuilder encoded = new StringBuilder();
        for (char c : text.toCharArray()) {
            encoded.append(codeMap.get(c));
        }

        return encoded.toString();
    }

    private static void buildCodeMap(Node node, String code, HashMap<Character, String> codeMap) {
        if (node != null) {
            if (node.left == null && node.right == null) {
                codeMap.put(node.character, code);
            }
            buildCodeMap(node.left, code + "0", codeMap);
            buildCodeMap(node.right, code + "1", codeMap);
        }
    }

    public static String decode(String binary) {
        StringBuilder decoded = new StringBuilder();
        Node current = root;
        for (char bit : binary.toCharArray()) {
            if (bit == '0') {
                current = current.left;
            } else {
                current = current.right;
            }
            if (current.left == null && current.right == null) {
                decoded.append(current.character);
                current = root;
            }
        }
        return decoded.toString();
    }
}
