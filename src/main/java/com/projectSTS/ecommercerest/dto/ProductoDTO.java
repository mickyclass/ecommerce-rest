package com.projectSTS.ecommercerest.dto;

public class ProductoDTO {
	private String nombre;
	private Double precio;
	public ProductoDTO(String nombre, Double precio) {
		super();
		this.nombre = nombre;
		this.precio = precio;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public ProductoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
