package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.domain.DriverInfo;
import com.example.demo.domain.DriverInfoRepository;
import com.example.demo.domain.PassengerInfo;
import com.example.demo.domain.PassengerInfoRepository;

import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class DistanceGetController {//0

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private PassengerInfoRepository passengerRepositoy;
	@Autowired
	private DriverInfoRepository driverRepositoy;
	
	
	  	
	  	
	//司机行程距离
    public int getDistance(){
    	
    	ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		HttpSession session = (HttpSession) request.getSession();
    	
    	double lat =  (double)  session.getAttribute("start2");
    	double lng =  (double)  session.getAttribute("start1");
    	double lat1 = (double)  session.getAttribute("end2");
    	double lng1 = (double)  session.getAttribute("end1");


		String url="https://restapi.amap.com/v3/direction/driving?origin="+lat+","+lng+"&destination="+lat1+","+lng1+"&extensions=all&output=json&key=924c44ade82fbc30ab8e19ffba3c67e6";
        JSONObject json = restTemplate.getForEntity(url, JSONObject.class).getBody();
        JSONArray jsonarray;
        JSONObject jsonObj;
        String object = json.toJSONString();
        jsonObj  = JSONObject.parseObject(object);
        jsonObj=(JSONObject)jsonObj.get("route");//route是键值，定位到这，然后这句话相当于去掉{
        jsonarray = (JSONArray)(jsonObj.get("paths"));
        String a= jsonarray.getString(0);
        jsonObj  = JSONObject.parseObject(a);
        String b = jsonObj.toJSONString();
        jsonObj  = JSONObject.parseObject(b);
        String distance=(String)jsonObj.get("distance");
        int d=Integer.parseInt(distance);
        return d;//司机行程d

       
	}

    //司机接送乘客新距离
   
    public String[] getPosition(){
    	
    	
    	ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		HttpSession session = (HttpSession) request.getSession();
		
		Long b1 = passengerRepositoy.count();//获取总行数
		String[] dis = new String[(int) (b1+1-1)];
		
		for(int i=0; i<b1 ;i++){
			List<PassengerInfo> abc = passengerRepositoy.findAll();
			PassengerInfo def= abc.get(i);//获取乘客表第i个元素
			int a1 = def.getId(); //id
			
		PassengerInfo passengerEntity = passengerRepositoy.findPassengerInfoById(a1);
		double lat2 = passengerEntity.getStartPosition2();
		double lng2 = passengerEntity.getStartPosition1();
		double lat3 = passengerEntity.getEndPosition2();
		double lng3 = passengerEntity.getEndPosition1();
		
		//当前司机
		double lat =  (double)  session.getAttribute("start2");
    	double lng =  (double)  session.getAttribute("start1");
    	double lat1 = (double)  session.getAttribute("end2");
    	double lng1 = (double)  session.getAttribute("end1");
		
		String url ="https://restapi.amap.com/v3/direction/driving?key=924c44ade82fbc30ab8e19ffba3c67e6&origin="+lat+","+lng+"&destination="+lat1+","+lng1+"&originid=&destinationid=&extensions=all&waypoints="+lat2+","+lng2+"&waypoints="+lat3+","+lng3+"";
		
		JSONObject json = restTemplate.getForEntity(url, JSONObject.class).getBody();

		JSONArray jsonarray;
        JSONObject jsonObj;
        
        String object = json.toJSONString();
        jsonObj  = JSONObject.parseObject(object);
        jsonObj=(JSONObject)jsonObj.get("route");//route是键值，定位到这，然后这句话相当于去掉 {       
        jsonarray = (JSONArray)(jsonObj.get("paths"));        
        String a= jsonarray.getString(0);
        jsonObj  = JSONObject.parseObject(a); 
        String b = jsonObj.toJSONString();
        jsonObj  = JSONObject.parseObject(b);
        String distance1 =(String)jsonObj.get("distance");
     
         int d1=Integer.parseInt(distance1);//接送乘客新距离d1        
         int  d = getDistance();
         double s = getCompatibility(d1-d);
         NumberFormat nt = NumberFormat.getPercentInstance();//获取格式化对象
       //设置百分数精确度2即保留两位小数
         nt.setMinimumFractionDigits(0);
         dis[i] = nt.format(s);  
	}

		return dis;//返回一个数组,里面是距离差对应的匹配度（按实际乘客订单表顺序遍历记录的）
		
	}

  //乘客行程距离
    public int getDistance1(){
    	
    	ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		HttpSession session = (HttpSession) request.getSession();
    	
    	double lat =  (double)  session.getAttribute("start02");
    	double lng =  (double)  session.getAttribute("start01");
    	double lat1 = (double)  session.getAttribute("end02");
    	double lng1 = (double)  session.getAttribute("end01");


		String url="https://restapi.amap.com/v3/direction/driving?origin="+lat+","+lng+"&destination="+lat1+","+lng1+"&extensions=all&output=json&key=924c44ade82fbc30ab8e19ffba3c67e6";
        JSONObject json = restTemplate.getForEntity(url, JSONObject.class).getBody();
        JSONArray jsonarray;
        JSONObject jsonObj;
        String object = json.toJSONString();
        jsonObj  = JSONObject.parseObject(object);
        jsonObj=(JSONObject)jsonObj.get("route");//route是键值，定位到这，然后这句话相当于去掉{
        jsonarray = (JSONArray)(jsonObj.get("paths"));
        String a= jsonarray.getString(0);
        jsonObj  = JSONObject.parseObject(a);
        String b = jsonObj.toJSONString();
        jsonObj  = JSONObject.parseObject(b);
        String distance=(String)jsonObj.get("distance");
        int d=Integer.parseInt(distance);
        return d;//乘客行程d
    	
    }
	
  //乘客-司机新距离
  
    public String[] getPosition1(){
    	
    	ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		HttpSession session = (HttpSession) request.getSession();
		
		Long b1 = driverRepositoy.count();//获取总行数
		String[] dis1 = new String[(int) (b1+1-1)];
		
		for(int i=0; i < b1 ;i++){
			
			List<DriverInfo> abc = driverRepositoy.findAll();
			DriverInfo def= abc.get(i);//获取乘客表第i个元素
			int a1 = def.getId(); //id
			
		DriverInfo driverEntity = driverRepositoy.findDriverInfoById(a1);
		double lat2 = driverEntity.getStartPosition2();
		double lng2 = driverEntity.getStartPosition1();
		double lat3 = driverEntity.getEndPosition2();
		double lng3 = driverEntity.getEndPosition1();
		
		//当前乘客
		double lat0 =  (double)  session.getAttribute("start02");
    	double lng0 =  (double)  session.getAttribute("start01");
    	double lat01 = (double)  session.getAttribute("end02");
    	double lng01 = (double)  session.getAttribute("end01");
		
		String url ="https://restapi.amap.com/v3/direction/driving?key=924c44ade82fbc30ab8e19ffba3c67e6&origin="+lat0+","+lng0+"&destination="+lat01+","+lng01+"&originid=&destinationid=&extensions=all&waypoints="+lat2+","+lng2+"&waypoints="+lat3+","+lng3+"";
		
		JSONObject json = restTemplate.getForEntity(url, JSONObject.class).getBody();

		JSONArray jsonarray;
        JSONObject jsonObj;
        
        String object = json.toJSONString();
        jsonObj  = JSONObject.parseObject(object);
        jsonObj=(JSONObject)jsonObj.get("route");//route是键值，定位到这，然后这句话相当于去掉 {       
        jsonarray = (JSONArray)(jsonObj.get("paths"));        
        String a= jsonarray.getString(0);
        jsonObj  = JSONObject.parseObject(a); 
        String b = jsonObj.toJSONString();
        jsonObj  = JSONObject.parseObject(b);
        String distance1 =(String)jsonObj.get("distance");
     
         int d1=Integer.parseInt(distance1);       
         int  d = getDistance1();
         double s = getCompatibility(d1-d);
         NumberFormat nt = NumberFormat.getPercentInstance();//获取格式化对象
       //设置百分数精确度2即保留两位小数
         nt.setMinimumFractionDigits(0);
         dis1[i] = nt.format(s);  
	}
		return dis1;
    }
    
	//定义距离差——匹配度函数
	public double getCompatibility(int extra_distance) {
		
		//多走0.5公里减1%
		double compatibility  = 1-(extra_distance /500)*0.01;	
		return compatibility;
		
	}
	
	//当前司机：返回匹配度——乘客信息
		@RequestMapping("getpassenger")
		public JSONObject[] getcomp() {
			
			Long b = passengerRepositoy.count();//获取总行数
			String[] ab = getPosition();//ab是来接收dis的数组
			JSONObject[] csu = new JSONObject[(int) (b+1-1)];
			
			for(int i=0;i<b;i++) {
				List<PassengerInfo> abc = passengerRepositoy.findAll();
				PassengerInfo def= abc.get(i);//获取乘客表第i个元素
				int a = def.getId(); //获取id
				String name = def.getUser().getName();
				
				//存109乘客各种信息,先从旧表取出来再放进新表
				PassengerInfo passenger = passengerRepositoy.findPassengerInfoById(a);

				Date k1 = passenger.getDepartureTimeTo();//最晚出发时间
				String k11 = k1.toString();
				k11 = k11.substring(0,k11.length() - 5);//规范化
				String k2 = passenger.getStartPlace();//起点
				String k3 = passenger.getEndPlate();//终点
				
				String v = ab[i].replace("%", "");
				double num = Double.parseDouble(v);
				if(num > 50) {
				JSONObject jsonObject = new JSONObject();  
		        jsonObject.put("name",name);  
		        jsonObject.put("compatibility", ab[i]);  
		        jsonObject.put("startplace", k2);  
		        jsonObject.put("endplace", k3);  
		        jsonObject.put("time", k11);  
				
		        csu[i] = jsonObject;
				}
				else
					continue;
				
			}
			
			
			return csu;
			
		}
	
		//当前乘客：返回匹配度——司机信息
				@RequestMapping("getdriver")
				public JSONObject[] getcomp1() {
					
					Long b = driverRepositoy.count();//获取总行数
					String[] ab = getPosition1();
					JSONObject[] csu = new JSONObject[(int) (b+1-1)];

					for(int i=0;i<b;i++) {
						
						List<DriverInfo> abc = driverRepositoy.findAll();
						DriverInfo def= abc.get(i);//获取司机表第i个元素
						int a = def.getId(); //id
						
						DriverInfo nn= abc.get(i);
						String name = nn.getUser().getName();
						
						//存109乘客各种信息,先从旧表取出来再放进新表
						DriverInfo driver = driverRepositoy.findDriverInfoById(a);
						Date k1 = driver.getDepartureTimeTo();//最晚出发时间
						String k11 = k1.toString();
						k11 = k11.substring(0,k11.length() - 5);//规范化
						String k2 = driver.getStartPlace();//起点
						String k3 = driver.getEndPlate();//终点
						
						JSONObject jsonObject = new JSONObject(); 
						String v = ab[i].replace("%", "");
						double num = Double.parseDouble(v);
						if(num > 50) {
				        jsonObject.put("name",name);  
				        jsonObject.put("compatibility", ab[i]);  
				        jsonObject.put("startplace", k2);  
				        jsonObject.put("endplace", k3);  
				        jsonObject.put("time", k11);  
						
				        csu[i] = jsonObject;
				        }
						else
							continue;
					}
					
					
					return csu;
					
				}
	
				
				
}//0	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	

