package jp.ken.rental.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.ken.rental.repository.ProductRepository;

@Service
public class ProductDeleteService {
	
	private ProductRepository productRepository;
	
	public ProductDeleteService(ProductRepository productRepository) {
		
		this.productRepository = productRepository;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int deleteProduct(int productId) throws Exception {
		
		int rows = productRepository.deleteItem(productId);
		
		if(rows == 0) {
			throw new IllegalArgumentException("商品が存在しません");
		}
		return rows;
	}

}
