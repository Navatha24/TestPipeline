package integrationtests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import org.junit.Test;

public class ProductConTrollerIT {

	@Test
	public void testGetProducts() {
		given().when().get("http://localhost:8080/products").then().statusCode(200)
				.contentType("text/plain;charset=UTF-8").body(containsString("products"));
	}

}
