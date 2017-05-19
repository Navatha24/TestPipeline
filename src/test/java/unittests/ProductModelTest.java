package unittests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import java.math.BigDecimal;
import org.junit.Test;
import com.ensat.entities.Product;

public class ProductModelTest {

	@Test
	public void testProductModelWithoutData() {
		Product product = new Product();
		assertThat(product.getVersion(), is(nullValue()));
		assertThat(product.getId(), is(nullValue()));
		assertThat(product.getProductId(), is(nullValue()));
		assertThat(product.getName(), is(nullValue()));
		assertThat(product.getPrice(), is(nullValue()));
	}

	@Test
	public void testProductModelWithData() {
		Product product = new Product(1, 100, "P1", "Coffee", new BigDecimal("1.999999"));
		assertThat(product.getVersion(), is(equalTo(100)));
		assertThat(product.getId(), is(equalTo(1)));
		assertThat(product.getProductId(), is(equalTo("P1")));
		assertThat(product.getName(), is(equalTo("Coffees")));
		assertThat(product.getPrice(), is(equalTo(new BigDecimal("1.999999"))));
	}
}
