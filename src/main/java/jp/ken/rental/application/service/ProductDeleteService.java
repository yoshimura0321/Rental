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
		
		int rows = productRepository.deleteItem(productId)+productRepository.deleteStock(productId);
		
		if(rows <2) {
			throw new Exception("商品削除に失敗しました");
		}
		return rows;
	}

}
