package com.evolution.Inventoryservice.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author laith ghnemat
 * @version 1.0
 * @since 1/9/2024
 */
@Entity
@Table(name = "inventory")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "sku_code")
    private String skuCode;
    private Integer quantity;
}