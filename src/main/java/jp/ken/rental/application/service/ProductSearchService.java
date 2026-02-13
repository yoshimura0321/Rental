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
    
    // ★ POST：名前検索
    public List<ProductForm> getProductList(ProductForm form)throws Exception{
        
        String keyword = form.getProductName();
        String category = form.getProductCategory();
        
        boolean noName = (keyword == null || keyword.isBlank());
        boolean noCategory = (category == null || category.isBlank());
        
        List<ProductEntity> entityList;
        
        if (noName && noCategory) {
            return List.of();
        } else if (!noName && noCategory) {
            // 名前だけ
            entityList = productRepository.getProductByName(keyword);
        } else if (noName && !noCategory) {
            // カテゴリーだけ
            entityList = productRepository.getProductByCategory(category);
        } else {
            // 名前＋カテゴリー
            entityList = productRepository.getProductByNameAndCategory(keyword, category);
        }
        
        return convert(entityList);
    }

    // ★ GET：最新5件検索
    public List<ProductForm> getLatest5Products() throws Exception {
        List<ProductEntity> entityList = productRepository.getProductByArrivalDate();
        return convert(entityList);
    }
    
    // カテゴリ別で最新5件取得
    public List<ProductForm> getLatest5ProductsByCategory(String category) throws Exception {
        List<ProductEntity> entityList = productRepository.getProductByArrivalDateByCategory(category);
        return convert(entityList);
    }

    private List<ProductForm> convert(List<ProductEntity> entityList){
        
        List<ProductForm> formList = new ArrayList<>();
        
        for(ProductEntity entity : entityList) {
            ProductForm form = modelMapper.map(entity, ProductForm.class);
            
            
         // 仮の画像名
            if (form.getThumbnail() == null || form.getThumbnail().isBlank()) {
                form.setThumbnail("no-image.png");
            }
            
            formList.add(form);
        }
        
        return formList;
    }

    public ProductForm getProductById(int productId) throws Exception {

        ProductEntity entity = productRepository.getProductById(productId);

        if (entity == null) {
            return null;
        }

        return modelMapper.map(entity, ProductForm.class);
    }
    public List<ProductForm> getCartProduct()throws Exception{
    	List<ProductForm> formList =null;
    	List<ProductEntity> entityList = null;
    	
    	entityList = productRepository.adminProductSearch();
    	
    	formList = convert(entityList);
    	
    	return formList;
    }
    
    public List<ProductForm> getAdminProduct()throws Exception{
    	List<ProductForm> formlist =null;
    	List<ProductEntity> entitylist = null;
    	
    	entitylist = productRepository.adminProductSearch();
    	
    	formlist = convert(entitylist);
    	
    	return formlist;
    }
    
    public Boolean checkrental(int productId)throws Exception{
    	ProductEntity entity = productRepository.checkrental(productId);
    	return entity.getRentalCount()< entity.getStockQuantity();
    }
    
    public List<ProductForm> getAdminProductByName(String name)throws Exception{
    	List<ProductEntity> entity=productRepository.adminProductSearchByName(name);
    	List<ProductForm> form=null;
    	
    	form = convert(entity);
    	
    	return form;
    }

}

