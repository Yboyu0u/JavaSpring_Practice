package com.example.demo.service;

import com.example.demo.ifs.CrudInterface;
import com.example.demo.model.entity.Item;
import com.example.demo.model.entity.User;
import com.example.demo.model.network.Header;
import com.example.demo.model.network.Pagination;
import com.example.demo.model.network.request.ItemApiRequest;
import com.example.demo.model.network.response.ItemApiResponse;
import com.example.demo.model.network.response.UserApiResponse;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.PartnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemApiLogicService extends BaseService<ItemApiRequest,ItemApiResponse,Item> {

    @Autowired
    private PartnerRepository partnerRepository;

    @Override
    public Header<ItemApiResponse> create(Header<ItemApiRequest> request) {

        ItemApiRequest body = request.getData();

        //item entity 생성
        Item item = Item.builder()
                    .status(body.getStatus())
                    .name(body.getName())
                    .title(body.getTitle())
                    .content(body.getContent())
                    .price(body.getPrice())
                    .brandName(body.getBrandName())
                    .registeredAt(LocalDateTime.now())
                    .partner(partnerRepository.getOne(body.getPartnerId()))
                    .build();
        Item newItem = baseRepository.save(item);
        return Header.OK(response(newItem));
    }

    @Override
    public Header<ItemApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(item -> response(item))
                .map(ItemApiResponse -> Header.OK(ItemApiResponse))
                .orElseGet(()-> Header.Error("데이터 없음"));

    }

    @Override
    public Header<ItemApiResponse> update(Header<ItemApiRequest> request) {

                ItemApiRequest body = request.getData();

               return baseRepository.findById(body.getId())
                        .map(entityItem -> {
                            entityItem
                                    .setStatus(body.getStatus())
                                    .setName(body.getName())
                                    .setTitle(body.getTitle())
                                    .setContent(body.getContent())
                                    .setPrice(body.getPrice())
                                    .setBrandName(body.getBrandName())
                                    .setRegisteredAt(body.getRegisteredAt())
                                    .setUnregisteredAt(body.getUnregisteredAt())
                                    ;
                            return entityItem;
                        })
                        .map(newEntityItem -> baseRepository.save(newEntityItem))
                        .map(item -> response(item))
                        .map(Header::OK)
                        .orElseGet(()-> Header.Error("데이터 없음"));

    }

    @Override
    public Header delete(Long id) {

        return baseRepository.findById(id)
                .map(item -> {
                    baseRepository.delete(item);
                    return Header.OK();
                })
                .orElseGet(()->Header.Error("데이터 없음"));
    }

    @Override
    public Header<List<ItemApiResponse>> search(Pageable pageable) {
        Page<Item> items = baseRepository.findAll(pageable);

        List<ItemApiResponse> ItemApiResponseList =items.stream()
                .map(item -> response(item))
                .collect(Collectors.toList());
        //List<UserApiResponse>
        //Header<List<UserApiResponse>>

        Pagination pagination  = Pagination.builder()
                .totalPages(items.getTotalPages())
                .totalElements(items.getTotalElements())
                .currentPage(items.getNumber())
                .currentElements(items.getNumberOfElements())
                .build();
        return Header.OK(ItemApiResponseList);
    }

    public ItemApiResponse response(Item item){

        ItemApiResponse body = ItemApiResponse.builder()
                .id(item.getId())
                .status(item.getStatus())
                .name(item.getName())
                .title(item.getTitle())
                .content(item.getContent())
                .price(item.getPrice())
                .brandName(item.getBrandName())
                .registeredAt(item.getRegisteredAt())
                .unregisteredAt(item.getUnregisteredAt())
                .partnerId(item.getPartner().getId())
                .build();

        return body;
    }
}
