package com.tobipeter.giftdrop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tobipeter.giftdrop.BaseIntegrationTest;
import com.tobipeter.giftdrop.dtos.request.wish.CreateWish;
import com.tobipeter.giftdrop.dtos.request.wish.CreateWishList;
import com.tobipeter.giftdrop.dtos.request.wish.UpdateWish;
import com.tobipeter.giftdrop.dtos.request.wish.UpdateWishList;
import com.tobipeter.giftdrop.enums.Category;
import com.tobipeter.giftdrop.exceptions.DuplicateEntryException;
import com.tobipeter.giftdrop.exceptions.NotFoundException;
import com.tobipeter.giftdrop.services.WishMgtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class WishControllerTest extends BaseIntegrationTest {
    @MockBean
    private WishMgtService wishMgtService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @BeforeEach
    public void setUp(){
        super.setUp();
    }

    @Test
    public void should_create_wish_SUCCESS() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/wishes")
                .content(objectMapper.writeValueAsString(toCreateWishListDto()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void should_create_wish_FAILURE() throws Exception {
        doThrow(new DuplicateEntryException("Wish already exists"))
                .when(wishMgtService).createWishList(toCreateWishListDto());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/wishes")
                .content(objectMapper.writeValueAsString(toCreateWishListDto()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    public void should_get_all_wishes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/wishes")
                .param("page", "0")
                .param("size", "10"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void should_get_a_users_wish_list() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/wishes/123"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void should_share_a_users_wish_list() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/wishes/share/123"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void should_update_wishes_SUCCESS() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users/wishes")
                .content(objectMapper.writeValueAsString(toUpdateWishListDto()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void should_update_wishes_FAILURE() throws Exception {
        doThrow(new NotFoundException("Wish not found"))
                .when(wishMgtService).updateWishItems(toUpdateWishListDto());
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users/wishes")
                .content(objectMapper.writeValueAsString(toUpdateWishListDto()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private CreateWishList toCreateWishListDto(){
        CreateWishList request = new CreateWishList();
        List<CreateWish> wishRequests = new ArrayList<>();

        CreateWish wishDto = new CreateWish();
        wishDto.setName("WISH");
        wishDto.setCategory(Category.APPLIANCES.name());
        wishDto.setPrice(1L);
        wishDto.setLink("link");
        wishDto.setUserWishingId("123");

        wishRequests.add(wishDto);
        request.setWishes(wishRequests);

        return request;
    }

    private UpdateWishList toUpdateWishListDto(){
        UpdateWishList request = new UpdateWishList();
        List<UpdateWish> wishRequests = new ArrayList<>();

        UpdateWish dto = new UpdateWish();
        dto.setCode("CODE");
        dto.setStatus("READY");

        wishRequests.add(dto);
        request.setWishes(wishRequests);

        return request;
    }
}
