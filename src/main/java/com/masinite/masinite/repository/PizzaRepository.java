package com.masinite.masinite.repository;

import com.masinite.masinite.model.Pizza;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PizzaRepository extends CrudRepository<Pizza, Long> {
    List<Pizza> findByChefId(Long chefId);
    Pizza findFirstByName(String name);
}
