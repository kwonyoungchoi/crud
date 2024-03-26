package com.exam.crud.controller;

import com.exam.crud.dto.BoardDTO;
import com.exam.crud.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
//Controller>Service>Repository
@RequiredArgsConstructor
public class BoardController {
    //서비스를 주입
    private final BoardService boardService;

    //@Autowired는 private 클래스명으로 선언시 자동주입
    //@RequiredAresConstructor는 private final 클래스명으로 선언시 자동주입
    //form 태그가 있는 페이지는 get/post 존재
    //from 태그가 없는 페이지는 get/post 중 선택
    //삽입(C) ~Form(view로 이동), ~Proc(Service로 처리)
    //Service처리한 페이지 redirect로 이동

    @GetMapping("/board/insert")
    public String insertForm(){
        return "board/insert"; //html파일로 이동
    }
    //입력한 내용을 DTO로 받는다.
    //form(input태그 name)과 DTO(변수명) 매치 값을 전달
    //@ModelAttribute 매칭(생략가능)
    @PostMapping("/board/insert")
    public String insertProc(@ModelAttribute BoardDTO boardDTO){
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
    public String updateProc(BoardDTO boardDTO) {
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
    public String listForm(Model model) {

        //서비스 처리(전체조회)
        List<BoardDTO> boardDTOS = boardService.list();

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
