package service;

import java.util.List;
import java.util.Map;

import dao.UserDao;
import entity.User;

public class UserService {
	private UserDao userDao=new UserDao();

	public User login(String username, String password) {
		
		return userDao.findByUsername(username,password);
	}

	public List<Map<String, Object>> getAllDingdan(String userid) {
		return userDao.findOrdersByUserid(userid);
	}



	public List<Map<String, Object>> getHotList() {
		return userDao.findHotFood();
	}

	public List<Map<String, Object>> getTypeList() {
		return userDao.findTypeList();
	}

	public List<Map<String, Object>> getOneMenu(String typeid) {
		return userDao.findMenuByTypeid(typeid);
	}

	public void addCart(String userid, String foodid) {
		userDao.insertCart(userid,foodid);
		
	}

	public List<Map<String, Object>> getCartCount(String userid, String foodid) {
		return userDao.findCartCount(userid,foodid);
	}

	public void updateCart(String id, String count) {
		userDao.updateCartCount(id,count);
		
	}

	public List<Map<String, Object>> getCart(String userid) {
		return userDao.findCartByUserid(userid);
	}

	public void updateCartCount(String id, String count) {
		userDao.updateCartCount2(id,count);
		
	}

}
