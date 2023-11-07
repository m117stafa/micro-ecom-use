package org.learning.inventoryservice.services;

import lombok.extern.slf4j.Slf4j;
import org.learning.inventoryservice.dto.InventoryResponse;
import org.learning.inventoryservice.repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;
    public List<InventoryResponse> isInStock(List<String> productCode){

        return inventoryRepository.findByProductCodeIn(productCode).stream()
                .map(inventory ->
                    InventoryResponse.builder()
                            .productCode(inventory.getProductCode())
                            .isInStock(inventory.getQuantity()>0).build()
                ).toList();

    }

}
