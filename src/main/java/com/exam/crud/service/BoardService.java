package com.exam.crud.service;

import com.exam.crud.dto.BoardDTO;
import com.exam.crud.entity.BaseEntity;
import com.exam.crud.entity.BoardEntity;
import com.exam.crud.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.asm.Advice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

//순서 보장을 위한 클래스
@Service
//repository, entity, dto를 주입처리
@RequiredArgsConstructor
//데이터베이스 처리를 모아서 처리(일괄처리)
@Transactional
@Log4j2
public class BoardService {
    //사용할 저장소 지정
    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;


    //view에서 DTO전달->Entity변환 후 데이터베이스에 저장
    public void insert(BoardDTO boardDTO) {


        BoardEntity boardEntity = modelMapper.map(boardDTO, BoardEntity.class);
        //사용할 데이터베이스 질의어로 데이터베이스 구동
        //저장확인가능
        //데이터베이스 처리를 위한 변환 DTO -> Entity (boardDTO boardDTO)
        boardRepository.save(boardEntity);  //질의어
        //결과를 Controller에 전달하기 위해 Entity->DTO (public BoardDTO)




    }



    //수정
    public void update(BoardDTO boardDTO) {
        //수정할 데이터가 존재하는가?
        //수정할 id로 조회
        Optional<BoardEntity> temp = boardRepository.findById(boardDTO.getId());

        if(temp.isPresent()) { //존재하면

            BoardEntity boardEntity = modelMapper.map(boardDTO, BoardEntity.class);
            //사용할 데이터베이스 질의어로 데이터베이스 구동
            //저장확인가능
            boardRepository.save(boardEntity);
        }

    }

    public void delete(Long id) {
        boardRepository.deleteById(id); // 해당 번호 자료 삭제
    }

    //개별조회
    //요청번호 -> 조회해서 -> DTO변환 후 -> View전달
    public BoardDTO read(Long id) {
        //요청
        Optional<BoardEntity> temp = boardRepository.findById(id);
        //변환
        /*BoardDTO boardDTO = modelMapper.map(temp,BoardDTO.class);
        return boardDTO;
        */
        return modelMapper.map(temp, BoardDTO.class);
    }

    //전체조회 List, pageable
    public Page<BoardDTO> list(Pageable page, String type, String search) {
        //페이지정보를 읽기위한 정렬
        int currentPage = page.getPageNumber()-1; // 데이터베이스 페이지번호 변경
        int pageLimit=5; //한화면에 출력할 데이터갯수

        //페이지 처리를 위한 정렬
        //PageRequest.of페이지요청(찾을페이지, 가져올 개수, 정렬(정렬형식Desc(내림차순),ASC(오름차순)
        Pageable pageable = PageRequest.of(currentPage, pageLimit, Sort.by(Sort.Direction.DESC, "id"));

        Page<BoardEntity> boardEntities;

        //검색을 추가
        // 문자비교는 변수.equals("문자")
        if(type.equals("s") && search != null) { //제목을 선택하고 검색어가 있으면
            boardEntities = boardRepository.findByTitleContaining(search, pageable);
        }else if (type.equals("c") && search != null) { // 내용을 선택하고 검색어가 있으면
            boardEntities = boardRepository.findByContentContaining(search, pageable);
        }else if (type.equals("w") && search != null) { // 작성자를 선택하고 검색어가 있으면
            boardEntities = boardRepository.findByWriterContaining(search, pageable);
        }else if (type.equals("sc") && search != null) { // 제목+내용을 선택하고 검색어가 있으면
            boardEntities = boardRepository.findByTitleContainingOrContentContaining(search, pageable);
        }else if (type.equals("scw") && search != null) { // 제목+내용+작성자를 선택하고 검색어가 있으면
            boardEntities = boardRepository.findByTitleContainingOrContentContainingOrWriterContaining(search, pageable);
        }else {
            // 전체조회
            boardEntities = boardRepository.findAll(pageable);

        }
        // 변환
        //Arrays.asList : List의 내용을 개별로 읽어서 변환 후 배열로 저장

        //ForEach(data:entity)...
        //entity(entity,page) ->data(임시변수)->data값을 modelMapper로 변환
        Page<BoardDTO> boardDTOS = boardEntities.map(data->modelMapper.map(data, BoardDTO.class));
        return boardDTOS;
    }
}
