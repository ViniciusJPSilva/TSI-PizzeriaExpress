package br.vjps.tsi.pe.enumeration;

/**
 * Enumeração que representa os tamanhos dos itens.
 * 
 * @author Vinicius J P Silva
 */
public enum ItemSize {
	I_SMALL("P"),
	I_MEDIUM("M"),
	I_LARGE("G"),
	I_300ML("300ml"),
	I_350ML("350ml");
	
	private String description;
	
	private ItemSize(String description) {
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
	public static ItemSize getByDescription(String description) {
		for(ItemSize itemSize : ItemSize.values())
			if(itemSize.description.equalsIgnoreCase(description))
				return itemSize;
		return null;
	}
}
