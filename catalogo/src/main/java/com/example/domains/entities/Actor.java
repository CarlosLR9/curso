package com.example.domains.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.domains.core.entities.EntityBase;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * The persistent class for the actor database table.
 * 
 */
@Entity
@Table(name = "actor")
@NamedQuery(name = "Actor.findAll", query = "SELECT a FROM Actor a")
public class Actor extends EntityBase<Actor> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "actor_id", unique = true, nullable = false)
	private int actorId;

	@Column(name = "first_name", nullable = false, length = 45)
	@NotBlank
	@Size(max = 45, min = 2)
	private String firstName;

	@Column(name = "last_name", nullable = false, length = 45)
	@Size(max = 45, min = 2)
	@Pattern(regexp = "[A-Z]+", message = "Tiene que estar en mayusculas")
	private String lastName;

	@Column(name = "last_update", insertable = false, updatable = false, nullable = false)
	@PastOrPresent
	private Timestamp lastUpdate;

	// bi-directional many-to-one association to FilmActor
	@OneToMany(mappedBy = "actor", fetch = FetchType.EAGER)
	@JsonIgnore
	private List<FilmActor> filmActors = new ArrayList<FilmActor>();

	public Actor() {
	}

	public Actor(int actorId) {
		super();
		this.actorId = actorId;
	}

	public Actor(int actorId, @NotBlank @Size(max = 45, min = 2) 
			@Pattern(regexp = "[A-Z]+", message = "Tiene que estar en mayusculas") String firstName,
			@Size(max = 45, min = 2) @Pattern(regexp = "[A-Z]+", message = "Tiene que estar en mayusculas") String lastName) {
		super();
		this.actorId = actorId;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public int getActorId() {
		return this.actorId;
	}

	public void setActorId(int actorId) {
		this.actorId = actorId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	// Gestion de films

	public List<Film> getFilms() {
		return this.filmActors.stream().map(item -> item.getFilm()).toList();
	}
	
	public void setFilms(List<Film> source) {
		if (filmActors == null || !filmActors.isEmpty())
			clearFilms();
		source.forEach(item -> addFilm(item));
	}
	
	public void clearFilms() {
		filmActors = new ArrayList<FilmActor>();
	}

	public void addFilm(Film film) {
		FilmActor filmActor = new FilmActor(film, this);
		filmActors.add(filmActor);
	}

	public void addFilm(int filmId) {
		addFilm(new Film(filmId));
	}
	
	public void removeFilm(Film film) {
		var filmActor = filmActors.stream().filter(item -> item.getFilm().equals(film)).findFirst();
		if (filmActor.isEmpty())
			return;
		filmActors.remove(filmActor.get());
	}

	@Override
	public int hashCode() {
		return Objects.hash(actorId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Actor other = (Actor) obj;
		return actorId == other.actorId;
	}

	@Override
	public String toString() {
		return "Actor [actorId=" + actorId + ", firstName=" + firstName + ", lastName=" + lastName + ", lastUpdate="
				+ lastUpdate + "]";
	}
	
	public Actor merge(Actor target) {
		target.firstName = firstName;
		target.lastName = lastName;
		// Borra las peliculas que sobran
		target.getFilms().stream().filter(item -> !getFilms().contains(item))
				.forEach(item -> target.removeFilm(item));
		// Añade las peliculas que faltan
		getFilms().stream().filter(item -> !target.getFilms().contains(item)).forEach(item -> target.addFilm(item));
		return target;
	}

}