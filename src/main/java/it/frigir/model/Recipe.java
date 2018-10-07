package it.frigir.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recipe")
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "description")
	private String description;

	@Column(name = "prep_time")
	private Integer prepTime;

	@Column(name = "cook_time")
	private Integer cookTime;

	@Column(name = "servings")
	private Integer servings;

	@Column(name = "source")
	private String source;

	@Column(name = "url")
	private String url;

	@Column(name = "directions")
	@Lob
	private String directions;

	@Column(name = "difficulty")
	@Enumerated(value = EnumType.STRING)
	private Difficulty difficulty;

	@Column(name = "image")
	@Lob
	private Byte[] image;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
	private Set<Ingredient> ingredients = new HashSet<>();

	@OneToOne(cascade = CascadeType.ALL)
	private Notes note;

	@ManyToMany
	@JoinTable(name = "recipe_category", joinColumns = @JoinColumn(name = "recipe_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Category> categories = new HashSet<>();

	public void addIngredient(Ingredient ingredient) {
		ingredient.setRecipe(this);
		this.ingredients.add(ingredient);
	}

	public void setNotes(Notes note) {
		note.setRecipe(this);
		this.note = note;
	}

	public void addCategory(Category category) {
		this.categories.add(category);
	}


}
