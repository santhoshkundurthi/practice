package com.anz.testcase;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.anz.file.reader.CreditLimitFileReader;
import com.anz.file.util.PropertiesFileReader;
import com.anz.pojo.CreditEntities;
import com.anz.service.CalculatorCreditLimit;

public class JUnitProgramThow {
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Mock
	CreditLimitFileReader creditLimitFileReader = Mockito.mock(CreditLimitFileReader.class);
	@Mock
	PropertiesFileReader propertiesFileReader = Mockito.mock(PropertiesFileReader.class);
	@Mock
	CalculatorCreditLimit calculatorCreditLimit = Mockito.mock(CalculatorCreditLimit.class);

	@Mock
	static List<CreditEntities> CreditEntitiesList;

	@Test
	public void CalculatorCreditLimit() {
		// Mockito.when(calculatorCreditLimit.getBreachesCreditLimit(CreditEntitiesList)).
		// Mockito.when(calculatorCreditLimit.getBreachesCreditLimit(CreditEntitiesList));

	}

	@Test
	public void create_WithErrors() {
		CreditLimitFileReader creditLimitFileReader = Mockito.mock(CreditLimitFileReader.class);
		PropertiesFileReader propertiesFileReader = Mockito.mock(PropertiesFileReader.class);
		CalculatorCreditLimit calculatorCreditLimit = Mockito.mock(CalculatorCreditLimit.class);

//		Mockito.when(creditLimitFileReader.bindingResult.hasErrors()).thenReturn(true);
//		String result = create(scriptFile, bindingResult, new ModelMap());
//		Mockito.verify(bindingResult.hasErrors());
//		Mockito.verify(scriptFile.persist(), Mockito.never());
		// assertThat(result, is(equalTo("scriptfile/create")));
	}

	@Test
	public void shouldPersistFile() {
//		Mockito.when(result.hasErrors()).thenReturn(false);
//		Mockito.when(scriptFile.getId()).thenReturn("FILE_ID");
//
//		String output = create(scriptFile, result, modelMap);
//
//		Mockito.verify(scriptFile, times(1)).persist();
//		assertEquals("redirect:/scriptfile/FILE_ID", output);
	}

	@Test
	public void shouldHandleErrors() {
//		Mockito.when(result.hasErrors()).thenReturn(true);
//		Mockito.when(scriptFile.getId()).thenReturn("FILE_ID");
//
//		String output = create(scriptFile, result, modelMap);
//
//		Mockito.verify(modelMap, times(2)).addAttribute(Mockito.any(), Mockito.any());
		// assertEquals("scriptfile/create", output);
	}
}