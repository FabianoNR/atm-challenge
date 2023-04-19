package com.rapkiewicz.atm.model.handler;

import com.rapkiewicz.atm.exception.CashMachineException;
import com.rapkiewicz.atm.model.CashAmount;

public interface CashHandler {
	
	void dispense(CashAmount amount, CashCollector collector)
			throws CashMachineException;
	
	CashHandler appendHandler(CashHandler nextHandler);

}
