package com.example.demo.board.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.board.model.Board;
import com.example.demo.board.model.BoardDto;
import com.example.demo.board.model.SearchDto;
import com.example.demo.board.model.UploadResponse;
import com.example.demo.board.service.IBoardService;
import com.example.demo.utils.FileUtils;

// 프론트엔드와 동일한 호스트이므로 cors 설정은 필요없다.
//@CrossOrigin
@RestController
public class BoardController {
	
	private static final Logger logger = LogManager.getLogger(BoardController.class);
	
	@Autowired
	private IBoardService service;
	
	@Autowired
	private FileUtils fileUtils;
	
	@PostMapping(value="/api/board/list")
    public ResponseEntity<Map<String, List<Board>>> list(HttpServletResponse resp, Principal principal) throws Exception {
		
		Map<String, List<Board>> list = new HashMap<>();
				
		if (!principal.getName().isEmpty()) {
			
			if (logger.isDebugEnabled()) {
				logger.debug(principal.getName());
			}
		
			list = service.selectBoardList();
		}
				
		return ResponseEntity.ok(list);
    }
	
	@PostMapping(value="/api/board/listPage")
    public ResponseEntity<Map<String, Object>> listPage(@RequestBody final SearchDto searchDto, HttpServletResponse resp, Principal principal) throws Exception {
		
		Map<String, Object> list = new HashMap<>();
				
		if (!principal.getName().isEmpty()) {
			
			if (logger.isDebugEnabled()) {
				logger.debug(principal.getName());
			}
		
			int pageNo = searchDto.getPageNo();
			int pageSize = searchDto.getPageSize();
			String sortBy = "insDt";
			
			list = service.selectBoardPageList(pageNo, pageSize, sortBy);
		}
				
		return ResponseEntity.ok(list);
    }
	
	@PostMapping(value="/api/board/save")
    public ResponseEntity<Board> save(@RequestBody final BoardDto boardDto, HttpServletResponse resp, Principal principal) throws Exception {
		
		Board result = null;
		
		if (!principal.getName().isEmpty()) {
			
			if (logger.isDebugEnabled()) {
				logger.debug(principal.getName());
			}
		
			Board board = new Board();
			board.setBoardId(boardDto.getBoardId());
			board.setCnttText(boardDto.getCnttText());
			board.setCnttTitle(boardDto.getCnttTitle());
			board.setUserName(boardDto.getUserName());
			board.setAuthorId(boardDto.getAuthorId());
			
			Optional<Board> v = service.save(board);			
			result = v.get();
		}
				
		return ResponseEntity.ok(result);
    }
	
	
	// ckeditor5 SimpleUploadAdapter 를 사용하면 POST 메소드를 통해 "upload" 라는 이름으로 파일이 전송된다. 
	@PostMapping(value="/api/board/imageupload")
    public ResponseEntity<UploadResponse> upload(@RequestParam("upload") MultipartFile file, HttpServletResponse resp, Principal principal) throws Exception {
			
		UploadResponse result = new UploadResponse();
	
		if (!principal.getName().isEmpty()) {
			
			String imgUrl = fileUtils.parseCkEditorImgPath(file);
			result.setUrl(imgUrl);
			
		} else {
			Map<String, String> m = new HashMap<>();
			m.put("message", "NO PRINCIPAL");
			result.setError(m);
		}
		
		return ResponseEntity.ok(result);
    }

	
	@PostMapping(value="/api/board/updateReadCount")
    public ResponseEntity<?> updateReadCount(@RequestBody final BoardDto boardDto, HttpServletResponse resp, Principal principal) throws Exception {
				
		if (!principal.getName().isEmpty()) {
			
			if (logger.isDebugEnabled()) {
				logger.debug(principal.getName());
			}			
			service.updateReadCount(boardDto.getCnttId());		
		}
				
		return ResponseEntity.ok(null);
    }
	
	@PostMapping(value="/api/board/delete")
    public ResponseEntity<?> delete(@RequestBody final BoardDto boardDto, HttpServletResponse resp, Principal principal) throws Exception {
		
		if (!principal.getName().isEmpty()) {
			
			if (logger.isDebugEnabled()) {
				logger.debug(principal.getName());
			}
		
			OAuth2User user = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getDetails();					
			service.delete(boardDto.getCnttId(), user.getAttribute("sub").toString());
		}
				
		return ResponseEntity.ok(null);
    }
	
	@PostMapping(value="/api/board/update")
    public ResponseEntity<?> update(@RequestBody final BoardDto boardDto, HttpServletResponse resp, Principal principal) throws Exception {
		
		if (!principal.getName().isEmpty()) {
			
			if (logger.isDebugEnabled()) {
				logger.debug(principal.getName());
			}
		
			OAuth2User user = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getDetails();					
			service.update(boardDto, user.getAttribute("sub").toString());
		}
				
		return ResponseEntity.ok(null);
    }
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> exception(Exception e) {		
		return new ResponseEntity<String>("BoardController Failed!", HttpStatus.OK);
	}

}
