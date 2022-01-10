package com.developer.mealmonkey.model

data class CategoriesModel(val imgUrl: String, val name: String)

class Data {

    companion object {
        fun categoriesData(): List<CategoriesModel> {
            val list=ArrayList<CategoriesModel>()
            list.add(
                CategoriesModel(
                    "https://images.unsplash.com/photo-1601348637967-140ce3f53fa2?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80",
                    "Burgers"
                )
            )

            list.add(
                CategoriesModel(
                    "https://images.unsplash.com/photo-1590083745251-4fdb0b285c6a?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1978&q=80",
                    "Pizza"
                )
            )
            list.add(
                CategoriesModel(
                    "https://cdn.pixabay.com/photo/2020/09/06/14/07/biryani-5549075_1280.jpg",
                    "Biryani"
                )
            )
            list.add(
                CategoriesModel(
                    "https://b.zmtcdn.com/data/pictures/6/94286/bd5be9d764c10efeeab5603c0d8a2907.jpg?fit=around|771.75:416.25&crop=771.75:416.25;*,*",
                    "Barbeque"
                )
            )
            list.add(
                CategoriesModel(
                    "https://www.thespruceeats.com/thmb/UQQvtbSIgwGCqZaSYGJm7FbE3xE=/3081x3081/smart/filters:no_upscale()/rasgulla-indian-dessert-1957839-hero-01-7c3528a2d34a4f1b9248c7483a73d0a6.jpg",
                    "Deserts"
                )
            )
            return list
        }
    }
}