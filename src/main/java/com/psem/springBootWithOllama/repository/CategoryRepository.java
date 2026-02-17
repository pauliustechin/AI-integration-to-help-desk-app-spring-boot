package com.psem.springBootWithOllama.repository;

import com.psem.springBootWithOllama.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // jpa naming convention, no need to write sql query
    Optional<Category> findByCategoryName(String categoryName);

}
