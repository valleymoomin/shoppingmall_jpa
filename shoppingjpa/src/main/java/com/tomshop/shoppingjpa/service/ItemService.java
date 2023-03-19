package com.tomshop.shoppingjpa.service;

import com.tomshop.shoppingjpa.dto.ItemFormDto;
import com.tomshop.shoppingjpa.dto.ItemImgDto;
import com.tomshop.shoppingjpa.dto.ItemSearchDto;
import com.tomshop.shoppingjpa.dto.MainItemDto;
import com.tomshop.shoppingjpa.entity.Item;
import com.tomshop.shoppingjpa.entity.ItemImg;
import com.tomshop.shoppingjpa.repository.ItemImgRepository;
import com.tomshop.shoppingjpa.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ItemService {


    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;

    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {

        //상품 등록
        Item item = itemFormDto.createItem();
        itemRepository.save(item);

        //이미지 등록
        for (int i = 0; i < itemImgFileList.size(); i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);
            if (i == 0) {
                itemImg.setRepimgYn("Y");
            } else {
                itemImg.setRepimgYn("N");

            }
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));

        }
        return item.getId();
    }

    //등록된 상품을 불러오는 메소드
    @Transactional(readOnly = true) //읽기전용 -> 더티체킹 수행안해서 성능향상..
    public ItemFormDto getItemDtl(Long itemId) {
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        //해당상품의 이미지를 조홰ㅣ한다. 등록순으로 가지고온다. 상품이미지아이디 오름차순
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();
        for (ItemImg itemImg : itemImgList) {
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }

        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        ItemFormDto itemFormDto = ItemFormDto.of(item);
        itemFormDto.setItemImgDtoList(itemImgDtoList);
        return itemFormDto;
    }


    //상품 업데이트 - 변경감지기능사용
    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {
        //상품 수정
        Item item = itemRepository.findById(itemFormDto.getId()).orElseThrow(EntityNotFoundException::new);
        item.updateItem(itemFormDto);


        //상품 이미지 아이디 리스트를 조회
        List<Long> itemImgIds = itemFormDto.getItemImgIds();

        //이미지 등록
        for (int i = 0; i < itemImgFileList.size(); i++) {
            itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
        } //상품 이미지 아이디, 상품이미지 파일정보를 파라미터로 전달

        return item.getId();
    }

    @Transactional(readOnly = true) //데이터수정X -> 최적화
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getAdminItemPage(itemSearchDto, pageable);
    }


    @Transactional(readOnly = true)
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getMainItemPage(itemSearchDto, pageable);
    }
}
