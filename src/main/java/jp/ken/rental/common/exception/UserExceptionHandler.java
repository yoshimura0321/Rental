package jp.ken.rental.common.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException; // 例外クラス
import org.springframework.ui.Model; // Modelオブジェクト
import org.springframework.web.bind.annotation.ControllerAdvice; // クラスに付与
import org.springframework.web.bind.annotation.ExceptionHandler; // メソッドに付与
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EmptyResultDataAccessException.class)
	protected String handlerException(EmptyResultDataAccessException e,Model model) {
		model.addAttribute("error", e + " 該当データがありません");
		return "error/error";
	}
	
	@ExceptionHandler(DataAccessException.class)
	protected String
		handlerException(DataAccessException e, Model model) {
		model.addAttribute("error", e + " SQL文でエラーが発生しました");
		return "error/error";
	}

	@ExceptionHandler(Exception.class)
	protected String handlerException(Exception e, Model model) {
		model.addAttribute("error", e + " システムエラーが発生しました");
		return "error/error";
	}
}
