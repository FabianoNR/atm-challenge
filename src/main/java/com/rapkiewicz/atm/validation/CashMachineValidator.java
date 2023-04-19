package com.rapkiewicz.atm.validation;

import com.rapkiewicz.atm.exception.CashMachineValidateException;
import com.rapkiewicz.atm.model.CashAmount;

public class CashMachineValidator {
	
	public void validate(CashAmount amount) throws CashMachineValidateException{
		
		String message = "Valor informado para saque é inválido. Esse caixa eletrônico "
				+ "trabalha com as seguintes cédulas: R$ 10, R$ 20, R$ 50 ou R$ 100";
		
		if( amount == null || amount.getValue().equals(0)) {
			throw new CashMachineValidateException(message);
		}else if( amount.getValue() % 10 > 0 ){
			throw new CashMachineValidateException(message);
		}
		
	}

}
