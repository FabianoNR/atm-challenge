package com.rapkiewicz.atm.model.handler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.rapkiewicz.atm.model.CashAmount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import com.rapkiewicz.atm.exception.CashMachineException;
import com.rapkiewicz.atm.model.WadOfCash;
import com.rapkiewicz.atm.model.CashMachineCollector;

class FiftyNoteHandlerTest {

private CashHandler nextHandler;	
	
	@BeforeEach
	public void setUp() {
		nextHandler = Mockito.mock(CashHandler.class);
	}
	
	@ParameterizedTest
	@MethodSource("provideAlotOfValidMultiplesOfFifty")
	public void givenInfinitiveNotesWhenCashAmountIsValidThenGetCash(
            CashAmount amount, List<WadOfCash> expected) throws CashMachineException {
		
		CashMachineCollector collector = new CashMachineCollector();
		getInfinitiveHandler().dispense(amount, collector);
		
		assertEquals(expected, collector.getWadOfCash());
		
		verify(nextHandler, never()).dispense(any(), any());
	}
	
	@Test
	public void givenFinitiveNotesWhenCashAmounIsGreaterThanLimitThenCallNext()
			throws CashMachineException {
		
		List<WadOfCash> expected = List.of(new WadOfCash(50, 2));
		
		CashMachineCollector collector = new CashMachineCollector();
		getFinitiveHandler(2).dispense(CashAmount.from(180), collector);
		
		assertEquals(expected, collector.getWadOfCash());
		
		verify(nextHandler, times(1)).dispense(CashAmount.from(80), collector);
	}
	
	@ParameterizedTest
	@MethodSource("provideDataToDifferentNotes")
	public void whentCashAmountNeedsDifferentNotesThenNextHandlerIsCalled(
			CashAmount amount, List<WadOfCash> expected, Integer difference) throws CashMachineException {
		
		CashMachineCollector collector = new CashMachineCollector();
		getInfinitiveHandler().dispense(amount, collector);
		
		assertEquals(expected, collector.getWadOfCash());
		
		if(difference > 0)
			verify(nextHandler, times(1)).dispense(CashAmount.from(difference), collector);
		else
			verify(nextHandler, never()).dispense(any(), any());
	}
	
	private CashHandler getInfinitiveHandler() {
		Optional<Integer> numberOfNotes = Optional.ofNullable(null);
		CashHandler infinitiveHandler = new FiftyNoteHandler(numberOfNotes);
		infinitiveHandler.appendHandler(nextHandler);
		return infinitiveHandler;
	}
	
	private CashHandler getFinitiveHandler(Integer numberOfNotes) {
		Optional<Integer> limit = Optional.ofNullable(numberOfNotes);
		CashHandler finitiveHandler = new FiftyNoteHandler(limit);
		finitiveHandler.appendHandler(nextHandler);
		return finitiveHandler;
	}

	private static Stream<Arguments> provideAlotOfValidMultiplesOfFifty() {
	    return Stream.of(
	      Arguments.of(CashAmount.from(50), List.of(new WadOfCash(50, 1))),
	      Arguments.of(CashAmount.from(500), List.of(new WadOfCash(50, 10))),
	      Arguments.of(CashAmount.from(5000), List.of(new WadOfCash(50, 100))),
	      Arguments.of(CashAmount.from(3700), List.of(new WadOfCash(50, 74))),
	      Arguments.of(CashAmount.from(47200), List.of(new WadOfCash(50, 944)))
	    );
	}
	
	private static Stream<Arguments> provideDataToDifferentNotes() {
	    return Stream.of(
	      Arguments.of(CashAmount.from(10), List.of(), 10),
	      Arguments.of(CashAmount.from(40), List.of(), 40),
	      Arguments.of(CashAmount.from(130), List.of(new WadOfCash(50, 2)), 30),
	      Arguments.of(CashAmount.from(300), List.of(new WadOfCash(50, 6)), 0)
	    );
	}

}
