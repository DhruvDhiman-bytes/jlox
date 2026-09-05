
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Lox {
    static boolean hadError = false;
    public static void main(String [] args) throws IOException {

        if(args.length > 1) { // if there is more information given to the command line
            System.out.println("Usage: jlox [script]");
            System.exit(64);
        }
        else if (args.length == 1) { // when there is only the file name given to run the code file
            runFile(args[0]);
        }
        else { // when we need to directly talk with the interpreter, meaning write the code directly in the interpreter
            runPrompt();
        }
    }

    // function that excepts the code file when given in the command
    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));

        // if statement if the code file had syntax error
        if(hadError) {
            System.exit(65);
        }
    }

    // function for coding directly in the interpreter
    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for(;;) {
            System.out.print(":->");
            String line = reader.readLine();
            if(line == null) {
                break;
            }
            run(line);

            hadError = false;
        }
    }

    // function that does the job of running either the file or the prompt
    private static void run(String source) {
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        for(Token token : tokens) {
            System.out.println(token);
        }
    }

    /*
        ----- THE SECTION OF ERROR HANDLING --------
    */

    static void error(int line, String message) {
        report(line, "", message);
    }

    private static void report (int line, String where, String message) {
        System.err.println("[line "+ line +" ]  Error "+where+": "+message);
        hadError = true;
    }
}
