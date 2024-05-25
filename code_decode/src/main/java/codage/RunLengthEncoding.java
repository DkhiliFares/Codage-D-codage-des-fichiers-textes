package codage;

public class RunLengthEncoding {
    public static String encode(String text) {
        StringBuilder encoded = new StringBuilder();
        int count = 1;
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) == text.charAt(i - 1)) {
                count++;
            } else {
                encoded.append(count).append(text.charAt(i - 1));
                count = 1;
            }
        }
        encoded.append(count).append(text.charAt(text.length() - 1));
        return encoded.toString();
    }

    public static String decode(String binary) {
        StringBuilder decoded = new StringBuilder();
        for (int i = 0; i < binary.length(); i += 2) {
            int count = Integer.parseInt(String.valueOf(binary.charAt(i)));
            char character = binary.charAt(i + 1);
            for (int j = 0; j < count; j++) {
                decoded.append(character);
            }
        }
        return decoded.toString();
    }
}



