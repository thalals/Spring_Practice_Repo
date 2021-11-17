package com.example.springcore.service;

import com.example.springcore.domain.Folder;
import com.example.springcore.domain.User;
import com.example.springcore.dto.ProductMypriceRequestDto;
import com.example.springcore.dto.ProductRequestDto;
import com.example.springcore.domain.Product;
import com.example.springcore.repository.FolderRepository;
import com.example.springcore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;


@Service
public class ProductService {
    // 멤버 변수 선언
    private final ProductRepository productRepository;
    private final FolderRepository folderRepository;
    private static final int MIN_PRICE = 100;

    public Page<Product> getProducts(Long userId, int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        return productRepository.findAllByUserId(userId, pageable);
    }

    // 생성자: ProductService() 가 생성될 때 호출됨
    @Autowired
    public ProductService(ProductRepository productRepository, FolderRepository folderRepository) {
        // 멤버 변수 생성
        this.productRepository = productRepository;
        this.folderRepository = folderRepository;
    }
    // 회원 ID 로 등록된 모든 상품 조회
//    public List<Product> getProducts(Long userId) {
//        return productRepository.findAllByUserId(userId);
//    }

    public List<Product> getProducts() throws SQLException {
        // 멤버 변수 사용
        return productRepository.findAll();
    }
    // 모든 상품 조회 (관리자용)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(ProductRequestDto requestDto, Long userId) throws SQLException {
        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Product product = new Product(requestDto, userId);
        productRepository.save(product);
        return product;
    }

    public Product updateProduct(Long id, ProductMypriceRequestDto requestDto) throws SQLException {
        Product product = productRepository.findById(id).orElseThrow(
                ()->new NullPointerException("해당 아이디가 존재하지 않습니다,")
        );

        int myPrice = requestDto.getMyprice();
        product.updateMyPrice(myPrice);
        return product;
    }

    @Transactional
    public Product addFolder(Long productId, Long folderId, User user) {
        // 1) 상품를 조회합니다.
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new NullPointerException("해당 관심상품 아이디가 존재하지 않습니다.")
        );

        // 2) 관심상품을 조회합니다.
        Folder folder = folderRepository.findById(folderId).orElseThrow(
                () -> new NullPointerException("해당 폴더 아이디가 존재하지 않습니다.")
        );

        // 3) 조회한 폴더와 관심상품이 모두 로그인한 회원의 소유인지 확인합니다.
        Long userId = user.getId();
        Long productUserId = product.getUserId();
        Long folderUserId = folder.getUser().getId();

        if (!userId.equals(productUserId) || !userId.equals(folderUserId)) {
            throw new IllegalArgumentException("회원님의 관심상품이 아니거나, 폴더가 아니어서 추가하지 못했습니다.");
        }

        // 4) 상품에 폴더를 추가합니다.
        product.addFolder(folder);
        return product;
    }

    // 회원 ID 가 소유한 폴더에 저장되어 있는 상품들 조회
    public Page<Product> getProductsOnFolder(User user, int page, int size, String sortBy, boolean isAsc, Long folderId) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAllByUserIdAndFolderList_Id(user.getId(), folderId, pageable);
    }


}
