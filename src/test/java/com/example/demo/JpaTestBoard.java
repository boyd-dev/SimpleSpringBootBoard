package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import com.example.demo.board.dao.IBoardRepository;
import com.example.demo.board.model.Board;
import com.example.demo.board.model.File;


@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JpaTestBoard {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private IBoardRepository boardRepository;
		
	@Disabled
	@Test
	@Order(1)	
	@Rollback(false)
	void testInsert() throws Exception {
		
		Board board = new Board();
		
		board.setBoardId("B01");
		board.setAuthorId("me@foo.com");
		board.setCnttTitle("테스트 게시물 제목");
		board.setCnttText("차기 검찰총장 후보군에 김오수 전 법무부 차관과 구본선 광주고검장, 배성범 법무연수원장, 조남관 대검찰청 차장검사가 올랐다. 유력 후보로 꼽혔던 이성윤 서울중앙지검장은 후보군에 들지 않았다.");

		
		List<File> files = new ArrayList<>();
		
		File f = new File();
		f.setFileExt("txt");
		f.setFilePath("/files");
		f.setFileSize(1024*30);
		f.setOriginalFileName("myfile-1");
		f.setStoredFileName("20210507001");		
		files.add(f);
		
		f = new File();
		f.setFileExt("txt");
		f.setFilePath("/files");
		f.setFileSize(1024*100);
		f.setOriginalFileName("myfile-2");
		f.setStoredFileName("20210507002");		
		files.add(f);
		
		
		board.setFiles(files);
		
		Board result = boardRepository.save(board);
		
		assertThat(result.getBoardId()).isNotNull();		
	    
	}
	
	@Disabled
	@Test
	@Rollback(false)
	void testUpdate() throws Exception {
		
		Board board = this.em.find(Board.class, 1L);
		
		if (board != null) {
			board.setCnttHit(10);
			this.em.merge(board);
		    
			Board result = this.em.find(Board.class, 1L);	
			assertThat(result.getCnttHit()).isEqualTo(10);
		}
	}
	
	@Disabled
	@Test
	@Order(2)	
	@Rollback(false)
	void testSelect() throws Exception {		
		
		Optional<Board> result = boardRepository.findById(1L);		
		assertThat(result.isEmpty()).isFalse();	    
	}
	
	@Disabled
	@Test
	@Order(3)	
	@Rollback(false)
	void testSelectAll() throws Exception {		
		
		List<Board> result = boardRepository.findAll();
		assertThat(result.size()).isEqualTo(1);		
	}
	
	@Disabled
	@Test
	@Order(4)	
	@Rollback(false)
	void testDelete() throws Exception {		
		
		Board board = this.em.find(Board.class, 1L);
		if (board != null) {
			boardRepository.delete(board);
		}
		
		List<Board> result = boardRepository.findAll();
		assertThat(result.size()).isEqualTo(0);	
	}
	
	
	@Disabled
	@Test
	@Rollback(false)
	void testSelect1() throws Exception {
			
		Optional<List<Board>> result = boardRepository.findByOrderByUpdDtDesc();
		
		assertThat(result.get().size()).isEqualTo(13); // totalCount
	}
	
	@Disabled
	@Test
	@Rollback(false)
	void testSelectPage() throws Exception {
		
		int pageNo = 1;
		int pageSize = 10;
		
		PageRequest preq = PageRequest.of(pageNo-1, pageSize, Sort.by("updDt").descending());		
		Page<Board> result = boardRepository.findBy(preq);

		assertThat(result.getTotalPages()).isEqualTo(2);
	}
	
	@Disabled
	@Test
	void testSelectByIdAuthorId() throws Exception {
		
		Long cnttId = Long.valueOf("5");
		String authorId = "12345";
		
		Optional<Board> result = boardRepository.selectForUpdate(cnttId, authorId);

		assertThat(result.get()).isNotNull();
		assertThat(result.get().getAuthorId()).isEqualTo(authorId);
	}
	

}
