package com.example.springcore.controller;

import com.example.springcore.domain.User;
import com.example.springcore.dto.ProductMypriceRequestDto;
import com.example.springcore.dto.ProductRequestDto;
import com.example.springcore.security.UserDetailsImpl;
import com.example.springcore.service.FolderService;
import com.example.springcore.service.ProductService;
import com.example.springcore.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController // JSON으로 데이터를 주고받음을 선언합니다.
public class ProductController {
    // 멤버 변수 선언
    private final ProductService productService;
    private final FolderService folderService;

    // 생성자: ProductController() 가 생성될 때 호출됨
    @Autowired
    public ProductController(ProductService productService, FolderService folderService) {
        // 멤버 변수 생성
        this.productService = productService;
        this.folderService = folderService;
    }

    // 로그인한 회원이 등록한 상품들 조회
    @GetMapping("/api/products")
    public Page<Product> getProducts(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Long userId = userDetails.getUser().getId();
        page = page - 1;
        return productService.getProducts(userId, page , size, sortBy, isAsc);
    }

    // 신규 상품 등록
    @PostMapping("/api/products")
    public Product createProduct(@RequestBody ProductRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws SQLException {
        // 로그인 되어 있는 ID
        Long userId = userDetails.getUser().getId();
        Product product = productService.createProduct(requestDto, userId);
        // 응답 보내기
        return product;
    }

    // 설정 가격 변경
    @PutMapping("/api/products/{id}")
    public Long updateProduct(@PathVariable Long id, @RequestBody ProductMypriceRequestDto requestDto) throws SQLException {
        Product product = productService.updateProduct(id, requestDto);
        return product.getId();
    }

    // (관리자용) 등록된 모든 상품 목록 조회
    @Secured("ROLE_ADMIN")  //admin 페이지 접근하는 유저 정보에 Role admin이 있는지 체크
    @GetMapping("/api/admin/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // 상품에 폴더 추가
    @PostMapping("/api/products/{id}/folder")
    public Long addFolder(@PathVariable Long id,
                          @RequestParam("folderId") Long folderId,
                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        Product product = productService.addFolder(id, folderId, user);
        // 응답 보내기
        return product.getId();
    }

    // 회원이 등록한 폴더 내 모든 상품 조회
    @GetMapping("/api/folders/{folderId}/products")
    public Page<Product> getProductsOnFolder(@PathVariable("folderId") Long folderId,
                                             @RequestParam("page") int page,
                                             @RequestParam("size") int size,
                                             @RequestParam("sortBy") String sortBy,
                                             @RequestParam("isAsc") boolean isAsc,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        page = page - 1;
        return folderService.getProductsOnFolder(userDetails.getUser(), page, size, sortBy, isAsc, folderId);
    }
}