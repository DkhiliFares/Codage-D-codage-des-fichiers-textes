package codage;

import java.util.HashMap;

public class FanoShanon {
    public static String encode(String text) {
        HashMap<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : text.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        StringBuilder encoded = new StringBuilder();
        for (char c : text.toCharArray()) {
            int frequency = frequencyMap.get(c);
            String binaryCode = Integer.toBinaryString(frequency);
            encoded.append(binaryCode);
        }
        return encoded.toString();
    }

    public static String decode(String binary) {
        StringBuilder decoded = new StringBuilder();
        int i = 0;
        while (i < binary.length()) {
            StringBuilder binaryCode = new StringBuilder();
            while (i < binary.length() && binary.charAt(i) == '1') {
                binaryCode.append(binary.charAt(i));
                i++;
            }
            int frequency = binaryCode.length();
            decoded.append((char) frequency);
            i++;
        }
        return decoded.toString();
    }

}


