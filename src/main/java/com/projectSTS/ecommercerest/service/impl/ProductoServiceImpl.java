package com.projectSTS.ecommercerest.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectSTS.ecommercerest.dto.ProductoDTO;
import com.projectSTS.ecommercerest.mapper.ProductoMapper;
import com.projectSTS.ecommercerest.model.Producto;
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
	public List<Producto> obtenerTodos(){
		return productoRepository.findAll();
	}
	
	@Override
	public Optional<Producto> obtenerPorId(Long id){
		return productoRepository.findById(id);
	}
	
	@Override
    public Producto guardar(ProductoDTO productoDTO) {
        Producto producto = productoMapper.toEntity(productoDTO);
        return productoRepository.save(producto);
    }
	
	@Override
    public Optional<Producto> actualizar(Long id, ProductoDTO productoDTO) {
        return productoRepository.findById(id).map(productoExistente -> {
            productoExistente.setNombre(productoDTO.getNombre());
            productoExistente.setPrecio(productoDTO.getPrecio());
            return productoRepository.save(productoExistente);
        });
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
