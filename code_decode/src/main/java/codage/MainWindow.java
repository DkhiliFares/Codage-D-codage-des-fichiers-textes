package codage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MainWindow extends JFrame {
    private JComboBox<String> algorithmComboBox;
    private JButton encodeButton, decodeButton, fileButton, fileOperationButton;
    private JTextArea inputTextArea, resultTextArea, statsTextArea;

    public MainWindow() {
        setTitle("Text Encoder/Decoder");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                int w = getWidth();
                int h = getHeight();
                Color color1 = new Color(247, 204, 204); // Light pink
                Color color2 = new Color(230, 184, 190); // Light rose
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
                g2d.dispose();
            }
        };
        mainPanel.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.setOpaque(false); // Make the panel transparent
        JLabel algorithmLabel = new JLabel("Algorithm:");
        algorithmComboBox = new JComboBox<>(new String[]{"Huffman", "RunLengthEncoding", "FanoShanon"});
        topPanel.add(algorithmLabel);
        topPanel.add(algorithmComboBox);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        encodeButton = new JButton("Encode");
        decodeButton = new JButton("Decode");
        fileButton = new JButton("Choose File");
        fileOperationButton = new JButton("Encode/Decode Files");
        buttonPanel.add(encodeButton);
        buttonPanel.add(decodeButton);
        buttonPanel.add(fileButton);
        buttonPanel.add(fileOperationButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        JPanel textPanel = new JPanel(new GridLayout(1, 2));
        textPanel.setOpaque(false);
        inputTextArea = new JTextArea();
        JScrollPane inputScrollPane = new JScrollPane(inputTextArea);
        textPanel.add(inputScrollPane);

        resultTextArea = new JTextArea();
        JScrollPane resultScrollPane = new JScrollPane(resultTextArea);
        textPanel.add(resultScrollPane);

        mainPanel.add(textPanel, BorderLayout.CENTER);

        statsTextArea = new JTextArea();
        statsTextArea.setEditable(false);
        JScrollPane statsScrollPane = new JScrollPane(statsTextArea);
        mainPanel.add(statsScrollPane, BorderLayout.EAST);

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

        fileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
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

        fileOperationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FileOperationWindow fileOperationWindow = new FileOperationWindow();
                fileOperationWindow.setVisible(true);
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
        statsTextArea.setText(stats);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Set Nimbus look and feel for modern UI
                try {
                    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new MainWindow().setVisible(true);
            }
        });
    }
}
