package cn.com.taiji.mongoweb.controller;


import cn.com.taiji.mongoweb.model.User;
import cn.com.taiji.mongoweb.service.UserService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.geo.Point;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 入口
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;


	@RequestMapping("/get/{id}")
	public User getUser(@PathVariable int id) {
		return userService.getUser(id);
	}

	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		userService.remove(id);
		return "delete sucess";
	}

	@RequestMapping("/add")
	public String insert() {
		User user =new User(16, ""+16, 16);
		userService.insert(user);
		return "sucess";
	}

	@RequestMapping("/insert")
	public String insertAll() {
		List<User> list = new ArrayList<>();
		for (int i = 10; i < 15; i++) {
			list.add(new User(i, "" + i, i));
		}
		userService.insertAll(list);
		return "sucess";
	}
	
	@RequestMapping("/find/all")
	public List<User> find(){
		return userService.findAll();
	}
	
	@RequestMapping("/find/{start}")
	public List<User> findByPage(@PathVariable int start,User user){
		Pageable pageable=new PageRequest(start, 2);
		return userService.findByPage(user, pageable);
	} 
	
	@RequestMapping("/update/{id}")
	public String update(@PathVariable int id){
		User user =new User(id, ""+1, 1);
		userService.update(user);
		return "sucess";
	}

	@RequestMapping("/geo")
	public List<DBObject> geo(){
		DBObject query = new BasicDBObject();
		Point point = new Point(118.783799,31.979234);
//		point.setLng(118.783799);
//		point.setLat(31.979234);
		int limit = 5;
		Long maxDistance = 5000L; // 米
		List<DBObject> list = userService.geo("point.test", query, point, limit, maxDistance);
		for(DBObject obj : list)
			System.out.println(obj);
		return list;
	}
	
//method1
	
//	@RequestMapping("/withinPolygon")
//	public List<DBObject> withinPolygon(){
//		DBObject query = new BasicDBObject();
//		DBObject field = new BasicDBObject();
//		int limit = 3;//
//		List<double[]> list = new ArrayList<>();
//		list.add(new double[]{110,40});
//		list.add(new double[]{110,0});
//		list.add(new double[]{120,0});
//		list.add(new double[]{120,40});
//		list.add(new double[]{110,40});
	
//	    List<List<double[]>> list2 = new ArrayList<>();
//	      list2.add(list);
//	      list2.add(list1);
	
//		List<DBObject> listfinal = userService.withinPolygon("point.test",  "loc",
//				list2, field, query, limit);
//		for(DBObject obj : listfinal)
//			System.out.println(obj);
//		return listfinal;
//	}
	
//	method2
	@RequestMapping("/withinPolygon1")
	public List<DBObject> withinPolygon1(){
		DBObject query = new BasicDBObject();
		DBObject field = new BasicDBObject();
		int limit = 2;//
		List<double[]> list = new ArrayList<>();
		list.add(new double[]{110,40});
		list.add(new double[]{110,0});
		list.add(new double[]{120,0});
		list.add(new double[]{120,40});
		list.add(new double[]{110,40});
		
	
		List<double[]> list1 = new ArrayList<>();
		list1.add(new double[]{200,40});
		list1.add(new double[]{210,0});
		list1.add(new double[]{220,0});
		list1.add(new double[]{220,40});
		list1.add(new double[]{210,40});
		
//		List<List<double[]>> list2 = new ArrayList<>();
//		list2.add(list);
//		list2.add(list1);
//		
		List<DBObject> listfinal = userService.withinPolygon("point.test",  "loc",
				list,list1, field, query, limit);
		for(DBObject obj : listfinal)
			System.out.println(obj);
	
	
		return listfinal;
	}
}
