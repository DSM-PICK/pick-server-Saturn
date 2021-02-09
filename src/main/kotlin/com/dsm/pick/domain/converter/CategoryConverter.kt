package com.dsm.pick.domain.converter

import com.dsm.pick.domain.attribute.Category
import com.dsm.pick.exception.NonExistCategoryException
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class CategoryConverter : AttributeConverter<Category, String> {

    override fun convertToDatabaseColumn(category: Category) = category.value

    override fun convertToEntityAttribute(category: String) =
        Category.values().singleOrNull { it.value == category }?: throw NonExistCategoryException(category)
}