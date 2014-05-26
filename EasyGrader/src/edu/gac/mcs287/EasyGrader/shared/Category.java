	package edu.gac.mcs287.EasyGrader.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Category implements Serializable {
	private static final long serialVersionUID = 1l;
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	private String id;

	@Persistent
	private String title = "no title";

	/*
	 * private CategoryGradeType type;
	 * 
	 * @Persistent
	 * 
	 * @Unowned
	 */
	@NotPersistent
	private List<Category> subcategories = new ArrayList<Category>();
	@Persistent
	private int value;
	@Persistent
	private boolean isSubcategory = false;

	public Category() {
	}

	public Category(String title, int value) {
		setTitle(title);
		setValue(value);
		// setType(type);
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\t-" + title);
		for (Category s : subcategories) {
			buffer.append("\n\t\t-" + s.getTitle());
		}
		return buffer.toString();
	}

	// Getters/setters
	public List<Category> getSubcategories() {
		return subcategories;
	}

	public void setSubcategories(List<Category> subcategories) {
		for(Category c: subcategories){
			c.setSubcategory(true);
		}
		this.subcategories = subcategories;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public void setSubcategory(boolean isSubcategory) {
		this.isSubcategory = isSubcategory;
	}

	public boolean isSubcategory() {
		return isSubcategory;
	}

	/*
	 * public CategoryGradeType getType() { return type; }
	 * 
	 * public void setType(CategoryGradeType type) { this.type = type; }
	 */

	public void addCategory(Category c) {
		c.setSubcategory(true);
		this.subcategories.add(c);
	}




}
