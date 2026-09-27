package com.projectSTS.ecommercerest.service;

import java.util.List;
import java.util.Optional;

import com.projectSTS.ecommercerest.model.Producto;
import com.projectSTS.ecommercerest.pagination.PageInfoDTO;
import com.projectSTS.ecommercerest.dto.ProductoDTO;

public interface ProductoService {
	PageInfoDTO<ProductoDTO> obtenerTodosPaginado(int page, int size);
	ProductoDTO obtenerPorId(Long id);
	ProductoDTO guardar(ProductoDTO productoDTO);
	ProductoDTO actualizar(Long id, ProductoDTO productoDTO);
	boolean eliminar(Long id);
	
}
