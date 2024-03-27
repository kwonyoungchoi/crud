package com.exam.crud.controller;

import com.exam.crud.dto.BoardDTO;
import com.exam.crud.service.BoardService;
import com.exam.crud.service.PageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


//제어를 위한 클래스
@Controller
//Controller>Service>Repository
@RequiredArgsConstructor
//콘솔에 로그형식 내용을 출력
@Log4j2
public class BoardController {
    //서비스를 주입
    private final BoardService boardService;
    
    

    //@Autowired는 private 클래스명으로 선언시 자동주입
    //@RequiredAresConstructor는 private final 클래스명으로 선언시 자동주입
    //form 태그가 있는 페이지는 get/post 존재
    //from 태그가 없는 페이지는 get/post 중 선택
    //삽입(C) ~Form(view로 이동), ~Proc(Service로 처리)
    //Service처리한 페이지 redirect로 이동


    //Model은 Controller->View에 값을 전달
    //주석 사용법
    //TODO : 해야할 작업을 기록
    //FixMe : 해결하지 못한 문제점 기록
    
    //TODO : 1. 검증
    //TODO : 2. 페이지
    //TODO : 3. 검색
    //FixMe : 검증처리가 안됨
    
    @GetMapping("/board/insert")
    public String insertForm(Model model){
        log.info("일반적으로 처리할 메세지");
        log.warn("경고나 주의 메세지");
        log.error("문제발생 메세지");

        //"boardDTO" --> view ${boardDTO}
        model.addAttribute("boardDTO", new BoardDTO());

        return "board/insert"; //html파일로 이동
    }
    //입력한 내용을 DTO로 받는다.
    //form(input태그 name)과 DTO(변수명) 매치 값을 전달
    //@ModelAttribute 매칭(생략가능)
    @PostMapping("/board/insert")
    //1. @Validated 대상 : 값이 들어오면 DTO를 이용해서 검증
    //2. BindingResult : DTO에서 검증한 결과를 전달
    public String insertProc(@ModelAttribute @Validated BoardDTO boardDTO,
                             BindingResult bindingResult){
        //서비스 처리 전(데이터베이스 처리 오류를 방지하는게 목적)
        if(bindingResult.hasErrors()) {
            // 검증 결과에서 오류가 발생했을면
            return "board/insert"; //입력한 폼으로 다시 이동
        }

        //서비스 처리
        boardService.insert(boardDTO);
        return "redirect:/board/list"; // 이동할 맵핑명
    }

    
    //수정(U)
    //Path(맵핑에)Variale(변수)로 전달받은 값을 Long id에 저장
    @GetMapping("/board/update/{id}")
    //Model은 데이터베이스에서 받은 내용을 html에 전달할게 있으면 선언
    public String updateForm(@PathVariable Long id, Model model){ //(받고, 보낼변수)
        // 개별조회 후 수정폼에 출력(조회한 내용을 DTO에 저장)
        BoardDTO boardDTO = boardService.read(id);
        //HTML에 값을 전달
        model.addAttribute("boardDTO", boardDTO); //"변수명", 보낼값
        //                              HTML 변수    보낼 값
        // 서비스 처리(조회)
        return "board/update";
    }
    
    // 수정할 내용을 boardDTO가 받아서 데이터베이스에 수정처리
    @PostMapping("/board/update")
    public String updateProc(@Validated BoardDTO boardDTO,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()){
            return "/board/update";
        }
        // 서비스 처리(수정)
        boardService.update(boardDTO);
        return "redirect:/board/list";
    }


    //삭제(D)
    @GetMapping("/board/delete/{id}")
    public String deleteProc(@PathVariable Long id) {
        //서비스 처리(삭제)
        boardService.delete(id);
        return "redirect:/board/list";
    }
    
    
    //전체조회(R)
    @GetMapping("/board/list")
    //@PageableDefault = View에서 페이지 정보가 전달되지 않으면 기본 1페이지로 사용
    //검색어 추가(type=대상, seacrch=검색어)
    //@RequestParam은 ? 뒤에 변수로 보낸 값을 처리(value="보낸변수", defaultValue="변수가 없을 때 대체")
    public String listForm(@PageableDefault(page=1) Pageable pageable,
                           @RequestParam(value="type", defaultValue="")String type,
                           @RequestParam(value="search", defaultValue="")String search, Model model) {


        Page<BoardDTO> boardDTOS = boardService.list(pageable, type, search);

        //추가로 페이지정보도 view에 전달(하단에 출력할 정보를 가공)
        Map<String, Integer> pageinfo = PageService.pagination(boardDTOS);
        //addAllAttributes = 여러개의 변수를 한번에 전달할 때
        model.addAllAttributes(pageinfo);
        //서비스 처리(전체조회)
        model.addAttribute("list", boardDTOS);
        return "/board/list";
    }
    
    //상세조회(R)
    @GetMapping("/board/{id}")
    public String readForm(@PathVariable Long id, Model model) {
        BoardDTO boardDTO = boardService.read(id);
        //서비스에서 값을 반드면 반드시 model로 전달
        model.addAttribute("boardDTO", boardDTO);
        return "/board/read";
    }

    /*Model(변수)AndView(페이지)
    public ModelAndView readForm() {
    }
    public String(View) readForm(Model) {
    }*/
} //요청처리
