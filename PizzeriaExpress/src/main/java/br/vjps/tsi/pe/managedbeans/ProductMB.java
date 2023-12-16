package br.vjps.tsi.pe.managedbeans;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.vjps.tsi.pe.dao.DAO;
import br.vjps.tsi.pe.enumeration.Category;
import br.vjps.tsi.pe.enumeration.ItemSize;
import br.vjps.tsi.pe.model.Product;

@ViewScoped
@ManagedBean
public class ProductMB {

	private Product product = new Product();
	private List<Product> productList;

	private Category selectedCategory = Category.SAVORY_PIZZA;
	
	/**
	 * Adiciona um novo produto.
	 * 
	 * Se o ID do produto for nulo, adiciona o produto ao banco de dados. Caso contrário, atualiza o produto existente.
	 * Reinicializa o produto e a lista de produtos após a operação.
	 */
	public void newProduct() {
		DAO<Product> dao = new DAO<>(Product.class);
		if (product.getId() == null) {
			dao.add(product);
		}else {
			dao.update(product);
		}
		
		this.product = new Product();
		this.productList = dao.list();
	}
	
	/**
	 * Cancela a operação, redefinindo o produto para um novo.
	 */
	public void cancel() {
		this.product = new Product();
	}
	
	/**
	 * Remove um produto.
	 * 
	 * Remove o produto do banco de dados e atualiza a lista de produtos.
	 */
	public void remove(Product product) {
		DAO<Product> dao = new DAO<>(Product.class);
		dao.remove(product);
		this.productList = dao.list();
	}
	
	/**
	 * Obtém a lista de categorias disponíveis.
	 * 
	 * @return Lista de categorias disponíveis.
	 */
	public List<Category> getCategoryList() {
		return Arrays.asList(Category.values());
	}
	
	/**
	 * Obtém a lista de tamanhos de item disponíveis.
	 * 
	 * @return Lista de tamanhos de item disponíveis.
	 */
	public List<ItemSize> getItemSizeList() {
		return Arrays.asList(ItemSize.values());
	}
	
	/**
	 * Obtém a lista de produtos por categoria.
	 * 
	 * @param category Categoria dos produtos a serem filtrados.
	 * @return Lista de produtos filtrada pela categoria.
	 */
	public List<Product> getProductListByCategory(Category category) {
		return getAvailableProducts().stream()
                .filter(product -> product.getCategory() == category)
                .collect(Collectors.toList());
	}
	
	/**
	 * Obtém a lista de produtos disponíveis.
	 * 
	 * @return Lista de produtos disponíveis.
	 */
	public List<Product> getAvailableProducts() {
		return getProductList().stream()
                .filter(product -> product.isAvailable())
                .collect(Collectors.toList());
	}
	
	public Product getProduct() {
		return product;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public List<Product> getProductList() {
		this.productList = new DAO<Product>(Product.class).list();
		return productList;
	}
	
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	
	public Category getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(Category selectedCategory) {
		this.selectedCategory = selectedCategory;
	}
	
}
