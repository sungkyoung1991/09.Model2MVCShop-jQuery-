package com.model2.mvc.web.product;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@RestController
@RequestMapping("/product/*")
public class ProductRestController {

	/// Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	public ProductRestController() {
		System.out.println(this.getClass());
	}

	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;

	@Value("#{commonProperties['pageSize']}")
	int pageSize;

	@RequestMapping(value = "json/addProduct", method = RequestMethod.POST)
	public Product addProduct(@RequestBody Product product) throws Exception{
		System.out.println("json/addProduct........");
		
		System.out.println("product..........." + product);
		
		
		productService.addProduct(product);
		
		System.out.println("after product.......... " + product);
		
		return product;
	}
	@RequestMapping( value="json/getProduct/{value}", method=RequestMethod.GET )
	public Map getProduct(@PathVariable int value) throws Exception{
		
		System.out.println("/product/json/getProduct : GET");
		
		Product returnProduct = new Product();
		
		returnProduct=productService.getProduct(value);
		
		returnProduct.setProdName("¼º°æ");
		
		System.out.println("returnProduct............."+returnProduct);
		
		Map map = new HashMap();
		map.put("product", returnProduct);
		
		return map;
	}

	@RequestMapping(value = "json/updateProductView", method = RequestMethod.GET)
	public Product updateProductView(@PathVariable int prodNo) throws Exception {

		System.out.println("/updateProductView");

		Product dbProduct = productService.getProduct(prodNo);

		return dbProduct;
	}

	@RequestMapping("json/updateProduct")
	public Product updateProduct(@RequestBody Product product)
			throws Exception {

		System.out.println("/updateProduct");

		productService.updateProduct(product);
		System.out.println("updateProduct : " + product);

		return product;
	}

	@RequestMapping(value = "json/listProduct", method = RequestMethod.POST)
	public Map getProductList(@RequestBody Search search)
			throws Exception {

		System.out.println("/listProduct");

		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);

		Map<String, Object> map = productService.getProductList(search);

		Page resultPage = new Page(search.getCurrentPage(), ((Integer) map.get("totalCount")).intValue(), pageUnit,
				pageSize);

		map.put("search", search);
		map.put("list", map.get("list"));
		map.put("resultPage", resultPage);

		return map;

	}

}