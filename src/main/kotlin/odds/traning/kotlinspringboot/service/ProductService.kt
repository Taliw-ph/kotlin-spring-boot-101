package odds.traning.kotlinspringboot.service

import odds.traning.kotlinspringboot.entity.ProductEntity
import odds.traning.kotlinspringboot.model.ProductRequest
import odds.traning.kotlinspringboot.model.ProductResponse
import odds.traning.kotlinspringboot.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository
) {

    fun addProduct(product: ProductRequest): ProductResponse {
        val productEntity = ProductEntity(
            id = 0,
            name = product.name,
            price = addTaxToPrice(product.price ?: 0.0),
            description = product.description ?: "",
            stock = product.stock,
        )
        return transformToProductResponse(productRepository.save(productEntity))
    }

    fun getProducts(): List<ProductResponse> {
        return productRepository.findAll().map { transformToProductResponse(it) }
    }

    fun updateProductById(productId: Int, request: ProductRequest): ProductResponse {
        val productEntity = ProductEntity(
            id = productId,
            name = request.name,
            price = addTaxToPrice(request.price ?: 0.0),
            description = request.description ?: "",
            stock = request.stock,
        )
        return transformToProductResponse(productRepository.save(productEntity))
    }

    fun deleteProductById(productId: Int): String = productRepository.deleteById(productId).let { "Delete productId: $productId successfully" }

    private fun addTaxToPrice(price: Double) = price * 1.07

    private fun transformToProductResponse(entity: ProductEntity): ProductResponse = ProductResponse(
        id = "${entity.id}",
        name = entity.name,
        price = "${entity.price?.toBigDecimal()}",
        description = "${entity.description}",
        stock = "${entity.stock}",
    )
}