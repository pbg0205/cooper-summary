# Forward와 Redirect 차이

## 1. Forward

- 이동할 URL로 요청 정보를 그대로 전달한다. 그러므로 사용자가 요청한 정보는 다음 URL에서도 유효하다.
- 한마디로 요청과 응답을 돌리는 것이다.

<br>

## 2. Redirect

- 웹 브라우저에게 다른 페이지로 이동하라고 명령을 내려 다른 URL 주소로 이동하는 과정
- 다른 주소로 이동하면 새로운 페이지에서는 Request와 Response 객체가 새롭게 생성된다.

<br>

## 3. Sprring에서 사용하느 Redirect 예시
```java
package com.mang.board.controller;

import com.mang.board.vo.BoardVO;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Resource(name="boardService")
	private BoardService boardService;

	@PostMapping(value="/insertBoardInfo")
	public String insertBoardInfo(HttpServletRequest request, @ModelAttribute BoardVO boardVO) throws Exception{
		int rsCnt = boardService.insertBoardInfo(request, boardVO);
		if(rsCnt < 1) {
			return "/cmmn/error";
		}
		return "redirect:/board/retrieveBoardList";			
	}
    
출처: https://mangkyu.tistory.com/51 [MangKyu's Diary:티스토리]
```