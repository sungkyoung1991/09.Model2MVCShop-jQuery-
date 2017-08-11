package com.model2.mvc.web.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;

//==> ȸ������ Controller
@RestController
@RequestMapping("/purchase/*")
public class PurchaseRestController {

	/// Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	// setter Method ���� ����

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	public PurchaseRestController() {
		System.out.println(this.getClass());
	}

	// ==> classpath:config/common.properties ,
	// @Value("#{commonProperties['pageUnit']}")
	@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;

	// @Value("#{commonProperties['pageSize']}")
	@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;

	@RequestMapping(value = "json/addPurchaseView/*", method = RequestMethod.GET)
	public Product addPurchaseView(@RequestBody Purchase purchase, @PathVariable int prodNo // pathVariable
																							// url경로에
																							// 변수삽입
																							// 바인딩하여
																							// 받는
																							// 기능.
	) throws Exception {

		System.out.println("addPurchaseView start..................");

		// product.setProTranCode(tranCode);
		Product product = productService.getProduct(prodNo);
		System.out.println("product..........." + product);

		purchase.setPurchaseProd(product);

		return product;

	}

	@RequestMapping(value = "json/addPurchase", method = RequestMethod.POST)
	public Purchase addPurchase(@RequestBody Purchase purchase) throws Exception {

		System.out.println("/addPurchase");

		System.out.println("purchaseCheck........................" + purchase);

		// purchase.getPurchaseProd().setProTranCode(purchase.getTranCode());

		// purchase.setBuyer(userService.getUser(buyerId));
		// purchase.setPurchaseProd(productService.getProduct(prodNo));

		purchaseService.addPurchase(purchase);

		return purchase;
	}

	@RequestMapping(value = "json/getPurchase/{tranNo}", method = RequestMethod.GET)
	public Purchase getPurchase(@PathVariable int tranNo // @RequestParam("prodNo")
															// int
															// prodNo,
	// @RequestParam("menu") String menu,
	) throws Exception {

		System.out.println("getPurchase");

		Purchase purchase = purchaseService.getPurchase(tranNo);

		return purchase;
	}

	@RequestMapping(value = "json/updatePurchaseView", method = RequestMethod.POST)
	public String updatePurchaseView(@RequestParam("tranNo") int tranNo, Model model) throws Exception {

		System.out.println("/updatePurchaseView");

		Purchase purchase = purchaseService.getPurchase(tranNo);

		model.addAttribute("purchase", purchase);

		return "forward:/purchase/updatePurchaseView.jsp";
	}

	@RequestMapping(value = "json/updatePurchase", method = RequestMethod.POST)
	public Purchase updatePurchase(@RequestBody Purchase purchase) throws Exception {

		System.out.println("updatePurchase......");

		// purchase.setDivyDate(purchase.getDivyDate().replaceAll("-", ""));

		// Purchase rePurchase = purchaseService.getPurchase(tranNo);
		//
		// System.out.println("tranNo Check....." + tranNo);
		//
		// System.out.println("get Check....................." + rePurchase); //
		// 기대값.. tranNo에대한 purchase 정보.
		//
		// User user = (User) request.getSession().getAttribute("user");
		//
		// rePurchase.setBuyer(user);
		// rePurchase.setDivyRequest(purchase.getDivyRequest());
		// rePurchase.setReceiverName(purchase.getReceiverName());

		purchaseService.updatePurchase(purchase);

		System.out.println("purchase Check........ : " + purchase);

		return purchase;
	}

	@RequestMapping(value = "json/listPurchase", method = RequestMethod.POST)
	public Map getPurchaseList(@RequestBody Search search, @RequestParam("buyerId") String buyerId) throws Exception {

		System.out.println("/listProduct");

		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);

		Map<String, Object> map = purchaseService.getPurchaseList(search, buyerId);

		Page resultPage = new Page(search.getCurrentPage(), ((Integer) map.get("totalCount")).intValue(), pageUnit,
				pageSize);

		map.put("search", search);
		map.put("list", map.get("list"));
		map.put("resultPage", resultPage);

		return map;
	}

	@RequestMapping(value = "json/updateTranCode", method = RequestMethod.POST)
	public Purchase updateTranCode(@RequestBody Purchase purchase) throws Exception {

		System.out.println("/updateTranCode ............");
		System.out.println("tranCode check .......... " + purchase.getTranCode());

		Product product = productService.getProduct(purchase.getPurchaseProd().getProdNo());

		purchase.setPurchaseProd(product);

		if (purchase.getTranCode().equals("1  ")) {
			purchase.setTranCode("2  ");
			purchaseService.updateTranCode(purchase);

		} else {
			purchase.setTranCode("3  ");
			purchaseService.updateTranCode(purchase);
		}

		return purchase;

	}

}