package com.rapkiewicz.atm.model;

import java.util.List;

import com.rapkiewicz.atm.validation.CashMachineValidator;
import org.springframework.beans.factory.annotation.Autowired;

import com.rapkiewicz.atm.exception.CashMachineException;
import com.rapkiewicz.atm.model.handler.CashHandler;

public class CashMachine {

	private CashHandler cashHandler;
	private CashMachineValidator validator;

	@Autowired
	public CashMachine(CashHandler cashHandler, CashMachineValidator validator) {		
		this.cashHandler = cashHandler;
		this.validator = validator;
		
	}
	
	public List<WadOfCash> withdraw(CashAmount amount) throws CashMachineException {
		validator.validate(amount);
		
		CashMachineCollector cashCollector = new CashMachineCollector();
		
		cashHandler.dispense(amount, cashCollector);
		
		return cashCollector.getWadOfCash();
	}
}
