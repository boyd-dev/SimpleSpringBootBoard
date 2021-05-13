package com.example.demo.board.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.board.model.Board;

@Repository
public interface IBoardRepository extends JpaRepository<Board, Long> {
	
	public Optional<List<Board>> findByOrderByUpdDtDesc();
	
	// Spring Data 패키지에서 제공하는 Pageable 인터페이스 타입을 파라미터(패이지 크기, 정렬 등의 정보)로 전달하면 페이징된 결과를 리턴받을 수 있다.
	// Page 인터페이스 타입으로 리턴을 받으면 페이징 설정 정보도 함께 받을 수 있다. 
	public Page<Board> findBy(Pageable pageable);
	
	
	@Query("select b from Board b where b.cnttId = ?1 and b.authorId = ?2")
	public Optional<Board> selectForUpdate(Long cnttId, String authorId);	

}
