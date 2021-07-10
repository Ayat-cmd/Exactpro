package com.exactpro.surveillancesystem.factories;

import com.exactpro.surveillancesystem.entities.Prices;
import static com.exactpro.surveillancesystem.utils.ConvertDateTimeUtils.convertDateFormat;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class PricesFactory implements EntityFactory<Prices>{
    @Override
    public List<Prices> createEntities(List<String[]> rawData) throws ParseException {
        List<Prices> resultPrices = new ArrayList<>();
        for (String[] data : rawData) {
            Prices prices = new Prices();
            prices.setInstrument_name(data[0]);
            prices.setDate(convertDateFormat(data[1]));
            prices.setCurrency(data[2]);
            prices.setAvg_price(Double.parseDouble(data[3]));
            prices.setNet_amount_per_day(Double.parseDouble(data[4]));
            resultPrices.add(prices);
        }
        return resultPrices;
    }
}
