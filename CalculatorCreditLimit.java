package com.anz.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.anz.pojo.CreditEntities;

public class CalculatorCreditLimit {
	private static final Logger LOGGER = Logger.getLogger(CalculatorCreditLimit.class.getName());
	static List<CreditEntities> entitieReturnList;
	private volatile static Optional<String> parentEntity;
	static List<CreditEntities> overAllObject;

	public static void getBreachesCreditLimit(List<CreditEntities> listObj) {
		LOGGER.info("CalculatorCreditLimit.getValidateCreditLimit() started");
		overAllObject = listObj;
		// read the all individual starting entities with No parent
		if (listObj != null) {
			List<CreditEntities> noParentObject = listObj.stream().filter(obj -> obj.getParent().get() == "")
					.filter(obj -> obj.getEntity().get() != null).collect(Collectors.toList());
			findEntityofBreaches(noParentObject);
		}
		LOGGER.info("CalculatorCreditLimit.getValidateCreditLimit() Completed ");
	}

	public static void findEntityofBreaches(List<CreditEntities> noParentEntityList) {
		LOGGER.info("CalculatorCreditLimit.otherLogic() started  ");
		boolean itsBreachesFlag = true;
		// Iterate each individual starting entities with No parent
		for (CreditEntities entityObj : noParentEntityList) {
			entitieReturnList = new ArrayList<>();
			parentEntity = entityObj.getEntity();
			entitieReturnList.add(entityObj);
         // find each individual  related entities based on parent 
			CreditEntities response = recursionObjectCheck(parentEntity);
			if (response.getEntity().isPresent() && response.getParent().isPresent()) {
				parentEntity = Optional.ofNullable(response.getEntity().get());
				// find each individual  related entities based on parent 
				recursionObjectCheck(parentEntity);

			}
            // find current entities contains combined Entities
			List<String> combinedEntitiesList = combinedEntities(parentEntity);
			if (combinedEntitiesList.size() > 0) {
				// find All  combined parent Entities list
				for (String inputValu : combinedEntitiesList) {
					// find All parent entities   combined list
					List<CreditEntities> findCombinedEntitiesList = findCombinedEntities(inputValu);
					// Calculate combined  Entities limit 
					caluculateCombinedEntitiesCredit(findCombinedEntitiesList);
				}
			}
            // Calculate Credit of First  Entities
			caluculateCredit(entitieReturnList, itsBreachesFlag);
			// update next Entities onwards having breaches
			itsBreachesFlag = false;
		}
		LOGGER.info("CalculatorCreditLimit.otherLogic() Completed ");
	}

	public static CreditEntities recursionObjectCheck(Optional<String> optional) {
		LOGGER.info("CalculatorCreditLimit.recursionObjectCheck() started");
		// find each entities object based on input value 
		CreditEntities searchObject = overAllObject.stream()
				.filter(obj -> obj.getParent() != null && !obj.getParent().isEmpty())
				.filter(obj -> obj.getParent().equals(optional)).findAny().orElse(null);
		entitieReturnList.add(searchObject);
		LOGGER.info("CalculatorCreditLimit.recursionObjectCheck() Completed");
		return searchObject;

	}

	public static List<CreditEntities> findCombinedEntities(String optional) {
		LOGGER.info("CalculatorCreditLimit.findCombinedEntities() started" + optional);
		// find all Combined Entities of parent object  
		List<CreditEntities> searchObject = null;
		if (optional != null) {
			searchObject = overAllObject.stream().filter(obj -> obj.getParent() != null && !obj.getParent().isEmpty())
					.filter(obj -> obj.getParent().get().equals(optional)).collect(Collectors.toList());
			entitieReturnList.addAll(searchObject);
		}
		LOGGER.info("CalculatorCreditLimit.findCombinedEntities() Completed");
		return searchObject;

	}

	public static List<String> combinedEntities(Optional<String> optional) {
		// find all Combined Entities and there parent object 
		List<String> combinedEntitiesList = new ArrayList<>();
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (CreditEntities ch : overAllObject) {
			if (ch.getParent().get().equals(optional.get()))
				if (map.containsKey(ch.getParent().get())) {
					map.put(ch.getParent().get(), map.get(ch.getParent().get()) + 1);

				} else {
					map.put(ch.getParent().get(), 1);
				}
		}
		for (Map.Entry<String, Integer> entry : map.entrySet()) { 
			combinedEntitiesList.add(entry.getKey());
		}
		return combinedEntitiesList;
	}

	public static void caluculateCombinedEntitiesCredit(List<CreditEntities> combinedEntitiList) {
		LOGGER.info("CalculatorCreditLimit.caluculateCombinedEntitiesCredit() started --------------");
		// Calculate Combined Entities Credit limit
		Optional<Integer> utilisationValue = combinedEntitiList.stream().filter(e -> e != null)
				.map(e -> e.getUtilisation().get()).reduce(Integer::sum);
		int utilValue = utilisationValue.get();

		int rootUtlisation = combinedEntitiList.get(0).getUtilisation().get();

		String rootEntiti = combinedEntitiList.get(0).getParent().get();
		int rootEntityLimit = combinedEntitiList.get(0).getLimit().get();
		System.out.println("rootEntiti" + rootEntiti + "rootEntityLimit" + rootEntityLimit);
		LOGGER.info("***************************************************************************************");
		LOGGER.info("Combined Entities: " + rootEntiti + ":-      Limit breach at " + rootEntiti + " "
				+ "(combined limit = " + rootEntityLimit + "" + ", direct utilisation =" + rootUtlisation + "" + ""
				+ ", combined utilisation =" + utilValue + ").");
		LOGGER.info("***************************************************************************************");
		LOGGER.info("CalculatorCreditLimit.caluculateCombinedEntitiesCredit() Completed ------------");

	}

	public static void caluculateCredit(List<CreditEntities> eachSetofData, boolean parentCheck) {
		if (parentCheck == true) {
			// Calculate CreditValue  No limit breaches First or root entities 

			String entityBreach = headingBreaches(eachSetofData);

			LOGGER.info("***************************************************************************************");
			LOGGER.info("Entities: " + entityBreach + ":-   No limit breaches");
			LOGGER.info("***************************************************************************************");

		} else if (parentCheck == false) {
			caluculateCreditValue(eachSetofData);
		}
	}

	public static String headingBreaches(List<CreditEntities> eachSetofData) {
		// framing the root entities valid pattern 
		String entityBreach = "/";		
		for (CreditEntities eachObj : eachSetofData) {
			if (eachObj != null)
				entityBreach = entityBreach + eachObj.getEntity().get().concat("/");
		}
		return entityBreach;
	}

	public static void caluculateCreditValue(List<CreditEntities> eachSetofData) {
       //Calculate Limit breach of each entities breach 
		LOGGER.info("CalculatorCreditLimit.caluculateCredit() started --------------");
		Optional<Integer> utilisationValue = eachSetofData.stream().filter(e -> e != null)
				.map(e -> e.getUtilisation().get()).reduce(Integer::sum);
		int utilValue = utilisationValue.get();
		int rootLimit = eachSetofData.get(0).getLimit().get();
		int rootUtlisation = eachSetofData.get(0).getUtilisation().get();
		String entityValue = eachSetofData.get(0).getEntity().get();
		String entityBreach = headingBreaches(eachSetofData);
		LOGGER.info("***************************************************************************************");
		LOGGER.info("Entities: " + entityBreach + ":-      Limit breach at " + entityValue + " " + "(limit = "
				+ rootLimit + "" + ", direct utilisation =" + rootUtlisation + "" + "" + ", combined utilisation ="
				+ utilValue + ").");
		LOGGER.info("***************************************************************************************");
		LOGGER.info("CalculatorCreditLimit.caluculateCredit() Completed ------------");
	}

}
