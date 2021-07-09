package com.exactpro.surveillancesystem.entities;

import java.util.List;

public class Instrument {
	private List<String[]> transactions;
	private List<String[]> Prices;

	public List<String[]> getTransactions() {
		return transactions;
	}

	public List<String[]> getPrices() {
		return Prices;
	}

	public void setTransactions(List<String[]> transactions) {
		this.transactions = transactions;
	}

	public void setPrices(List<String[]> prices) {
		Prices = prices;
	}
}
