package com.jpa.boilerplate.jpaboilerplate.controller.MainController;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.jpa.boilerplate.jpaboilerplate.entity.Board.BoardCommentEntity;
import com.jpa.boilerplate.jpaboilerplate.entity.Board.BoardEntity;
import com.jpa.boilerplate.jpaboilerplate.repository.Board.BoardCommentRepository;
import com.jpa.boilerplate.jpaboilerplate.repository.Board.BoardRepository;
import com.jpa.boilerplate.jpaboilerplate.utils.BoardCommentNullCheckMessage;
import com.jpa.boilerplate.jpaboilerplate.utils.BoardNullCheckMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/main")
public class MainController {

    @Autowired
    BoardRepository board;
    @Autowired
    BoardCommentRepository boardComment;

    Optional<BoardEntity> boardEntity;
    Optional<BoardCommentEntity> boardCommentEntity;

    /**
     * 게시글 작성 api
     * 
     * @param boardParam
     * @return
     */
    @PostMapping("/boardWrite")
    public ResponseEntity<?> postingBoard(@RequestBody BoardEntity boardParam) {

        BoardNullCheckMessage boardNullCheckMessage = new BoardNullCheckMessage();
        HashMap<String, Object> nullCheckMessage = boardNullCheckMessage.nullCheck(boardParam);

        if (nullCheckMessage.isEmpty()) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format_time2 = format.format(System.currentTimeMillis());
            boardParam.setCreatedTime(format_time2);
            board.save(boardParam);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(nullCheckMessage, HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * 댓글작성 api
     * 
     * @param commentParam
     * @return
     */
    @PostMapping("/boardCommentWrite")
    public ResponseEntity<?> postingBoardComment(@RequestBody BoardCommentEntity commentParam) {

        BoardCommentNullCheckMessage boardcommentNullCheckMessage = new BoardCommentNullCheckMessage();
        HashMap<String, Object> nullCheckMessage = boardcommentNullCheckMessage.nullCheck(commentParam);

        if (nullCheckMessage.isEmpty()) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format_time2 = format.format(System.currentTimeMillis());
            commentParam.setCreatedTime(format_time2);
            boardComment.save(commentParam);

            return new ResponseEntity<>("success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(nullCheckMessage, HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * 게시물 list 를 조회하는 api
     * 
     * @param req
     * @return
     */
    @GetMapping("/select")
    public ResponseEntity<?> selectBoardList(HttpServletRequest req) {
        String pageNum = req.getParameter("pageNum");
        if (pageNum == "" || pageNum == null) {
            pageNum = "0";
        }

        Pageable result = PageRequest.of(Integer.parseInt(pageNum), 10, Sort.by("createdTime").descending());
        return new ResponseEntity<>(board.findBydeleteTimeNull(result), HttpStatus.OK);
    }

    /**
     * 게시글을 삭제하는 api
     * 
     * @param boardNo
     * @return
     */
    @DeleteMapping("/boardDelete/{boardNo}")
    public ResponseEntity<?> deleteBoard(@PathVariable String boardNo) {
        board.deleteById(Integer.parseInt(boardNo));
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    /**
     * 댓글을 삭제하는 api
     * 
     * @param commentNo
     * @return
     */
    @DeleteMapping("/commentDelete/{commentNo}")
    public ResponseEntity<?> deleteComment(@PathVariable String commentNo) {
        boardComment.deleteById(Integer.parseInt(commentNo));
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    /**
     * 게시물을 수정하는 api
     * 
     * @param boardNo
     * @param boardParam
     * @return
     */
    @PutMapping("/boardUpdate/{boardNo}")
    public ResponseEntity<?> boardUpdate(@PathVariable String boardNo, @RequestBody BoardEntity boardParam) {
        boardEntity = board.findById(Integer.parseInt(boardNo));
        boardEntity.ifPresent(item -> {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format_time2 = format.format(System.currentTimeMillis());

            item.setContent(boardParam.getContent());
            item.setTitle(boardParam.getTitle());
            item.setUpdatedTime(format_time2);
            board.save(item);
        });
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    /**
     * 댓글을 수정하는 api
     * 
     * @param boardNo
     * @param boardParam
     * @return
     */
    @PutMapping("/commentUpdate/{commentNo}")
    public ResponseEntity<?> commentUpdate(@PathVariable String commentNo,
            @RequestBody BoardCommentEntity commentParam) {
        boardCommentEntity = boardComment.findById(Integer.parseInt(commentNo));
        boardCommentEntity.ifPresent(item -> {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format_time2 = format.format(System.currentTimeMillis());

            item.setComment(commentParam.getComment());
            item.setName(commentParam.getName());
            item.setUpdatedTime(format_time2);
            boardComment.save(item);
        });
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
