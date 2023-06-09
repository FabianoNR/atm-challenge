package com.rapkiewicz.atm.controller.dto;

public class CashDTO {
	
	private Integer value;
	private Integer amount;
	
	public CashDTO() {
		
	}
	
	public CashDTO(Integer value, Integer amount) {
		this.value = value;
		this.amount = amount;
	}
	
	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
}
