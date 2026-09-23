package com.projectSTS.ecommercerest.service;

import java.util.List;
import java.util.Optional;

import com.projectSTS.ecommercerest.model.Producto;
import com.projectSTS.ecommercerest.dto.ProductoDTO;

public interface ProductoService {
	List<Producto> obtenerTodos();
	Optional<Producto> obtenerPorId(Long id);
	Producto guardar(ProductoDTO productoDTO);
	Optional <Producto> actualizar(Long id, ProductoDTO productoDTO);
	boolean eliminar(Long id);

}
