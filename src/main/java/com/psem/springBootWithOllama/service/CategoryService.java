package com.psem.springBootWithOllama.service;

import com.psem.springBootWithOllama.model.Category;

public interface CategoryService {
    Category checkIfCategoryExists(String categoryName);
}
