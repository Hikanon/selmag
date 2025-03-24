package ru.myproject.managerapp.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import ru.myproject.managerapp.client.api.ProductsRestClient;
import ru.myproject.managerapp.dto.NewProductDto;
import ru.myproject.managerapp.dto.UpdateProductDto;
import ru.myproject.managerapp.entity.Product;
import ru.myproject.managerapp.exception.BadRequestException;
import ru.myproject.managerapp.exception.NotFoundException;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class RestClientProductsRestClientImpl implements ProductsRestClient {

    private static final ParameterizedTypeReference<List<Product>> PRODUCTS_TYPE_REFERENCE = new ParameterizedTypeReference<>() {
    };

    private final RestClient restClient;

    @Override
    public List<Product> findAllProducts() {
        return restClient.get()
                .uri("/api/products")
                .retrieve()
                .body(PRODUCTS_TYPE_REFERENCE);
    }

    @Override
    public Product createProduct(NewProductDto newProductDto) throws BadRequestException {
        try {
            return restClient.post()
                    .uri("/api/products")
                    .body(newProductDto)
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(Product.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            throw new BadRequestException(getErrorsListFromException(exception));
        }
    }

    @Override
    public Product findById(long id) {
        return restClient.get()
                .uri("/api/products/{productId}", id)
                .retrieve()
                .body(Product.class);
    }

    @Override
    public void updateProduct(long id, UpdateProductDto updateProductDto) throws NotFoundException {
        try {
            restClient.patch()
                    .uri("/api/products/{productId}", id)
                    .body(updateProductDto)
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            throw new BadRequestException(getErrorsListFromException(exception));
        }
    }

    @Override
    public void delete(long id) {
        restClient.delete()
                .uri("/api/products/%d".formatted(id))
                .retrieve()
                .toBodilessEntity();
    }

    private List<String> getErrorsListFromException(HttpClientErrorException exception) {
        ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
        if (problemDetail == null) {
            throwRestClientException(exception);
        }
        Map<String, Object> properties = problemDetail.getProperties();
        if (properties == null) {
            throwRestClientException(exception);
        }
        if (!properties.containsKey("errors")) {
            throwRestClientException(exception);
        }
        Object errors = properties.get("errors");
        if (!(errors instanceof List)) {
            throwRestClientException(exception);
        }
        return (List<String>) errors;
    }

    private void throwRestClientException(HttpClientErrorException exception) {
        throw new RestClientResponseException("Ошибка при обработке ошибки при вызове /api/products",
                exception.getStatusCode(),
                exception.getStatusText(),
                exception.getResponseHeaders(),
                exception.getResponseBodyAsByteArray(),
                Charset.defaultCharset());
    }
}
