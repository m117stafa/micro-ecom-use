package org.learning.inventoryservice.controllers;

import lombok.extern.slf4j.Slf4j;
import org.learning.inventoryservice.dto.InventoryResponse;
import org.learning.inventoryservice.model.Inventory;
import org.learning.inventoryservice.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@Slf4j
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> productCode){
        log.info(productCode.toString());
        log.info("----------------***************");
        return inventoryService.isInStock(productCode);
    }
}
