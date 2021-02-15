package com.anz.file.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Logger;

import com.anz.file.util.PropertiesFileReader;
import com.anz.pojo.CreditEntities;
import com.anz.service.CalculatorCreditLimit;

public class CreditLimitFileReader {
	static List<CreditEntities> entitiesList = new ArrayList<>();	
	private static final Logger LOGGER = Logger.getLogger(CreditLimitFileReader.class.getName());

	public static void main(String[] args) {
		{
			String line = "";
			String splitBy = ",";
			BufferedReader br = null;
			try {		
				
				Properties prop = PropertiesFileReader.readPropertiesFile("application.properties");			    
				LOGGER.info("-----------CreditLimitFileReader.Main() File reading started -----------");				
				// Read the file from application Property file 
				br = new BufferedReader(
						new FileReader(prop.getProperty("creditlimitfilelocation")));	
				while ((line = br.readLine()) != null) 
				{
					String[] creditEntities = line.split(splitBy); 
					CreditEntities obj = new CreditEntities();
					if (creditEntities != null) {
						try {
							// Read the each value of the file 
							Optional<String> entity = Optional.ofNullable(creditEntities[0]);
							Optional<String> parent = Optional.ofNullable(creditEntities[1]);
							Optional<Integer> limit = Optional.ofNullable(Integer.valueOf(creditEntities[2]));
							Optional<Integer> utilisation = Optional.ofNullable(Integer.valueOf(creditEntities[3]));
							// Create the PoJjo object and set the values 
							obj.setEntity(entity);
							obj.setParent(parent);
							obj.setLimit(limit);
							obj.setUtilisation(utilisation);
						} catch (ArrayIndexOutOfBoundsException e) {							
							LOGGER.info("ArrayIndexOutOfBoundsException while reading file "+e);

						} catch (NullPointerException e) {							
							LOGGER.info("NullPointerException while reading file "+e);

						}					
						
					}
                    // Completed  of object Pojo map and return as a list 
					entitiesList.add(obj);

				}
				LOGGER.info(" -----------CreditLimitFileReader.Main() File reading completed --------------");
			} catch (FileNotFoundException ex) {
				LOGGER.info("FileNotFoundException while reading file "+ex);
			} catch (IOException ex) {
				LOGGER.info("IOException while reading file "+ex);
			} finally {
				try {
					if (br != null)
						br.close();
				} catch (IOException ex) {
					LOGGER.info("IOException while closing  finally block "+ex);
				}
			}
           //Called Credit service logic 
			CalculatorCreditLimit.getBreachesCreditLimit(entitiesList);

		}

	}

}
