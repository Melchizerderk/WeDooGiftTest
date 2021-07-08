package Level3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;

public abstract class CardDistribution {

    public static File inputFile = null;
    FindJsonData findJsonData;
    private static final String BALANCE = "balance";
    private static final String ID = "id";
    private static final String AMOUNT = "amount";
    protected Instant endDate;
    protected static JSONObject jsonObject;

    CardDistribution ()
    {
        findJsonData = new FindJsonData();
    }

    private void getJsonObject()
    {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(inputFile.getAbsolutePath()));
            this.jsonObject = (JSONObject) obj;
        }catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    protected void distributeCard(String companyThatDistribute, Long userId, Integer amount, Long walletType)
    {
        if (jsonObject == null)
        {
            getJsonObject();
        }
        boolean companyExist = false;
        try {
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
                        Long walletId = findWalletId(jsonObject, walletType);
                        if (walletId != null && updateUserBalance(users, company, amount, userId, walletId))
                        {
                            updateDistribution(jsonObject, amount, company, userId, walletId);
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

    private Long findWalletId(JSONObject jsonObject, Long walletType)
    {
        JSONArray wallets = findJsonData.getJsonArray("wallets", jsonObject);
        Iterator itr = wallets.iterator();
        while (itr.hasNext()) {
            JSONObject wallet = (JSONObject) itr.next();
            if (findJsonData.getJsonLong(ID, wallet).equals(walletType))
            {
                return findJsonData.getJsonLong(ID, wallet);
            }
        }
        System.out.println("Could not find wallet : " + walletType + ".");
        return null;
    }

    protected boolean updateUserBalance(JSONArray users, JSONObject company, Integer amount, Long userId, Long walletId)
    {
        Iterator itr = users.iterator();
        while (itr.hasNext())
        {
            JSONObject user = (JSONObject) itr.next();
            if (findJsonData.getJsonLong(ID, user).equals(userId))
            {
                JSONArray balance = findJsonData.getJsonArray(BALANCE, user);
                if (balance.isEmpty())
                {
                    balance.add(addNewWallet(walletId, amount));
                }
                else {
                    boolean walletIdFound = false;
                    Iterator itr2 = balance.iterator();
                    while (itr2.hasNext())
                    {
                        JSONObject balanceValue = (JSONObject) itr2.next();
                        if (findJsonData.getJsonLong("wallet_id", balanceValue).equals(walletId))
                        {
                            walletIdFound = true;
                            balanceValue.put(AMOUNT, (Long) balanceValue.get(AMOUNT) + amount);
                        }
                    }
                    if (!walletIdFound)
                    {
                        balance.add(addNewWallet(walletId, amount));
                    }
                }
                company.put(BALANCE, (Long) company.get(BALANCE) - amount);
                return true;
            }
        }
        System.out.println("The user : " + userId + " does not exist");
        return false;
    }

    private LinkedHashMap<String, Object> addNewWallet(Long walletId, Integer amount)
    {
        LinkedHashMap<String, Object> balanceValueMap = new LinkedHashMap<>();
        balanceValueMap.put("wallet_id", walletId);
        balanceValueMap.put(AMOUNT, amount);
        return balanceValueMap;
    }

    protected void updateDistribution(JSONObject jsonObject, Integer amount, JSONObject company, Long userId, Long walletId)
    {
        JSONArray distributions = findJsonData.getJsonArray("distributions", jsonObject);
        JSONArray newDistribution = new JSONArray();
        LinkedHashMap<String, Object> informations = new LinkedHashMap<>();
        informations.put(ID, distributions.size() + 1);
        informations.put(AMOUNT, amount);

        DateTimeFormatter stf = DateTimeFormatter.ofPattern("yyyy/MM/dd").withZone(ZoneId.systemDefault());
        LocalDateTime now = LocalDateTime.now();
        informations.put("start_date", stf.format(now));
        informations.put("end_date", stf.format(endDate));
        informations.put("company_id", findJsonData.getJsonLong(ID, company));
        informations.put("user_id", userId);

        newDistribution.add(informations);

        distributions.add(newDistribution);
    }

    public static void writeOutput()
    {
        try
        {
            jsonObject.remove("wallets");
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            mapper.writeValue(new File("output.json"), jsonObject);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        System.out.println("The output file has been updated");
    }

    protected void setEndDate(Instant endOfValidity)
    {
        this.endDate = endOfValidity;
    }

    protected void setInputFile(File inputFile)
    {
        this.inputFile = inputFile;
    }
}
