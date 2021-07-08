import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sun.corba.se.impl.orbutil.ObjectWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;

public class Level1 {

    public static File inputFile = null;
    public static String fileName = "";
    private static FindJsonData findJsonData;
    private static final String BALANCE = "balance";

    Level1()
    {
        findJsonData = new FindJsonData();
    }

    public void LaunchLevel1Example() {
        fileName = "input.json";

        inputFile = new File(Paths.get(fileName).toAbsolutePath().toString());
        if (inputFile.exists())
        {
            new GiftCard("Wedoogift", new Long(1), 200, new Long(1), inputFile);
            new MealVoucher("Wedoogift", new Long(1), 200, new Long(2), inputFile);
            CardDistribution.writeOutput();
        }
        else
        {
            System.out.println("File : " + inputFile.getAbsolutePath() + " does not exist");
        }
    }
}
