package Level3;

import java.io.File;
import java.nio.file.Paths;

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
