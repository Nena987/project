package com.iktpreobuka.project.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.project.entities.CategoryEntity;

@RestController
@RequestMapping(path = "/project/categories")
public class CategoryController {

	List<CategoryEntity> categories = new ArrayList<CategoryEntity>();

	private List<CategoryEntity> getDB() {
		if (categories.size() == 0) {
			CategoryEntity c1 = new CategoryEntity(1, " music", "description 1");
			CategoryEntity c2 = new CategoryEntity(2, " food", "description 2");
			CategoryEntity c3 = new CategoryEntity(3, " entertainment", "description3");

			categories.add(c1);
			categories.add(c2);
			categories.add(c3);
		}
		return categories;
	}

	/*
	 * 2.3 kreirati REST endpoint koji vraća listu svih kategorija • putanja
	 * /project/categories
	 */

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public List<CategoryEntity> getAllCategories() {
		return getDB();
	}

	/*
	 * 2.4 kreirati REST endpoint koji omogućava dodavanje nove kategorije • putanja
	 * /project/categories • metoda treba da vrati dodatu kategoriju
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/")
	public CategoryEntity newCategory(@RequestBody CategoryEntity newCat) {
		newCat.setId((new Random().nextInt()));
		categories.add(newCat);
		return newCat;
	}

	/*
	 * 2.5 kreirati REST endpoint koji omogućava izmenu postojeće kategorije •
	 * putanja /project/categories/{ • ukoliko je prosleđen ID koji ne pripada
	 * nijednoj kategoriji metoda treba da vrati null , a u suprotnom vraća podatke
	 * kategorije sa izmenjenim vrednostima
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public CategoryEntity changeCategory(@RequestBody CategoryEntity changeCat, @PathVariable Integer id) {
		for (CategoryEntity category : getDB()) {
			if (category.getId().equals(id)) {
				if (changeCat.getCategoryName() != null)
					category.setCategoryName(changeCat.getCategoryName());
				if (changeCat.getCategoryDescription() != null)
					category.setCategoryDescription(changeCat.getCategoryDescription());
				return category;
			}
		}
		return null;
	}

	/*
	 * 2.6 kreirati REST endpoint koji omogućava brisanje postojeće kategorije •
	 * putanja /project/categories/{ • ukoliko je prosleđen ID koji ne pripada
	 * nijednoj kategoriji metoda treba da vrati null , a u suprotnom vraća podatke
	 * o kategoriji koja je obrisana
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public CategoryEntity deleteCategory(@PathVariable Integer id) {
		Iterator<CategoryEntity> it = getDB().iterator();
		while (it.hasNext()) {
			CategoryEntity category = it.next();
			if (category.getId().equals(id)) {
				it.remove();
				return category;
			}
		}
		return null;
	}

	/*
	 * 2.7 kreirati REST endpoint koji vraća kategoriju po vrednosti prosleđenog ID
	 * a • putanja /project/categories/{ • u slučaju da ne postoji kategorija sa
	 * traženom vrednošću ID a vratiti null
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public CategoryEntity oneCategory(@PathVariable Integer id) {
		for (CategoryEntity category : getDB()) {
			if (category.getId().equals(id))
				return category;
		}
		return null;
	}
}
