# Text Encoder/Decoder - Mini Project

## Description
This project is a Java application designed for encoding and decoding text using three different algorithms: Huffman, Run-Length Encoding (RLE), and Fano-Shannon. The application provides a user-friendly graphical interface (GUI) built with Swing, allowing users to input text, select an algorithm, and view the encoded/decoded results. Additionally, it supports file operations, enabling users to encode/decode text files and save the results.

## Features
- **Algorithms Supported**:
  - Huffman Encoding/Decoding
  - Run-Length Encoding (RLE)
  - Fano-Shannon Encoding/Decoding
- **User Interface**:
  - Intuitive GUI with text input/output areas.
  - Algorithm selection via dropdown menu.
  - Buttons for encoding, decoding, file operations, and saving results.
- **File Operations**:
  - Import text files for encoding/decoding.
  - Save encoded/decoded results to new files.
- **Performance Analysis**:
  - Calculates original and encoded text lengths (in bits).
  - Computes compression ratio and average bits per character.

## UML Diagrams
The project includes the following UML diagrams for design clarity:
- **Class Diagram**: Illustrates the structure of classes and their relationships.
- **Use Case Diagram**: Describes user interactions with the system.
- **Activity Diagram**: Outlines the workflow of encoding/decoding processes.

## Technologies Used
- **Programming Language**: Java
- **Libraries**: Swing (for GUI), Java Collections Framework (for data structures)
- **Development Tools**:
  - IDE: Eclipse
  - UML Design: Lucid.app

## How to Use
1. **Launch the Application**:
   - Run the `MainWindow` class to start the GUI.
2. **Encode/Decode Text**:
   - Enter text in the input area.
   - Select an algorithm from the dropdown menu.
   - Click "Encode" or "Decode" to process the text.
3. **File Operations**:
   - Click "Choose File" to import a text file.
   - Use "Encode/Decode Files" for file-based operations.
   - Save results using "Save Result".
4. **View Statistics**:
   - Performance metrics (compression ratio, bits per character) are displayed automatically after encoding/decoding.

## Code Structure
- **Main Classes**:
  - `Huffman.java`: Implements Huffman encoding/decoding.
  - `RunLengthEncoding.java`: Implements RLE.
  - `FanoShanon.java`: Implements Fano-Shannon encoding/decoding.
  - `MainWindow.java`: Handles the main GUI and user interactions.
  - `FileOperationWindow.java`: Manages file operations.
- **Packages**:
  - All classes are organized under the `codage` package.

## Performance Metrics
The application calculates:
- Original and encoded text lengths (in bits).
- Compression ratio: `(original length) / (encoded length)`.
- Average bits per character: `(encoded length) / (number of characters)`.

## Example Output
