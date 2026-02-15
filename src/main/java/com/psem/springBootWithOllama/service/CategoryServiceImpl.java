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
        System.out.println("tsetsdtes" + categoryAnswer);

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

        Category category = categoryRepository.findByCategoryName(categoryName.toString())
                .orElseGet(() -> new Category());

        System.out.println(category.getCategoryName());

        if(category.getCategoryName() == null){
            category.setCategoryName(categoryName.toString());
            categoryRepository.save(category);
        }

        return category;


    }
}
