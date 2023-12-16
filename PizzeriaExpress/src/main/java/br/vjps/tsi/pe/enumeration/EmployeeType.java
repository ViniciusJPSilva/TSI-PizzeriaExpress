package br.vjps.tsi.pe.enumeration;

/**
 * Enumeração que representa os tipos de funcionário.
 * 
 * @author Vinicius J P Silva
 */
public enum EmployeeType {
	ADMIN("Administrador"),
	CHEF("Cozinheiro");
	
	private String description;
	
	private EmployeeType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Obtém um objeto do tipo EmployeeType com base na descrição especificada.
	 *
	 * @param description A descrição do tipo a ser buscado.
	 * @return O EmployeeType correspondente à descrição fornecida ou null se não encontrado.
	 */
	public static EmployeeType getByDescription(String description) {
		for(EmployeeType employeeType : EmployeeType.values())
			if(employeeType.description.equalsIgnoreCase(description))
				return employeeType;
		return null;
	}
}
