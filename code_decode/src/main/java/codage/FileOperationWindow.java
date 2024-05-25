package codage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

class FileOperationWindow extends JFrame {
    private JComboBox<String> algorithmComboBox;
    private JButton encodeButton, decodeButton, chooseFileButton, saveButton;
    private JTextArea inputTextArea, resultTextArea;
    private File selectedFile;

    public FileOperationWindow() {
        setTitle("File Encoder/Decoder");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel(new FlowLayout());
        JLabel algorithmLabel = new JLabel("Algorithm:");
        algorithmComboBox = new JComboBox<>(new String[]{"Huffman", "RunLengthEncoding", "FanoShanon"});
        topPanel.add(algorithmLabel);
        topPanel.add(algorithmComboBox);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        encodeButton = new JButton("Encode");
        decodeButton = new JButton("Decode");
        chooseFileButton = new JButton("Choose File");
        saveButton = new JButton("Save Result");
        buttonPanel.add(encodeButton);
        buttonPanel.add(decodeButton);
        buttonPanel.add(chooseFileButton);
        buttonPanel.add(saveButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        JPanel textPanel = new JPanel(new GridLayout(1, 2));
        inputTextArea = new JTextArea();
        JScrollPane inputScrollPane = new JScrollPane(inputTextArea);
        textPanel.add(inputScrollPane);

        resultTextArea = new JTextArea();
        JScrollPane resultScrollPane = new JScrollPane(resultTextArea);
        textPanel.add(resultScrollPane);
        mainPanel.add(textPanel, BorderLayout.CENTER);

        add(mainPanel);

        encodeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
                String s = inputTextArea.getText();
                if (s.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "InputTextField is empty");
                } else {
                    String c;
                    switch (selectedAlgorithm) {
                        case "Huffman":
                            c = Huffman.encode(s);
                            break;
                        case "RunLengthEncoding":
                            c = RunLengthEncoding.encode(s);
                            break;
                        default:
                            c = FanoShanon.encode(s);
                            break;
                    }
                    resultTextArea.setText(c);
                    computeStatistics(s, c);
                }
            }
        });

        decodeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
                String s = inputTextArea.getText();
                if (s.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "InputTextField is empty");
                } else {
                    String c;
                    try {
                        switch (selectedAlgorithm) {
                            case "Huffman":
                                c = Huffman.decode(s);
                                break;
                            case "RunLengthEncoding":
                                c = RunLengthEncoding.decode(s);
                                break;
                            default:
                                c = FanoShanon.decode(s);
                                break;
                        }
                        resultTextArea.setText(c);
                        computeStatistics(s, c);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);
                    }
                }
            }
        });

        chooseFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
                        StringBuilder text = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            text.append(line).append("\n");
                        }
                        inputTextArea.setText(text.toString());
                        reader.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedFile == null) {
                    JOptionPane.showMessageDialog(null, "No file selected.");
                    return;
                }
                String result = resultTextArea.getText();
                if (result.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No result to save.");
                    return;
                }
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setSelectedFile(new File(selectedFile.getParent(), "encoded_decoded_" + selectedFile.getName()));
                int resultDialog = fileChooser.showSaveDialog(null);
                if (resultDialog == JFileChooser.APPROVE_OPTION) {
                    File outputFile = fileChooser.getSelectedFile();
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                        writer.write(result);
                        JOptionPane.showMessageDialog(null, "File saved successfully.");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error saving file: " + ex.getMessage());
                    }
                }
            }
        });
    }

    private void computeStatistics(String originalText, String encodedText) {
        int originalLength = originalText.length() * 8; // Length of original text in bits
        int encodedLength = encodedText.length(); // Length of encoded text in characters

        // Calculate compression ratio
        double compressionRatio = (double) originalLength / encodedLength;

        // Calculate average bits per character
        double bitsPerCharacter = (double) encodedLength / originalText.length();

        // Display statistics
        String stats = String.format("Original Text Length: %d bits\nEncoded Text Length: %d bits\nCompression Ratio: %.2f\nAverage Bits per Character: %.2f",
                originalLength, encodedLength, compressionRatio, bitsPerCharacter);
        JOptionPane.showMessageDialog(null, stats, "Statistics", JOptionPane.INFORMATION_MESSAGE);
    }
}
