package com.example.demo.controller;


  
public class MapDistance { 
        
    private static double EARTH_RADIUS = 6378.137; 
    
    private static double rad(double d) { 
        return d * Math.PI / 180.0; 
    }
      
    /**
     * 根据两个位置的经纬度，来计算两地的距离（单位为KM）
     * 参数为String类型
     * @param lat1 用户经度
     * @param lng1 用户纬度
     * @param lat2 商家经度
     * @param lng2 商家纬度
     * @return
     */
    
  //lat经度， lng纬度,已知经纬度求两点间距离
    public static Double getDistance(Double lat1, Double lng1, Double lat2, Double lng2) {
    	
    
    	//化成弧度制   
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double difference = radLat1 - radLat2;
        double mdifference = rad(lng1) - rad(lng2);
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(difference / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(mdifference / 2), 2)));
        distance = distance * EARTH_RADIUS;
        distance = Math.round(distance * 10000) / 10000;    //两点之间的距离
         
        return distance;
    }
 
 // 乘客起点（x0,y0） 到由司机起点终点组成的线段（x1,y1） ,( x2,y2 )  x经度 y纬度
    public static double  pointToLine(double x1, double y1, double x2, double y2, double x0,
    	       double y0 ,double x00,double y00) {
    	    double space = 0;
    	    double space1 = 0;
    	    double a, b, c,  b1, c1 ;
    	    a = getDistance(x1, y1, x2, y2);// 线段的长度
    	    b = getDistance(x1, y1, x0, y0);// (x1,y1)到点的距离
    	    c = getDistance(x2, y2, x0, y0);// (x2,y2)到点的距离
    	    b1 = getDistance(x1, y1, x00, y00);// (x1,y1)到点的距离
    	    c1= getDistance(x2, y2, x00, y00);// (x2,y2)到点的距离
    	    
    	    if (c <= 0.000001 || b <= 0.000001) {
    	       space = 0;
    	       return space;
    	    }
    	    if (a <= 0.000001) {
    	       space = b;
    	       return space;
    	    }
    	    if (c * c >= a * a + b * b) {
    	       space = b;
    	       return space;
    	    }
    	    if (b * b >= a * a + c * c) {
    	       space = c;
    	       return space;
    	    }
    	    double p = (a + b + c) / 2;// 半周长
    	    double s = Math.sqrt(p * (p - a) * (p - b) * (p - c));// 海伦公式求面积
    	    space = 2 * s / a;// 返回点到线的距离（利用三角形面积公式求高）
    	   
    	    //乘客终点（x00,y00）
    	    if (c1 <= 0.000001 || b1 <= 0.000001) {
     	       space1 = 0;
     	       return space1;
     	    }
     	    if (a <= 0.000001) {
     	       space1 = b1;
     	       return space1;
     	    }
     	    if (c1 * c1 >= a * a + b1 * b1) {
     	       space1 = b1;
     	       return space1;
     	    }
     	    if (b1 * b1>= a * a + c1 * c1) {
     	       space1 = c1;
     	       return space1;
     	    }
     	    double p1 = (a + b1 + c1) / 2;// 半周长
     	    double s1 = Math.sqrt(p1 * (p1 - a) * (p1 - b1) * (p1 - c1));// 海伦公式求面积
     	    space1 = 2 * s1 / a;// 返回点到线的距离（利用三角形面积公式求高）
    	    
    	    
    	    return (space + space1) / 2;
    	}
   
}