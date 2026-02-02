package jp.ken.rental.application.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.ken.rental.form.CartEntity;
import jp.ken.rental.form.CartForm;
import jp.ken.rental.repository.CartRepository;

@Service
public class CartService {
	private CartRepository cartRepository;
	private ModelMapper modelMapper;
	
	public CartService(CartRepository cartRepository,ModelMapper modelMapper) {
		this.cartRepository=cartRepository;
		this.modelMapper=modelMapper;
	}
	
	public List<CartForm> getCart(int userId)throws Exception{
		
		List<CartEntity> entityList =null;
		List<CartForm> formList =null;
		
		entityList = cartRepository.getCartByUserId(userId);
		formList = convert(entityList);
		
		return formList;
		
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int addCart(int userId,int productId)throws Exception{
		
		CartEntity entity = new CartEntity();
		entity.setUserId(userId);
		entity.setProductId(productId);
		entity.setStatus("cart");
		
		int num = cartRepository.addCart(entity);
		
		return num;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int deleteCart(int userId, int productId) throws Exception{
		CartEntity entity = new CartEntity();
		entity.setUserId(userId);
		entity.setProductId(productId);
		
		int num = cartRepository.deleteCart(entity);
		
		return num;
	}
	
	private List<CartForm> convert(List<CartEntity> entitylist){
		List<CartForm> formlist = new ArrayList<CartForm>();
		
		for(CartEntity entity : entitylist) {
			CartForm form = modelMapper.map(entity, CartForm.class);
			formlist.add(form);
		}
		return formlist;
	}
}
