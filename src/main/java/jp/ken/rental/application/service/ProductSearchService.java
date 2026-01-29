package jp.ken.rental.application.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jp.ken.rental.form.ProductEntity;
import jp.ken.rental.form.ProductForm;
import jp.ken.rental.repository.ProductRepository;

@Service
public class ProductSearchService {
	
	private ProductRepository productRepository;
	private ModelMapper modelMapper;
	
	public ProductSearchService(ProductRepository productRepository, ModelMapper modelMapper) {
		this.productRepository = productRepository;
		this.modelMapper = modelMapper;
	}
	
	public List<ProductForm> getProductList(ProductForm form)throws Exception{
		
		String keyword = form.getProductName();

		// null / 空文字対策
        if (keyword == null || keyword.isBlank()) {
            return List.of();
        }

        List<ProductEntity> entityList =
        		productRepository.getProductByName(keyword);
        return convert(entityList);
    }

	private List<ProductForm> convert(List<ProductEntity> entityList){
		
		List<ProductForm> formList = new ArrayList<ProductForm>();
		
		for(ProductEntity entity : entityList) {
			ProductForm form = modelMapper.map(entity, ProductForm.class);
			formList.add(form);
		}
		
		return formList;
		
	}

}
