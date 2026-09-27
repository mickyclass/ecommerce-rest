package com.projectSTS.ecommercerest.pagination;

import java.util.List;

import org.springframework.data.domain.Page;
import java.util.List;
import java.util.function.Function;

public class PageInfoDTO<T> {
	
	private List<T> contenido;
	private int paginaActual;
	private int tamanoPagina;
	private long totalElementos;
	private int totalPaginas;
	private boolean esUltima;
	
	public PageInfoDTO() {
		
	}
	
	public PageInfoDTO(List<T> contenido, int paginaActual, int tamanoPagina, long totalElementos, int totalPaginas, boolean esUltima) {
		this.contenido = contenido;
		this.paginaActual = paginaActual;
		this.tamanoPagina = tamanoPagina;
		this.totalElementos = totalElementos;
		this.totalPaginas = totalPaginas;
		this.esUltima = esUltima;
	}
	
	public static <T, U> PageInfoDTO<T> parse(Page<U> page, Function<U, T> converter) {
        List<T> contenidoDTO = page.getContent().stream().map(converter).toList();
        return new PageInfoDTO<>(
                contenidoDTO,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }
	
	public List<T> getContenido() {
        return contenido;
    }

    public void setContenido(List<T> contenido) {
        this.contenido = contenido;
    }

    public int getPaginaActual() {
        return paginaActual;
    }

    public void setPaginaActual(int paginaActual) {
        this.paginaActual = paginaActual;
    }

    public int getTamanoPagina() {
        return tamanoPagina;
    }

    public void setTamanoPagina(int tamanoPagina) {
        this.tamanoPagina = tamanoPagina;
    }

    public long getTotalElementos() {
        return totalElementos;
    }

    public void setTotalElementos(long totalElementos) {
        this.totalElementos = totalElementos;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public void setTotalPaginas(int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    public boolean isEsUltima() {
        return esUltima;
    }

    public void setEsUltima(boolean esUltima) {
        this.esUltima = esUltima;
    }

}
