package com.impl.products.controller.product;

import com.impl.products.controller.security.AbstractController;
import com.impl.products.dto.RequestParams;
import com.impl.products.dto.ResponseMessage;
import com.impl.products.dto.ProductRequestBody;
import com.impl.products.helper.ExcelHelper;
import com.impl.products.model.product.Product;
import com.impl.products.model.user.User;
import com.impl.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
public class ProductController extends AbstractController {
    @Autowired
    ProductService service;

    @GetMapping("/product")
    private List<Product> getAllProducts(Authentication authentication,@RequestParam(name = "limit", required = true) int limit,
                                         @RequestParam(name = "offset", required = true) int offset) {
        RequestParams params = new RequestParams();
        params.setLimit(limit);
        params.setOffset(offset);
        User user = getUserDetails(authentication).getUser();
        return service.getAllProducts(params);
    }

    @PostMapping("/product/upload")
    public ResponseEntity<ResponseMessage> uploadFile(Authentication authentication,@RequestParam("file") MultipartFile file) {
        String message = "";
        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                service.save(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }
        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @PutMapping("/product/{id}")
    private ResponseEntity<ResponseMessage> activateProduct(Authentication authentication, @PathVariable String id,
                                                            @RequestBody ProductRequestBody body) {
        service.activateProduct(Long.parseLong(id), body);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Product Updated successfully!"));

    }
}
