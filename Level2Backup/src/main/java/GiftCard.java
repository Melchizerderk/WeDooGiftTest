import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.LinkedHashMap;

public class GiftCard extends CardDistribution {

    GiftCard(String company, Long user, Integer amount, Long walletType, File inputFile)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 365);

        setInputFile(inputFile);
        setEndDate(calendar.toInstant());
        distributeCard(company, user, amount, walletType);
    }
}
