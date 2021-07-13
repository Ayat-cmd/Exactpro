package com.exactpro.surveillancesystem.factories;

import com.exactpro.surveillancesystem.entities.Prices;
import static com.exactpro.surveillancesystem.utils.ConvertDateTimeUtils.convertDateFormat;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PricesFactory implements EntityFactory<Prices>{
    @Override
    public List<Prices> createEntities(List<String[]> rawData) throws ParseException {
        List<Prices> resultPrices = new ArrayList<>(rawData.size());
        for (String[] data : rawData) {
            Prices prices = new Prices();
            prices.setInstrumentName(data[0]);
            prices.setDate(convertDateFormat(data[1]));
            prices.setCurrency(data[2]);
            prices.setAvgPrice(Double.parseDouble(data[3]));
            prices.setNetAmountPerDay(Double.parseDouble(data[4]));
            resultPrices.add(prices);
        }
        return resultPrices;
    }
}
