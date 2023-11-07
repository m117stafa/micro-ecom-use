package org.learning.orderservice.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponse {
    private String productCode;
    private boolean isInStock;
}
