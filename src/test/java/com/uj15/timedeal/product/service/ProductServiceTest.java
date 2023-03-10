package com.uj15.timedeal.product.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.uj15.timedeal.product.controller.dto.ProductCreateRequest;
import com.uj15.timedeal.product.controller.dto.ProductUpdateRequest;
import com.uj15.timedeal.product.entity.Product;
import com.uj15.timedeal.product.repository.ProductRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    @Nested
    @DisplayName("createProduct 메서드 테스트")
    class DescribeCreateProduct {

        @ParameterizedTest
        @NullSource
        @DisplayName("null을 인자로 받을 경우 IllegalArgumentException을 반환한다.")
        void itThrowIllegalArgumentException(ProductCreateRequest request) {
            //then
            Assertions.assertThatThrownBy(() -> productService.createProduct(request))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("createRequest를 인자로 받을 경우 Repository save메서드를 호출한다.")
        void itCallRepositorySave() {
            //given
            ProductCreateRequest requestDto = new ProductCreateRequest(
                    "product",
                    "description",
                    1000,
                    LocalDateTime.now()
            );

            //when
            productService.createProduct(requestDto);

            //then
            verify(productRepository).save(any());
        }
    }

    @Nested
    @DisplayName("updateProduct 메서드 테스트")
    class DescribeUpdateProduct {

        @ParameterizedTest
        @NullSource
        @DisplayName("null을 인자로 받을 경우 IllegalArgumentExcpetion을 반환한다.")
        void itThrowIllegalArgumentException(ProductUpdateRequest request) {
            //then
            Assertions.assertThatThrownBy(() -> productService.updateProduct(UUID.randomUUID(), request))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("updateRequest를 인자로 받을 경우 Repository findById메서드와 도메인 update메서드를 호출한다.")
        void itCallRepositoryFindAndDomainUpdate() {
            //given
            Product product = Product.builder()
                    .name("prodcut")
                    .description("description")
                    .price(1000)
                    .dealTime(LocalDateTime.now())
                    .build();
            UUID id = product.getId();

            ProductUpdateRequest requestDto = new ProductUpdateRequest(
                    "product",
                    "description",
                    1000,
                    LocalDateTime.now()
            );
            when(productRepository.findById(any())).thenReturn(Optional.of(product));

            //when
            productService.updateProduct(id, requestDto);

            //then
            verify(productRepository).findById(any());
        }
    }
}