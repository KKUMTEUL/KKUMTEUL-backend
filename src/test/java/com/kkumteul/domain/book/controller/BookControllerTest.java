package com.kkumteul.domain.book.controller;

import com.kkumteul.domain.book.controller.BookController;
import com.kkumteul.domain.book.dto.GetBookListResponseDto;
import com.kkumteul.domain.book.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @BeforeEach
    void setUp() {
        GetBookListResponseDto bookDto = new GetBookListResponseDto();
        GetBookListResponseDto[] bookArray = new GetBookListResponseDto[]{bookDto};
        Page<GetBookListResponseDto> bookPage = new PageImpl<>(Arrays.asList(bookArray));

        Mockito.when(bookService.getBookList(any(Pageable.class))).thenReturn(bookPage);
    }

    @Test
    @DisplayName("전체 도서 반환: 요청 시 책 목록 Page 당 12개씩 반환한다.")
    void testGetBookList() throws Exception {
        mockMvc.perform(get("/api/books?page=0&size=12")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.content").isArray())
                .andExpect(jsonPath("$.response.content.length()").value(1)); // 반환되는 책의 개수
    }
}
