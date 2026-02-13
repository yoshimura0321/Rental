package jp.ken.rental.application.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.ken.rental.form.ProductEntity;
import jp.ken.rental.form.ProductForm;
import jp.ken.rental.repository.ProductRepository;

@Service
public class ProductUpdateService {

	private ProductRepository productRepository;
	private ModelMapper modelMapper;
	
	public ProductUpdateService(ProductRepository productRepository, ModelMapper modelMapper) {
		
		this.productRepository=productRepository;
		this.modelMapper=modelMapper;
	}
	@Transactional(rollbackFor = Exception.class)
	public int updateitem(ProductForm productForm) throws Exception{
	
		ProductEntity productEntity = convert(productForm);
		int rows = productRepository.updateProduct(productEntity)+productRepository.updateStock(productEntity.getProductId(), productEntity.getStockQuantity());
		
		if(rows <2) {
			throw new Exception("更新できませんでした");
		}
		return rows;
	}
	
	private ProductEntity convert(ProductForm productForm) {
		String changeFormatDate = productForm.getArrivalDate().replace("/", "-");
		productForm.setArrivalDate(changeFormatDate);
		String changeFormatDate2 = productForm.getReleaseDate().replace("/","-");
		productForm.setReleaseDate(changeFormatDate2);
		
		ProductEntity entity = modelMapper.map(productForm, ProductEntity.class);
		
		return entity;
	}
}
