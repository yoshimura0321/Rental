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
	
	public List<CartForm> adminrental(int productId)throws Exception{
		List<CartEntity> entitylist = cartRepository.admincart(productId);
		List<CartForm> formlist = convert(entitylist);
		return formlist;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int rental(int userId,int productId)throws Exception{
		int num = cartRepository.rental(userId, productId);
		if(num<2) {
			throw new Exception("レンタル処理に失敗しました");
		}
		return num;
	}
	
	@Transactional(readOnly = true)
	public List<CartEntity> getCurrentRentals(int userId) throws Exception {
	    return cartRepository.getRentalsByUserId(userId);
	}

	@Transactional(readOnly = true)
	public List<CartEntity> getRentalHistory(int userId) throws Exception {
	    return cartRepository.getRentalHistoryByUserId(userId);
	}
	
	public List<CartForm> getreturnlist()throws Exception{
		List<CartForm> formlist = null;
		List<CartEntity> entitylist = cartRepository.getreturnlist();
		
		formlist = convert(entitylist);
		
		return formlist;
	}
	
	//商品名でリターンリスト
	public List<CartForm> getreturnlistByProductName(String name)throws Exception{
		List<CartForm> formlist = null;
		List<CartEntity> entitylist = cartRepository.getreturnlistByProductName(name);
		
		formlist = convert(entitylist);
		
		return formlist;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int doreturn(int userId,int productId)throws Exception{
		int num = cartRepository.doreturn(userId, productId);
		if(num<2) {
			throw new Exception("返却処理に失敗しました");
		}
		return num;
	}
	
	public List<CartForm> getDirectorRank()throws Exception{
		List<CartEntity> entitylist =cartRepository.getDirectorRank();
		List<CartForm> formlist = convert(entitylist);
		
		
		return formlist;
	}
	
	public List<CartForm> getMusicianRank()throws Exception{
		List<CartEntity> entitylist =cartRepository.getMusicianRank();
		List<CartForm> formlist = convert(entitylist);
		
		
		return formlist;
	}
	
	public List<CartForm> getProductRank()throws Exception{
		List<CartEntity> entitylist =cartRepository.getProductRank();
		List<CartForm> formlist = convert(entitylist);
		
		
		return formlist;
	}
	
	public int countPendingRentals() throws Exception {
	    return cartRepository.countByStatus("cart"); 
	}
	
	public int countReturns() throws Exception {
		return cartRepository.countByRental("cart");
	}
	@Transactional(rollbackFor=Exception.class)
	public int upPriority(int userId,int cartId,int priority)throws Exception{
		CartEntity entity=cartRepository.upSearch(userId, priority);
		int num =0;
		if(entity != null) {
		num = cartRepository.switchpriority(cartId, priority, entity.getCartId(), entity.getPriority());
		
		if(num<2) {
			throw new Exception("優先度変更できませんでした");
		}
		}else {
			num =0;
		}
		return num;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int downPriority(int userId,int cartId,int priority)throws Exception{
		CartEntity entity=cartRepository.downSearch(userId, priority);
		int num = cartRepository.switchpriority(cartId, priority, entity.getCartId(), entity.getPriority());
		
		if(num<2) {
			throw new Exception("優先度変更できませんでした");
		}
		
		return num;
	}
	
	//レンタルのためのリスト
	public List<CartForm> getRentalusers()throws Exception{
		List<CartEntity> entityList=cartRepository.getRentalusers();
		List<CartForm> formlist = convert(entityList);
		
		return formlist;
	}
	
	//ユーザ名でのレンタルリスト
	public List<CartForm> getRentalusersByuser(String name)throws Exception{
		List<CartEntity> entityList=cartRepository.getRentalusersByuser(name);
		List<CartForm> formlist = convert(entityList);
		
		return formlist;
	}
}
