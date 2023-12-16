package br.vjps.tsi.pe.enumeration;

/**
 * Enumeração que representa as categorias dos itens.
 * 
 * @author Vinicius J P Silva
 */
public enum Category {
	SAVORY_PIZZA("Pizza Salgada"),
	SWEET_PIZZA("Pizza Doce"),
	DRINK("Bebida");
	
	private String description;
	
	private Category(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Obtém um objeto do tipo UserType com base na descrição especificada.
	 *
	 * @param description A descrição do tipo a ser buscado.
	 * @return O UserType correspondente à descrição fornecida ou null se não encontrado.
	 */
	public static Category getByDescription(String description) {
		for(Category category : Category.values())
			if(category.description.equalsIgnoreCase(description))
				return category;
		return null;
	}
}
