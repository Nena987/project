package com.iktpreobuka.project.entities;

public class CategoryEntity {
	
	protected Integer Id;
	protected String categoryName;
	protected String categoryDescription;
	public CategoryEntity() {
		super();
	}
	public CategoryEntity(Integer id, String categoryName, String categoryDescription) {
		super();
		Id = id;
		this.categoryName = categoryName;
		this.categoryDescription = categoryDescription;
	}
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryDescription() {
		return categoryDescription;
	}
	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}
	
	

}
