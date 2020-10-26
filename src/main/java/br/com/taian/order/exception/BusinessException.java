package br.com.taian.order.exception;

import br.com.taian.order.enumeration.BusinessExceptionCode;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static br.com.taian.order.enumeration.BusinessExceptionCode.UNKNOWN;

@Data
public class BusinessException extends RuntimeException {

    private Map<BusinessExceptionCode, String> errorsMap;

    public BusinessException(String message) {
        super(message);
        if (errorsMap == null)
            errorsMap = new HashMap<>();
        errorsMap.put(UNKNOWN, message);
    }

    public BusinessException(BusinessExceptionCode code, String message) {
        super(message);
        if (errorsMap == null)
            errorsMap = new HashMap<>();
        errorsMap.put(code, message);
    }

    public BusinessException(Map<BusinessExceptionCode, String> errorsMap, String message) {
        super(message);
        this.errorsMap = errorsMap;
    }

    private List<String> errors;

    public BusinessException(List<String> errors, String title) {
        super(title);
        this.errors = errors;
    }

}
