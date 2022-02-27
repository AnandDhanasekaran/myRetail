//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.target.myRetail.controller;

import com.target.myRetail.domain.Price;
import com.target.myRetail.domain.Product;
import com.target.myRetail.domain.Response;
import com.target.myRetail.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(
    value = "MyRetail",
    description = "Save/Retrieve/Update product and price Detail by product id"
)
@RequestMapping({"/products"})
@RestController
public class ProductController {
  @Autowired
  ProductService productService;

  @ApiOperation("Gets the product and price detail by product id")
  @ApiResponses({@ApiResponse(
      code = 200,
      message = "Successful response"
  ), @ApiResponse(
      code = 400,
      message = "Product inventory mismatch"
  ), @ApiResponse(
      code = 404,
      message = "Product not available"
  )})
  @GetMapping(
      value = {"/{id}"},
      produces = {"application/json"}
  )
  public ResponseEntity<Product> getProductDetail(@PathVariable("id") String productId) throws Exception {
    Product product = this.productService.getProduct(productId);
    return new ResponseEntity(product, HttpStatus.OK);
  }

  @ApiOperation("Insert the price detail for the given product id")
  @ApiResponses({@ApiResponse(
      code = 201,
      message = "Successful response"
  ), @ApiResponse(
      code = 404,
      message = "Product not available"
  )})
  @PostMapping(
      value = {"/{id}"},
      produces = {"application/json"}
  )
  public ResponseEntity<Response> savePrice(@Valid @RequestBody Price price, @PathVariable("id") String productId) throws Exception {
    price.setId(Integer.parseInt(productId));
    this.productService.insertPrice(price, productId);
    return new ResponseEntity(new Response(HttpStatus.CREATED.value(), "Pricing details for the given product is inserted."), HttpStatus.CREATED);
  }

  @ApiOperation("Update the price for the given product id")
  @ApiResponses({@ApiResponse(
      code = 200,
      message = "Successful response"
  ), @ApiResponse(
      code = 400,
      message = "Product inventory mismatch"
  ), @ApiResponse(
      code = 404,
      message = "Product not available"
  )})
  @PutMapping(
      value = {"/{id}"},
      produces = {"application/json"}
  )
  public ResponseEntity<Response> updatePrice(@Valid @RequestBody Price price, @PathVariable("id") String productId) throws Exception {
    price.setId(Integer.parseInt(productId));
    this.productService.updatePrice(price, productId);
    return new ResponseEntity(new Response(HttpStatus.OK.value(), "Pricing details for the given product is updated."), HttpStatus.OK);
  }
}
