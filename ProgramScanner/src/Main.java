import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            SymbolTable st = new SymbolTable();
            LexicalScanner scanner = new LexicalScanner(st, "src/input/p1err.txt", readTokens());
            scanner.scan();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static List<String> readTokens() throws FileNotFoundException {
        Scanner input = new Scanner(new File("src/input/token.in"));
        List<String> tokens = new ArrayList<>();
        while(input.hasNextLine()) {
            tokens.add(input.nextLine());
        }
        return tokens;
    }
}
