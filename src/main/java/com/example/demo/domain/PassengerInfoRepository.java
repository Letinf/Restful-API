package com.example.demo.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



public interface PassengerInfoRepository extends JpaRepository<PassengerInfo, Integer>{
	PassengerInfo findPassengerInfoById(int id);
	List<PassengerInfo> findPassengerInfoBySeats(int seats);
	
	@Query(value = "select * from passenger_order limit ?1", nativeQuery =true)
	
//	@Query(value="SELECT 1 * FROM  passenger_order BY anyField DESC LIMIT 1", nativeQuery = true)
//	List<PassengerInfo> selectOne();
	
	List<PassengerInfo> findAllPassengersByCount(int count);
}
