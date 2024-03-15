package com.masinite.masinite.repository;

import com.masinite.masinite.model.Chef;
import com.masinite.masinite.model.Pizza;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChefRepository extends CrudRepository<Chef, Long> {
    Chef findFirstById(Long id);
    Chef findFirstByName(String name);
}
