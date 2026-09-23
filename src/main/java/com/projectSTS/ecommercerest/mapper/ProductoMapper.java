package com.projectSTS.ecommercerest.mapper;

import org.springframework.stereotype.Component;

import com.projectSTS.ecommercerest.dto.ProductoDTO;
import com.projectSTS.ecommercerest.model.Producto;

@Component
public class ProductoMapper {
	public ProductoDTO toDTO(Producto producto) {
        if (producto == null) {
            return null;
        }
        return new ProductoDTO(producto.getNombre(), producto.getPrecio());
    }
	
	public Producto toEntity(ProductoDTO dto) {
        if (dto == null) {
            return null;
        }
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        return producto;
    }

}
