package br.vjps.tsi.pe.component;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import br.vjps.tsi.pe.dao.DAO;
import br.vjps.tsi.pe.dao.EmployeeDAO;
import br.vjps.tsi.pe.enumeration.Category;
import br.vjps.tsi.pe.enumeration.EmployeeType;
import br.vjps.tsi.pe.enumeration.ItemSize;
import br.vjps.tsi.pe.model.Employee;
import br.vjps.tsi.pe.model.Product;

/**
 * Carregador de Dados Iniciais para o sistema, executado durante o evento de inicialização do contexto da aplicação.
 *
 * @author Vinícius J P Silva
 */
@WebListener
public class InitialDataLoader implements ServletContextListener {

	 /**
     * Executa a lógica de carga de dados iniciais quando o contexto da aplicação é atualizado.
     *
     * @param event O evento de atualização do contexto da aplicação.
     */
	@Override
    public void contextInitialized(ServletContextEvent sce) {
        // Verifica se os dados já foram carregados para evitar duplicatas
        if (alreadyLoaded()) return;

        // Adiciona os funcionários
        Employee admin = new Employee();
        admin.setName("Administrador");
        admin.setLogin("admin");
        admin.setPassword("admin");
        admin.setType(EmployeeType.ADMIN);
        
        Employee chef = new Employee();
        chef.setName("Cozinheiro");
        chef.setLogin("chef");
        chef.setPassword("chef");
        chef.setType(EmployeeType.CHEF);

        DAO<Employee> dao = new DAO<Employee>(Employee.class);
        DAO<Product> productDao = new DAO<Product>(Product.class);
        
        addProduct(productDao, "Calabresa", "Linguiça calabresa, molho de tomate fresco, cebola, azeitona, queijo muçarela e orégano salpicado", "P", "Pizza Salgada", 45.00);
        addProduct(productDao, "Portuguesa", "Cebola, azeitona, ervilha, queijo e presunto", "P", "Pizza Salgada", 45.00);
        addProduct(productDao, "Marguerita", "Muçarela, tomate, manjericão, orégano e base de molho de tomate. ", "P", "Pizza Salgada", 45.00);
        addProduct(productDao, "Frango com catupiry", "Catupiry, frango desfiado e Muçarela", "P", "Pizza Salgada", 45.00);
        addProduct(productDao, "Napolitana", "Orégano salpicado, tomate fresco, azeitona sem caroço, queijo muçarela e parmesão", "P", "Pizza Salgada", 45.00);
        addProduct(productDao, "Quatro queijos", "Muçarela, catupiry, provolone e parmesão. ", "P", "Pizza Salgada", 45.00);
        addProduct(productDao, "Baiana", "Calabresa moída, ovos, pimenta, cebola e um toque de parmesão", "P", "Pizza Salgada", 45.00);
        addProduct(productDao, "Brigadeiro", "Brigadeiro e Muçarela", "P", "Pizza Doce", 45.00);
        addProduct(productDao, "Romeu e Julieta", "Goiabada e Muçarela", "P", "Pizza Doce", 45.00);
        addProduct(productDao, "Creme de avelã com frutas", "Morangos, uvas, abacaxi, banana e creme de avelã", "P", "Pizza Doce", 45.00);
        addProduct(productDao, "Califórnia", "Queijo muçarela e frutas, como pêssego, figo, abacaxi ou uva passa. ", "P", "Pizza Doce", 45.00);
        addProduct(productDao, "Calabresa", "Linguiça calabresa, molho de tomate fresco, cebola, azeitona, queijo muçarela e orégano salpicado", "M", "Pizza Salgada", 55.00);
        addProduct(productDao, "Portuguesa", "Cebola, azeitona, ervilha, queijo e presunto", "M", "Pizza Salgada", 55.00);
        addProduct(productDao, "Marguerita", "Muçarela, tomate, manjericão, orégano e base de molho de tomate. ", "M", "Pizza Salgada", 55.00);
        addProduct(productDao, "Frango com catupiry", "Catupiry, frango desfiado e Muçarela", "M", "Pizza Salgada", 55.00);
        addProduct(productDao, "Napolitana", "Orégano salpicado, tomate fresco, azeitona sem caroço, queijo muçarela e parmesão", "M", "Pizza Salgada", 55.00);
        addProduct(productDao, "Quatro queijos", "Muçarela, catupiry, provolone e parmesão. ", "M", "Pizza Salgada", 55.00);
        addProduct(productDao, "Baiana", "Calabresa moída, ovos, pimenta, cebola e um toque de parmesão", "M", "Pizza Salgada", 55.00);
        addProduct(productDao, "Brigadeiro", "Brigadeiro e Muçarela", "M", "Pizza Doce", 55.00);
        addProduct(productDao, "Romeu e Julieta", "Goiabada e Muçarela", "M", "Pizza Doce", 55.00);
        addProduct(productDao, "Creme de avelã com frutas", "Morangos, uvas, abacaxi, banana e creme de avelã", "M", "Pizza Doce", 55.00);
        addProduct(productDao, "Califórnia", "Queijo muçarela e frutas, como pêssego, figo, abacaxi ou uva passa. ", "M", "Pizza Doce", 55.00);
        addProduct(productDao, "Calabresa", "Linguiça calabresa, molho de tomate fresco, cebola, azeitona, queijo muçarela e orégano salpicado", "G", "Pizza Salgada", 65.00);
        addProduct(productDao, "Portuguesa", "Cebola, azeitona, ervilha, queijo e presunto", "G", "Pizza Salgada", 65.00);
        addProduct(productDao, "Marguerita", "Muçarela, tomate, manjericão, orégano e base de molho de tomate. ", "G", "Pizza Salgada", 65.00);
        addProduct(productDao, "Frango com catupiry", "Catupiry, frango desfiado e Muçarela", "G", "Pizza Salgada", 65.00);
        addProduct(productDao, "Napolitana", "Orégano salpicado, tomate fresco, azeitona sem caroço, queijo muçarela e parmesão", "G", "Pizza Salgada", 65.00);
        addProduct(productDao, "Quatro queijos", "Muçarela, catupiry, provolone e parmesão. ", "G", "Pizza Salgada", 65.00);
        addProduct(productDao, "Baiana", "Calabresa moída, ovos, pimenta, cebola e um toque de parmesão", "G", "Pizza Salgada", 65.00);
        addProduct(productDao, "Brigadeiro", "Brigadeiro e Muçarela", "G", "Pizza Doce", 65.00);
        addProduct(productDao, "Romeu e Julieta", "Goiabada e Muçarela", "G", "Pizza Doce", 65.00);
        addProduct(productDao, "Creme de avelã com frutas", "Morangos, uvas, abacaxi, banana e creme de avelã", "G", "Pizza Doce", 65.00);
        addProduct(productDao, "Califórnia", "Queijo muçarela e frutas, como pêssego, figo, abacaxi ou uva passa. ", "G", "Pizza Doce", 65.00);
        addProduct(productDao, "Refrigerante", "Fanta Laranja", "350ml", "Bebida", 5.00);
        addProduct(productDao, "Refrigerante", "Coca-cola", "350ml", "Bebida", 5.00);
        addProduct(productDao, "Refrigerante", "Guaraná Antártica", "350ml", "Bebida", 5.00);
        addProduct(productDao, "Refrigerante", "Coca-cola sem açúcar", "350ml", "Bebida", 5.00);
        addProduct(productDao, "Refrigerante", "Sprite", "350ml", "Bebida", 5.00);
        addProduct(productDao, "Suco", "Laranja", "300ml", "Bebida", 8.00);
        addProduct(productDao, "Suco", "Limonada", "300ml", "Bebida", 8.00);
        addProduct(productDao, "Suco", "Limonada Suíça", "300ml", "Bebida", 8.00);
        addProduct(productDao, "Suco", "Abacaxi", "300ml", "Bebida", 8.00);
        addProduct(productDao, "Suco", "Abacaxi com Hortelã", "300ml", "Bebida", 8.00);
        addProduct(productDao, "Suco", "Maracujá", "300ml", "Bebida", 8.00);
        addProduct(productDao, "Suco", "Morangos", "300ml", "Bebida", 8.00);
        
        dao.add(admin);
        dao.add(chef);
    }
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {	}

    /**
     * Verifica se os dados iniciais já foram carregados no banco de dados.
     *
     * @return true se os dados já foram carregados, false caso contrário.
     */
	private boolean alreadyLoaded() {
        // Consulta o banco de dados para verificar se já existe um funcionário com o login "admin"
        try (EmployeeDAO employeeDAO = new EmployeeDAO()) {
        	Employee admin = employeeDAO.getByLogin("admin");
        	Employee chef = employeeDAO.getByLogin("chef");
            return admin != null && chef != null;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
	
	/**
	 * Adiciona um produto ao banco de dados.
	 * 
	 * @param dao         O DAO utilizado para interagir com o banco de dados.
	 * @param name        O nome do produto.
	 * @param description A descrição do produto.
	 * @param size        O tamanho do produto.
	 * @param category    A categoria do produto.
	 * @param price       O preço do produto.
	 */
	private static void addProduct(DAO<Product> dao, String name, String description, String size, String category, Double price) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setSize(ItemSize.getByDescription(size));
        product.setCategory(Category.getByDescription(category));
        product.setPrice(price);
        product.setAvailable(true);
        dao.add(product);
    }
}
