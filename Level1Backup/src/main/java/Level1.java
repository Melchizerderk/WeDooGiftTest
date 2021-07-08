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
            distributeGiftCard("Wedoogift", new Long(1), 200);
        }
        else
        {
            System.out.println("File : " + inputFile.getAbsolutePath() + " does not exist");
        }
    }

    public void distributeGiftCard(String companyThatDistribute, Long userId, Integer amount)
    {
        boolean companyExist = false;
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(inputFile.getAbsolutePath()));
            JSONObject jsonObject = (JSONObject) obj;
            //convert Object to JSONObject
            JSONArray companies = findJsonData.getJsonArray("companies", jsonObject);
            JSONArray users = findJsonData.getJsonArray("users", jsonObject);
            Iterator itr = companies.iterator();
            while (itr.hasNext()) {
                JSONObject company = (JSONObject) itr.next();
                if (findJsonData.getJsonString("name", company).equals(companyThatDistribute))
                {
                    System.out.println("Company found, updating the balance");
                    companyExist = true;
                    Long companyBalance = findJsonData.getJsonLong(BALANCE, company);
                    if (companyBalance > amount)
                    {
                        if (updateUserBalance(users, company, amount, userId))
                        {
                            updateDistribution(jsonObject, amount, company, userId);
                            writeOutput(jsonObject);
                        }
                    }
                    else
                    {
                        System.out.println("The company balance : " + companyBalance + " does not allow the distribution of : " + amount + " euros to the user : " + userId + ".");
                    }
                }
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        if (companyExist == false)
        {
            System.out.println("The company : " + companyThatDistribute + " does not exist in the input file");
        }
    }

    private boolean updateUserBalance(JSONArray users, JSONObject company, Integer amount, Long userId)
    {
        Iterator itr = users.iterator();
        while (itr.hasNext())
        {
            JSONObject user = (JSONObject) itr.next();
            if (findJsonData.getJsonLong("id", user).equals(userId))
            {
                user.put(BALANCE, (Long) user.get(BALANCE) + amount);
                company.put(BALANCE, (Long) company.get(BALANCE) - amount);
                return true;
            }
        }
        System.out.println("The user : " + userId + " does not exist");
        return false;
    }

    private void updateDistribution(JSONObject jsonObject, Integer amount, JSONObject company, Long userId)
    {
        JSONArray distributions = findJsonData.getJsonArray("distributions", jsonObject);
        JSONArray newDistribution = new JSONArray();
        LinkedHashMap<String, Object> informations = new LinkedHashMap<>();
        informations.put("id", distributions.size() + 1);
        informations.put("amount", amount);

        DateTimeFormatter stf = DateTimeFormatter.ofPattern("yyyy/MM/dd").withZone(ZoneId.systemDefault());
        LocalDateTime now = LocalDateTime.now();
        informations.put("start_date", stf.format(now));

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 365);
        informations.put("end_date", stf.format(calendar.toInstant()));
        informations.put("company_id", findJsonData.getJsonLong("id", company));
        informations.put("user_id", userId);

        newDistribution.add(informations);

        distributions.add(newDistribution);
    }

    private void writeOutput(JSONObject jsonObject)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            mapper.writeValue(new File("output.json"), jsonObject);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        System.out.println("The output file has been updated");
    }
}
