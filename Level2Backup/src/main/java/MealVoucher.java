import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.LinkedHashMap;

public class MealVoucher extends CardDistribution {

    MealVoucher(String company, Long user, Integer amount, Long walletType, File inputFile)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 365);
        Integer month = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.MONTH, 1);
        if (month > 1)
        {
            calendar.add(Calendar.YEAR, 1);
        }
        Integer year = calendar.get(Calendar.YEAR);
        if (year % 4 == 0)
        {
            if (year % 100 == 0)
            {
                if (year % 400 == 0)
                {
                    calendar.set(Calendar.DAY_OF_MONTH, 29);
                }
                else
                {
                    calendar.set(Calendar.DAY_OF_MONTH, 28);
                }
            }
            else
            {
                calendar.set(Calendar.DAY_OF_MONTH, 29);
            }
        }
        else
        {
            calendar.set(Calendar.DAY_OF_MONTH, 28);
        }
        setEndDate(calendar.toInstant());
        setInputFile(inputFile);
        distributeCard(company, user, amount, walletType);
    }
}
