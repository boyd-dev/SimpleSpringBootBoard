package com.example.demo.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.board.dao.IBoardRepository;
import com.example.demo.board.model.Board;
import com.example.demo.board.model.BoardDto;

@Service
public class BoardServiceImpl implements IBoardService {

	
	@Autowired
	private IBoardRepository repository;
	
	@Override
	public Map<String, List<Board>> selectBoardList() throws Exception {
		
		Map<String, List<Board>> result = new HashMap<>();
		Optional<List<Board>> list = repository.findByOrderByUpdDtDesc();
		
		if (!list.isEmpty()) {
			result.put("list", list.get());
		} else {
			result.put("list", null);
		}
				
		return result;		
	}

	@Override
	public Map<String, Object> selectBoardPageList(int pageNo, int pageSize, String sortBy) throws Exception {
		
		Map<String, Object> result = new HashMap<>();
		
		// 페이지 번호는 0부터 시작하므로 -1을 한다.
		Pageable pageable = PageRequest.of(pageNo-1, pageSize, Sort.by(sortBy).descending());
		Page<Board> list = repository.findBy(pageable);
		
		result.put("list", list.getContent());
		result.put("totalCount", list.getTotalElements());
		
		return result;
	}

	@Override
	@Transactional
	public Optional<Board> save(Board board) throws Exception {
		
		Board v = repository.save(board);		
		Optional<Board> result = Optional.of(v);
		return result;
	}

	@Override
	public Optional<Board> findById(Long id) throws Exception {
		
		Optional<Board> v = repository.findById(id);			
		return v;
	}

	@Override
	public int updateReadCount(Long id) throws Exception {
		
		Optional<Board> v = repository.findById(id);
		
		int count = 0;
		if (v.isPresent()) {
			v.get().setCnttHit(v.get().getCnttHit()+1);
			repository.save(v.get());
			count++;
		}
		
		return count;		
	}

	@Override
	public int delete(Long cnttId, String authorId) throws Exception {
		
        //Optional<Board> v = repository.findById(cnttId);
		// 사용자와 작성자가 같은 경우만 삭제 가능
		Optional<Board> v = repository.selectForUpdate(cnttId, authorId);
		
		int count = 0;
		if (v.isPresent()) {
			repository.delete(v.get());
			count++;
		}
		
		return count;		
	}

	@Override
	public int update(BoardDto boardDto, String authorId) throws Exception {
		
		Long cnttId = boardDto.getCnttId();
		
		// 사용자와 작성자가 같은 경우만 수정 가능
		Optional<Board> v = repository.selectForUpdate(cnttId, authorId);
		
		int count = 0;
		if (v.isPresent()) {			
			v.get().setCnttTitle(boardDto.getCnttTitle());
			v.get().setCnttText(boardDto.getCnttText());			
			repository.save(v.get());
			count++;
		}
		
		return count;		
	}
	

}
