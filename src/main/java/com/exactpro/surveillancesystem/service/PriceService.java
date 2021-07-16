package com.exactpro.surveillancesystem.service;

import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.exactpro.surveillancesystem.db.CassandraConnector;
import com.exactpro.surveillancesystem.entities.Price;

import java.time.ZoneId;
import java.util.Collection;

public class PriceService implements Service<Price>{
    private CassandraConnector cassandraConnector;

    public PriceService(CassandraConnector cassandraConnector) {
        this.cassandraConnector = cassandraConnector;
    }

    @Override
    public void saveAll(Collection<Price> prices) {
        for (Price price : prices) {
            PreparedStatement preparedStatement = cassandraConnector.preparedStatement("INSERT INTO ayat.prices (instrument_name, " +
                    "date, currency, avg_price,net_amount_per_day) VALUES (?,?,?,?,?);");

            BoundStatement boundStatement = preparedStatement.bind(price.getInstrumentName(),
                    price.getDate().atZone(ZoneId.systemDefault()).toLocalDate(),
                    price.getCurrency(),
                    price.getAvgPrice(),
                    price.getNetAmountPerDay());
            cassandraConnector.execute(boundStatement);
        }
    }
}
