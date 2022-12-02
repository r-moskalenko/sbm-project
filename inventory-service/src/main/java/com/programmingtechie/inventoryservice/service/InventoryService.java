package com.programmingtechie.inventoryservice.service;

import com.programmingtechie.inventoryservice.dto.InventoryDto;
import com.programmingtechie.inventoryservice.dto.InventoryResponse;
import com.programmingtechie.inventoryservice.repository.InventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class InventoryService {
    Logger logger = LoggerFactory.getLogger(InventoryService.class);
    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCodes) throws InterruptedException {
        logger.info("Wait started");
        Random random = new Random();
        int sleepSeconds = random.nextInt(2,6);
        logger.info("I will wait {} seconds", sleepSeconds);
        Thread.sleep(sleepSeconds * 1000L);
        logger.info("Wait ended");
        return inventoryRepository.findBySkuCodeIn(skuCodes)
                .stream().map(el ->
                    InventoryResponse.builder()
                            .skuCode(el.getSkuCode())
                            .isInStock(el.getQuantity() > 0)
                            .build()
                ).toList();
    }

    @Transactional(readOnly = true)
    public List<InventoryDto> getAllInventories() {
        var inventories = inventoryRepository.findAll();
        logger.debug("All fetched inventories {}", Arrays.toString(inventories.toArray()));
        return inventories.stream().map(inventory ->
                new InventoryDto(inventory.getId(), inventory.getSkuCode(), inventory.getQuantity())).toList();
    }
}
