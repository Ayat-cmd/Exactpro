package com.exactpro.surveillancesystem.factories;

import com.exactpro.surveillancesystem.entities.Price;
import static com.exactpro.surveillancesystem.utils.DateTimeUtils.convertDateFormat;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class PricesFactory implements EntityFactory<Price>{
    @Override
    public List<Price> createEntities(List<String[]> rawData) throws ParseException {
        List<Price> resultPrices = new ArrayList<>(rawData.size());
        for (String[] data : rawData) {
            Price prices = new Price();
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
