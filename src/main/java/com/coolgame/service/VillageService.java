package com.coolgame.service;

import com.coolgame.model.Account;
import com.coolgame.model.Village;
import com.coolgame.repo.VillageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class VillageService {

    private final VillageRepository villageRepository;

    @Autowired
    public VillageService(VillageRepository villageRepository) {
        this.villageRepository = villageRepository;
    }

    public Village createVillage(Account account) {
        // Logic for creating a new village
        // ...

        Village village = new Village();
        village.setAccount(account);
        initializeDefaultResources(village);
        initializeDefaultBuildings(village);

        // Save the new village to the database using the VillageRepository
        // It's a good practice to return the saved instance for potential further use.
        return villageRepository.save(village);
    }

    private void initializeDefaultResources(Village village) {
        // Set default resource amounts
        Map<String, Long> resources = new HashMap<>();
        resources.put("wheat", 1000L);
        resources.put("wood", 1000L);
        resources.put("stone", 500L);
        resources.put("gold", 500L);
        village.setResources(resources);
    }

    private void initializeDefaultBuildings(Village village) {
        // Set default building levels
        Map<String, Integer> buildings = new HashMap<>();
        buildings.put("farms", 0);
        buildings.put("lumbers", 0);
        buildings.put("rockMines", 0);
        buildings.put("goldMines", 0);
        village.setBuildings(buildings);
    }



    // public for tests!
    public long calculateElapsedHours(Date lastUpdateTime, Date now) {
        long elapsedMilliseconds = now.getTime() - lastUpdateTime.getTime();
        return elapsedMilliseconds / (1000 * 3600); // Convert elapsedMilliseconds to elapsedHours
    }


    public Map<String, Long> calculateResourcesProduced(Village village, long elapsedHours) {
        Map<String, Long> resourcesProduced = new HashMap<>();
        Map<String, Integer> buildingLevels = village.getBuildings();

        // Production rates for each resource building
        Map<String, Long> productionRates = new HashMap<>();
        productionRates.put("farms", 1000L);
        productionRates.put("lumbers", 500L);
        productionRates.put("rockMines", 300L);
        productionRates.put("goldMines", 100L);

        // Calculate the resources produced based on building levels and elapsed hours
        for (String resourceBuilding : productionRates.keySet()) {
            int buildingLevel = buildingLevels.getOrDefault(resourceBuilding, 0);
            long productionRate = productionRates.get(resourceBuilding);
            long producedAmount = buildingLevel * productionRate * elapsedHours;
            resourcesProduced.put(resourceBuilding, producedAmount);
        }

        return resourcesProduced;
    }


    public void updateVillageResources(Village village, Map<String, Long> resourcesProduced, Date now) {
        Map<String, Long> currentResources = village.getResources();

        for (String resource : resourcesProduced.keySet()) {
            long producedAmount = resourcesProduced.get(resource);
            long currentAmount = currentResources.getOrDefault(resource, 0L);
            long updatedAmount = currentAmount + producedAmount;
            currentResources.put(resource, updatedAmount);
        }

        village.setResources(currentResources);
        village.setLastUpdated(now);
    }
    // Other methods related to village business logic
}