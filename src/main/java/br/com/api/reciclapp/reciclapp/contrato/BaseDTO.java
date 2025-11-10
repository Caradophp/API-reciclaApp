package br.com.api.reciclapp.reciclapp.contrato;

import java.util.List;

public interface BaseDTO {

    List<?> convertToDTO(List<?> entity);

}
