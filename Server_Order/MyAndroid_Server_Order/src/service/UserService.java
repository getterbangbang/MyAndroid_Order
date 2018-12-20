package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Jpush;

import dao.UserDao;
import entity.User;

public class UserService {
	private UserDao userDao=new UserDao();

	public User login(String username, String password,String role) {
		
		return userDao.findByUsername(username,password,role);
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

	public void creatOrder(String ordername, String userid, String orderids,
			String prizesum, String dateString,String tableid) {
		String tablenum=userDao.findTablenumById(tableid);
		long id=userDao.insertOrder(ordername,userid,orderids,prizesum,dateString,tableid);
		
		Map<String, String> parm =new HashMap<String, String>();
	    //这是我的文章id
	    parm.put("id",(""+1).trim());
	    //文章标题
	    parm.put("Atitle","通知");
	    //设置提示信息,内容是文章标题
	    parm.put("msg",tablenum+"号桌有新订单！订单号："+id);
	    
	    System.out.println(tablenum+"号桌有新订单！订单号："+id);
	 
	    //然后调用安卓的
	    Jpush.jpushWorkerAndroid(parm);
		
	}

	public List<Map<String, Object>> getOneOrder(String orderid) {
		// TODO Auto-generated method stub
		return userDao.findOrderById(orderid);
	}

	public List<Map<String, Object>> getOneOrderMenu(String cartid) {
		return userDao.findOrderMenuById(cartid);
	}

	public List<Map<String, Object>> getTable() {
		return userDao.findAllTable();
	}

	public List<Map<String, Object>> getWorkerAllDingdan() {
		return userDao.findAllOrder();
	}

	public List<Map<String, Object>> getTableList() {
		return userDao.findTableList();
	}

	public int register(String username, String password) {
		return userDao.insertUser(username,password);
	}

	public int canRegister(String username) {
		return userDao.findByUsername(username);
	}

}
