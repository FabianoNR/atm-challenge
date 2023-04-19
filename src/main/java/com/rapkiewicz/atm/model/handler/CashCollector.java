package com.rapkiewicz.atm.model.handler;

import com.rapkiewicz.atm.model.CashAmount;

public interface CashCollector {
	
	public void addCash(CashAmount cash);
}
