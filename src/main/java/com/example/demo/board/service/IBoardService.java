package com.example.demo.board.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.demo.board.model.Board;
import com.example.demo.board.model.BoardDto;

public interface IBoardService {
	
	//조회
	public Map<String, List<Board>> selectBoardList() throws Exception;
	
	//페이징된 조회
	public Map<String, Object> selectBoardPageList(int pageNo, int pageSize, String sortBy) throws Exception;
	
	//저장
	public Optional<Board> save(Board board) throws Exception;
	
	//삭제
	public int delete(Long cnttId, String authorId) throws Exception;
	
	//수정
	public int update(BoardDto boardDto, String authorId) throws Exception;
	
	//단건 조회
	public Optional<Board> findById(Long id) throws Exception;	
	
	//조회수 업데이트
	public int updateReadCount(Long id) throws Exception;
	
	
}
