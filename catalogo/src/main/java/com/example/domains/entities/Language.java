package com.example.domains.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.example.domains.core.entities.EntityBase;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The persistent class for the language database table.
 * 
 */
@Entity
@Table(name = "language")
@NamedQuery(name = "Language.findAll", query = "SELECT l FROM Language l")
public class Language extends EntityBase<Language> implements Serializable {
	private static final long serialVersionUID = 1L;

	public static class Partial {
	}

	public static class Complete extends Partial {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "language_id")
	@JsonProperty("id")
	@JsonView(Language.Partial.class)
	private int languageId;

	@NotBlank
	@Size(max = 20)
	@JsonProperty("idioma")
	@JsonView(Language.Partial.class)
	private String name;

	@Column(name = "last_update", insertable = false, updatable = false)
	@JsonView(Language.Complete.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	@JsonProperty("ultimaModificacion")
	private Timestamp lastUpdate;

	// bi-directional many-to-one association to Film
	@OneToMany(mappedBy = "language")
	@JsonIgnore
	private List<Film> films = new ArrayList<Film>();

	// bi-directional many-to-one association to Film
	@OneToMany(mappedBy = "languageVO")
	@JsonIgnore
	private List<Film> filmsVO = new ArrayList<Film>();

	public Language() {
	}

	public Language(int languageId) {
		this.languageId = languageId;
	}

	public Language(int languageId, @NotBlank @Size(max = 20) String name) {
		super();
		this.languageId = languageId;
		this.name = name;
	}

	public int getLanguageId() {
		return this.languageId;
	}

	public void setLanguageId(int languageId) {
		this.languageId = languageId;
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

	public List<Film> getFilms() {
		return this.films;
	}

	public void setFilms(List<Film> films) {
		this.films = films;
	}

	public Film addFilm(Film film) {
		getFilms().add(film);
		film.setLanguage(this);

		return film;
	}

	public void addFilm(int filmId) {
		addFilm(new Film(filmId));
	}

	public void clearFilms() {
		films = new ArrayList<Film>();
	}

	public Film removeFilm(Film film) {
		getFilms().remove(film);
		film.setLanguage(null);

		return film;
	}

	public List<Film> getFilmsVO() {
		return this.filmsVO;
	}

	public void setFilmsVO(List<Film> filmsVO) {
		this.filmsVO = filmsVO;
	}

	public Film addFilmVO(Film filmsVO) {
		getFilmsVO().add(filmsVO);
		filmsVO.setLanguageVO(this);

		return filmsVO;
	}

	public void addFilmVO(int filmId) {
		addFilmVO(new Film(filmId));
	}

	public void clearFilmsVO() {
		filmsVO = new ArrayList<Film>();
	}

	public Film removeFilmVO(Film filmsVO) {
		getFilmsVO().remove(filmsVO);
		filmsVO.setLanguageVO(null);

		return filmsVO;
	}

	@Override
	public int hashCode() {
		return Objects.hash(languageId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Language other = (Language) obj;
		return languageId == other.languageId;
	}

	@Override
	public String toString() {
		return "Language [languageId=" + languageId + ", name=" + name + "]";
	}

	public Language merge(Language target) {
		target.name = name;
		// Borra las peliculas que sobran
		target.getFilms().stream().filter(item -> !getFilms().contains(item))
				.forEach(item -> target.removeFilm(item));
		// Añade las peliculas que faltan
		getFilms().stream().filter(item -> !target.getFilms().contains(item))
				.forEach(item -> target.addFilm(item));
		System.out.println(target.getFilmsVO());
		if (target.getFilmsVO() != null) {
			// Borra las peliculas en vo que sobran
			target.getFilmsVO().stream().filter(item -> !getFilmsVO().contains(item))
					.forEach(item -> target.removeFilmVO(item));
			// Añade las peliculas en vo que faltan
			getFilmsVO().stream().filter(item -> !target.getFilmsVO().contains(item))
					.forEach(item -> target.addFilmVO(item));
		}
		
		return target;
	}

}