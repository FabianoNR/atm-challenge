package com.rapkiewicz.atm.config;

import java.util.Optional;

import com.rapkiewicz.atm.model.handler.HundredNoteHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rapkiewicz.atm.model.CashMachine;
import com.rapkiewicz.atm.model.handler.FiftyNoteHandler;
import com.rapkiewicz.atm.model.handler.CashHandler;
import com.rapkiewicz.atm.model.handler.TenNoteHandler;
import com.rapkiewicz.atm.model.handler.TwentyNoteHandler;
import com.rapkiewicz.atm.validation.CashMachineValidator;

@Configuration
public class CashMachineConfig {

	@Value("${banknotes-limit.hundred}")
	private Integer hundredLimit;
	
	@Value("${banknotes-limit.fifty}")
	private Integer fiftyLimit;
	
	@Value("${banknotes-limit.twenty}")
	private Integer twentyLimit;
	
	@Value("${banknotes-limit.ten}")
	private Integer tenLimit;
	
	@Bean
	public CashMachine createCashMachine() {
		CashHandler cashHandler = new HundredNoteHandler(Optional.ofNullable(hundredLimit))
				.appendHandler(new FiftyNoteHandler(Optional.ofNullable(fiftyLimit)))
				.appendHandler(new TwentyNoteHandler(Optional.ofNullable((twentyLimit)))
				.appendHandler(new TenNoteHandler(Optional.ofNullable(tenLimit))));
		
		return new CashMachine(cashHandler, new CashMachineValidator());
	}
}
