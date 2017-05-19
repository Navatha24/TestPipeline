package unittests;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.math.BigDecimal;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;
import com.ensat.controllers.ProductController;
import com.ensat.entities.Product;
import com.ensat.services.ProductService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:spring-test.xml" })
public class ProductControllerTest {

	@Mock
	ProductService mockProductService;

	@InjectMocks
	ProductController productController;

	@Mock
	View mockView;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(productController).setSingleView(mockView).build();
	}

	@Test
	public void testListAllProducts() throws Exception {

		Iterable<Product> productList = Arrays.asList(
				new Product(1, 00001, "p-1", "Coffee", new BigDecimal("1.999999")),
				new Product(2, 00002, "p-2", "Sugar", new BigDecimal("1.5")));

		when(mockProductService.listAllProducts()).thenReturn(productList);

		mockMvc.perform(get("/products")).andExpect(status().isOk())
				.andExpect(content().contentType("text/plain;charset=ISO-8859-1")).andDo(MockMvcResultHandlers.print());

		verify(mockProductService, times(1)).listAllProducts();
		verifyNoMoreInteractions(mockProductService);

	}

}
