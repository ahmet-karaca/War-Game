import java.io.IOException;

public class Main {
    public static String initialsFile;
    public static String commandsFile;
    public static String outputFile;

    public static void main(String[] args) throws IOException {
        initialsFile = args[0];
        commandsFile = args[1];
        outputFile = args[2];

        Commands.command();
    }
}
