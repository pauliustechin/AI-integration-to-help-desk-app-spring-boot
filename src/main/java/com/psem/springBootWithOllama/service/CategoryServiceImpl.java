package com.psem.springBootWithOllama.service;

import com.psem.springBootWithOllama.model.Category;
import com.psem.springBootWithOllama.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category checkIfCategoryExists(String categoryAnswer) {

        AtomicReference<String> categoryName = new AtomicReference<>("InitialValue");

        if(categoryAnswer.contains("bug")){
            categoryName.set("Bug");
        } else if (categoryAnswer.contains("feature")){
            categoryName.set("Feature");
        } else if (categoryAnswer.contains("billing")){
            categoryName.set("Billing");
        } else if (categoryAnswer.contains("account")){
            categoryName.set("Account");
        } else {
            categoryName.set("Other");
        }

        // if category doesn't exist, create new category, set category name, save in database and return saved category.
        // else return category from database.

        Category category = categoryRepository.findByCategoryName(categoryName.toString())
                .orElseGet(() -> new Category());

        if(category.getCategoryName() == null){

            category.setCategoryName(categoryName.toString());
            Category savedCategory = categoryRepository.save(category);
            return savedCategory;
        }

        return category;


    }
}
