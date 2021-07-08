package Level3;

import Level3.CardDistribution;

import java.io.File;
import java.util.Calendar;

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
