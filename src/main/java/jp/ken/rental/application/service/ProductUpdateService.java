package jp.ken.rental.application.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
	public int updateitem(ProductForm productForm, MultipartFile imageFile) throws Exception{
		
		// DBから既存データ取得
	    ProductEntity db = productRepository.getProductById(Integer.parseInt(productForm.getProductId()));

	    if (imageFile != null && !imageFile.isEmpty()) {
	        String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();

	        // ディレクトリがなければ作成
	        Path uploadDir = Paths.get("src/main/resources/static/images");
	        if (!Files.exists(uploadDir)) {
	            Files.createDirectories(uploadDir);
	        }

	        Path savePath = uploadDir.resolve(fileName);
	        Files.copy(imageFile.getInputStream(), savePath, StandardCopyOption.REPLACE_EXISTING);

	        productForm.setThumbnail(fileName);
	    } else {
	        // 既存画像があればそれを保持
	        if (db.getThumbnail() != null && !db.getThumbnail().isEmpty()) {
	            productForm.setThumbnail(db.getThumbnail());
	        } else {
	            productForm.setThumbnail("no_image.png"); // 仮画像
	        }
	    }

	    ProductEntity entity = modelMapper.map(productForm, ProductEntity.class);
	    return productRepository.updateProduct(entity);
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
