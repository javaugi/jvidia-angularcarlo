/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jvidia.reactcloan.repo;

import com.jvidia.reactcloan.entity.RWFProduct;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Repository
@Transactional
public interface ProductWebFluxRepository extends ReactiveCrudRepository<RWFProduct, Long> {
    Flux<RWFProduct> findByCategory(String category);
    
}
