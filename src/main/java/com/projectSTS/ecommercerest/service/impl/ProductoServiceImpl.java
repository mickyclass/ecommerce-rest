package com.projectSTS.ecommercerest.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.projectSTS.ecommercerest.dto.ProductoDTO;
import com.projectSTS.ecommercerest.exception.GlobalExceptionHandler.ResourceNotFoundException;
import com.projectSTS.ecommercerest.mapper.ProductoMapper;
import com.projectSTS.ecommercerest.model.Producto;
import com.projectSTS.ecommercerest.pagination.PageInfoDTO;
import com.projectSTS.ecommercerest.repository.ProductoRepository;
import com.projectSTS.ecommercerest.service.ProductoService;

@Service
public class ProductoServiceImpl implements ProductoService{
	private final ProductoRepository productoRepository;
	private final ProductoMapper productoMapper;
	
	@Autowired
	public ProductoServiceImpl(ProductoRepository productoRepository, ProductoMapper productoMapper) {
		this.productoRepository = productoRepository;
		this.productoMapper = productoMapper;
	}
	
	@Override
	public PageInfoDTO<ProductoDTO> obtenerTodosPaginado(int page, int size) {
        Page<Producto> paginaProductos = productoRepository.findAll(PageRequest.of(page, size));
        return PageInfoDTO.parse(paginaProductos, productoMapper::toDTO);
    }
	
	@Override
    public ProductoDTO obtenerPorId(Long id) {
        if (id <= 0) {
            throw new ResourceNotFoundException("El ID " + id + " no es válido. Debe ser mayor a 0.");
        }

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El producto con ID " + id + " no existe."));

        return productoMapper.toDTO(producto);
    }
	
	@Override
    public ProductoDTO guardar(ProductoDTO productoDTO) {
		Producto producto = productoMapper.toEntity(productoDTO);
        Producto guardado = productoRepository.save(producto);
        return productoMapper.toDTO(guardado);
    }
	
	@Override
    public ProductoDTO actualizar(Long id, ProductoDTO productoDTO) {
		if (id <= 0) {
	        throw new ResourceNotFoundException("El ID " + id + " no es válido. Debe ser mayor a 0.");
	    }
		
        Producto productoExistente = productoRepository.findById(id)
        		.orElseThrow(() -> new ResourceNotFoundException("El producto con ID " + id + " no existe."));
            
        	productoExistente.setNombre(productoDTO.getNombre());
            productoExistente.setPrecio(productoDTO.getPrecio());
            
            Producto actualizado = productoRepository.save(productoExistente);
            
            return productoMapper.toDTO(actualizado);
    }
	
	@Override
    public boolean eliminar(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
