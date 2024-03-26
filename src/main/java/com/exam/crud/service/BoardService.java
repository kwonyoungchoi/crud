package com.exam.crud.service;

import com.exam.crud.dto.BoardDTO;
import com.exam.crud.entity.BoardEntity;
import com.exam.crud.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

//순서 보장을 위한 클래스
@Service
//repository, entity, dto를 주입처리
@RequiredArgsConstructor
//데이터베이스 처리를 모아서 처리(일괄처리)
@Transactional
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
    public List<BoardDTO> list() {
        // 전체조회
        List<BoardEntity> boardEntities = boardRepository.findAll();
        // 변환
        //Arrays.asList : List의 내용을 개별로 읽어서 변환 후 배열로 저장
        List<BoardDTO> boardDTOS = Arrays.asList(modelMapper.map(boardEntities,
                BoardDTO[].class));
        return boardDTOS;
    }
}
