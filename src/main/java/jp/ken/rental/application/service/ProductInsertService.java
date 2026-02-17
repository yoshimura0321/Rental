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
public class ProductInsertService {

	private ProductRepository productRepository;
	private ModelMapper modelMapper;
	
	public ProductInsertService(ProductRepository productRepository, ModelMapper modelMapper) {
		this.productRepository = productRepository;
		this.modelMapper = modelMapper;
	}
	@Transactional(rollbackFor = Exception.class)
	public int registItem(ProductForm form, MultipartFile imageFile) throws Exception {

	    // 画像保存処理
	    if (imageFile != null && !imageFile.isEmpty()) {
	        String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
	        Path uploadDir = Paths.get("src/main/resources/static/images");
	        if (!Files.exists(uploadDir)) {
	            Files.createDirectories(uploadDir);
	        }
	        Path savePath = uploadDir.resolve(fileName);
	        Files.copy(imageFile.getInputStream(), savePath, StandardCopyOption.REPLACE_EXISTING);

	        form.setThumbnail(fileName);
	    } else {
	        // 画像がない場合は仮画像
	        form.setThumbnail("no_image.png");
	    }

	    // 日付フォーマット変換
	    form.setArrivalDate(form.getArrivalDate().replace("/", "-"));
	    form.setReleaseDate(form.getReleaseDate().replace("/", "-"));

	    ProductEntity entity = modelMapper.map(form, ProductEntity.class);

	    int resultRow = productRepository.registitem(entity) + productRepository.registstock(entity.getStockQuantity());
	    if (resultRow < 2) {
	        throw new Exception("商品の登録に失敗しました");
	    }

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
