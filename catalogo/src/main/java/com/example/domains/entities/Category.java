package com.example.domains.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import com.example.domains.core.entities.EntityBase;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The persistent class for the category database table.
 * 
 */
@Entity
@Table(name = "category")
@NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c")
public class Category extends EntityBase<Category> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
	@JsonProperty("id")
	private int categoryId;

	@Column(name = "last_update", insertable = false, updatable = false)
	@PastOrPresent
	@JsonIgnore
	private Timestamp lastUpdate;

	@NotBlank
	@Size(max = 25)
	@JsonProperty("categoria")
	private String name;

	// bi-directional many-to-one association to FilmCategory
	@OneToMany(mappedBy = "category")
	@JsonIgnore
	private List<FilmCategory> filmCategories = new ArrayList<FilmCategory>();

	public Category() {
	}

	public Category(int categoryId) {
		super();
		this.categoryId = categoryId;
	}

	public Category(int categoryId, @NotBlank @Size(max = 25) String name) {
		super();
		this.categoryId = categoryId;
		this.name = name;
	}

	public int getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
		if (filmCategories != null && filmCategories.size() > 0)
			filmCategories.forEach(item -> {
				if (item.getId().getCategoryId() != categoryId)
					item.getId().setCategoryId(categoryId);
			});
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// Gestion de films

	public List<Film> getFilms() {
		return this.filmCategories.stream().map(item -> item.getFilm()).toList();
	}

	public void setFilms(List<Film> source) {
		if (filmCategories == null || !filmCategories.isEmpty())
			clearFilms();
		source.forEach(item -> addFilm(item));
	}

	public void clearFilms() {
		filmCategories = new ArrayList<FilmCategory>();
	}

	public void addFilm(Film item) {
		FilmCategory filmCategory = new FilmCategory(item, this);
		filmCategories.add(filmCategory);
	}

	public void addFilm(int id) {
		addFilm(new Film(id));
	}

	public void removeFilm(Film ele) {
		var filmCategory = filmCategories.stream().filter(item -> item.getFilm().equals(ele)).findFirst();
		if (filmCategory.isEmpty())
			return;
		filmCategories.remove(filmCategory.get());
	}

	@Override
	public int hashCode() {
		return Objects.hash(categoryId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		return categoryId == other.categoryId;
	}

	@Override
	public String toString() {
		return "Category [categoryId=" + categoryId + ", name=" + name + ", lastUpdate=" + lastUpdate + "]";
	}

	public Category merge(Category target) {
		target.name = name;
		// Borra las peliculas que sobran
		target.getFilms().stream().filter(item -> !getFilms().contains(item))
				.forEach(item -> target.removeFilm(item));
		// Añade las peliculas que faltan
		getFilms().stream().filter(item -> !target.getFilms().contains(item))
				.forEach(item -> target.addFilm(item));
		return target;
	}

}
