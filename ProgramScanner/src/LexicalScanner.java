import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LexicalScanner {
    private SymbolTable symbolTable;
    private String sourceProgramPath;
    private List<String> tokens;
    private List<Pair<String, Integer>> pif;

    public LexicalScanner(SymbolTable symbolTable, String sourceProgram, List<String> tokens) {
        this.symbolTable = symbolTable;
        this.sourceProgramPath = sourceProgram;
        this.tokens = tokens;
        pif = new ArrayList<>();
    }

    public void scan() {
        try {
            Scanner input = new Scanner(new File(this.sourceProgramPath));
            String line;
            int lineCount = 1;
            while(input.hasNextLine()) {
                line = input.nextLine();
                for(String token : this.lookForToken(line)) {
                    String classification = this.classifyToken(token);
                    //System.out.println(token + "->" + classification + "\n");
                    if(classification.compareTo("keyword") == 0) {
                        pif.add(new Pair<>(token, 0));
                    } else if (classification.compareTo("none") == 0) {
                        throw new RuntimeException("Lexical error at line: " + lineCount + " at token: " + token);
                    } else {
                        int location = this.symbolTable.search(token);
                        if(location == -1) {
                            this.symbolTable.add(token);
                        }
                        pif.add(new Pair<>(token, this.symbolTable.search(token)));
                    }
                }
                lineCount++;
            }
            System.out.println("Lexical correct!");
            this.saveOutput();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void saveOutput() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/pif.out"));
        for(Pair<String, Integer> p : this.pif) {
            writer.write("(" + p.getKey() + ", " + p.getValue() + ")");
            writer.newLine();
        }
        writer.close();
        writer = new BufferedWriter(new FileWriter("src/ST.out"));
        writer.write(this.symbolTable.toString());
        writer.close();
    }

    private List<String> lookForToken(String str) {
        List<String> foundTokens = new ArrayList<>();
        for(String tok : this.tokens) {
            if(str.contains(tok)) {
                foundTokens.add(tok);
                str = str.replace(tok, " ");
            }
        }
        String[] split = str.replaceAll("[ \\t]+", " ").split(" ");
        foundTokens.addAll(Arrays.stream(split).filter(chr -> chr.compareTo("") != 0).collect(Collectors.toList()));
        return foundTokens;
    }

    private String classifyToken(String tok) {
        if(this.tokens.contains(tok)) {
            return "keyword";
        } else if(tok.matches("(0\\.0)|([-]?\\d+\\.[1-9]\\d*)") || tok.matches("0|([-]?[1-9]\\d*)") || tok.matches("\"\"[A-Z]+\"\"") || tok.matches("''[A-Z0-9_]''")) {
            return "constant";
        } else if(tok.matches("[A-Z_][A-Z0-9_]*")) {
            return "identifier";
        } else return "none";
    }
}
