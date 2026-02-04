package jp.ken.rental.application.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.ken.rental.form.ProductEntity;
import jp.ken.rental.form.ProductForm;
import jp.ken.rental.repository.ProductRepository;

@Service
public class ProductInsertService {

	private ProductRepository productRepository;
	private ModelMapper modelMapper;
	
	public ProductInsertService(ProductRepository productRepository, ModelMapper modelMapper) {
		this.productRepository = productRepository;
		this.modelMapper = modelMapper;
	}
	@Transactional(rollbackFor = Exception.class)
	public int registItem(ProductForm form) throws Exception{
		ProductEntity entity = null;
		
		entity = convert(form);
		
		int resultRow = productRepository.registitem(entity);
		
		return resultRow;
		
	}
	
	private ProductEntity convert(ProductForm form) {
		
		String changeFormatDate = form.getArrivalDate().replace("/","-");
		form.setArrivalDate(changeFormatDate);
		
		String changeFormatDate2 = form.getReleaseDate().replace("/","-");
		form.setReleaseDate(changeFormatDate2);
		
		ProductEntity entity = modelMapper.map(form, ProductEntity.class);
		
		return entity;
	}
	
}
