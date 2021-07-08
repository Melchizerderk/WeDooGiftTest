package Level3;

import Level3.CardDistribution;

import java.io.File;
import java.util.Calendar;

public class MealVoucher extends CardDistribution {

    MealVoucher(String company, Long user, Integer amount, Long walletType, File inputFile)
    {
        Calendar calendar = Calendar.getInstance();
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
