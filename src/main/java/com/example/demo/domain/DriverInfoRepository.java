package com.example.demo.domain;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;




public interface DriverInfoRepository extends JpaRepository<DriverInfo,Integer>{
	DriverInfo findDriverInfoById(int id);
	List<DriverInfo> findDriverInfoBySeats(int seats);
	
	@Query(value = "select * from passenger_order limit ?1", nativeQuery =true)
	

	List<DriverInfo> findAllDriversByCount(int count);
	
//	@Query("select p from Product p join p.user u  where  u.id=?1 ")
//    List<DriverInfo> findByUserId(@Param("userId") int userId);
	

	
	 
}


