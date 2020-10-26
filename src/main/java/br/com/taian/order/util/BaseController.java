package br.com.taian.order.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public abstract class BaseController <K extends CrudService, MODEL, DTO> {

    @Autowired
    protected ModelMapper modelMapper;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected K service;

    @ApiOperation(value = "Get all elements")
    @GetMapping("/v1.0/all")
    @Transactional
    public ResponseEntity getAllElements() {
        List<MODEL> elements = (List<MODEL>) service.findAll();
        if (elements!=null && elements.size()>0){
            return ResponseEntity.ok(convertToListDto(elements));
        }
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Get all elements")
    @GetMapping("/v1.0/all/pagination")
    @Transactional
    public ResponseEntity getAllElements(Pageable pageable) {
        Page<MODEL> pages = (Page<MODEL>) service.findAll(pageable);
        if (pages!=null && pages.getTotalElements()>0){
            return ResponseEntity.ok(new PageImpl(convertToListDto(pages.getContent()), pageable, pages.getTotalElements()));
        }
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Get element by id")
    @GetMapping("/v1.0/{id}")
    @Transactional
    public ResponseEntity getElementById(@PathVariable(value = "id") Long elementId) {
        MODEL element = (MODEL) service.findById(elementId);
        if (element!=null){
            return ResponseEntity.ok(convertToDetailDto(element));
        }
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Create a new element")
    @PostMapping("/v1.0")
    @Transactional
    public ResponseEntity createElement(@RequestBody DTO element) {
        MODEL converted = convertToModel(element);
        MODEL elementCreated = (MODEL) service.createElement((BaseModel) converted);
        if (elementCreated!=null){
            ObjectNode response = objectMapper.createObjectNode();
            response.put("id", ((BaseModel<Long>) (elementCreated)).getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Update a element")
    @PutMapping("/v1.0/{id}")
    @Transactional
    public ResponseEntity updateElement(@PathVariable(value = "id") Long elementId,
                                        @RequestBody DTO element) {
        MODEL converted = convertToModel(element);
        MODEL elementUpdated = (MODEL) service.updateElement(elementId, (BaseModel) converted);
        if (elementUpdated!=null){
            ObjectNode response = objectMapper.createObjectNode();
            response.put("id", ((BaseModel<Long>) (elementUpdated)) .getId());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Delete a element")
    @DeleteMapping("/v1.0/{id}")
    public ResponseEntity deleteElement(@PathVariable(value = "id") Long elementId) {
        boolean success = service.deleteElement(elementId);
        if (success){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.noContent().build();
    }

    protected abstract List<?> convertToListDto(List<MODEL> elements);

    protected abstract Object convertToDetailDto(MODEL element);

    protected abstract MODEL convertToModel(DTO dto);

}
