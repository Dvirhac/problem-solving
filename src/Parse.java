import java.io.*;
import java.sql.Array;
import java.util.ArrayList;

public class Parse {

    static ArrayList<String> load() {
        ArrayList<String> input = new ArrayList<>();
        BufferedReader reader;
        {
            try {
                reader = new BufferedReader(new FileReader("input.txt"));
                String line = reader.readLine();
                while (line != null) {
                    input.add(line);
                    line = reader.readLine();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return input;
    }
}
