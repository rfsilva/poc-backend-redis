package br.com.rodrigo.poc.cache.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DTO para resposta paginada
 * @param <T> Tipo dos itens da página
 */
@Data
@NoArgsConstructor
public class PageResponse<T> {
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
    
    /**
     * Construtor com todos os campos
     * Cria uma cópia defensiva da lista content
     */
    public PageResponse(List<T> content, int pageNumber, int pageSize, long totalElements, int totalPages, boolean last) {
        this.content = content != null ? new ArrayList<>(content) : new ArrayList<>();
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
    }
    
    /**
     * Retorna uma cópia defensiva da lista content
     */
    public List<T> getContent() {
        return content != null ? Collections.unmodifiableList(content) : null;
    }
    
    /**
     * Define a lista content criando uma cópia defensiva
     */
    public void setContent(List<T> content) {
        this.content = content != null ? new ArrayList<>(content) : null;
    }
    
    /**
     * Builder personalizado para PageResponse
     */
    public static class PageResponseBuilder<T> {
        private List<T> content;
        private int pageNumber;
        private int pageSize;
        private long totalElements;
        private int totalPages;
        private boolean last;
        
        public PageResponseBuilder() {
        }
        
        /**
         * Define a lista content criando uma cópia defensiva
         */
        public PageResponseBuilder<T> content(List<T> content) {
            this.content = content != null ? new ArrayList<>(content) : null;
            return this;
        }
        
        public PageResponseBuilder<T> pageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
            return this;
        }
        
        public PageResponseBuilder<T> pageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }
        
        public PageResponseBuilder<T> totalElements(long totalElements) {
            this.totalElements = totalElements;
            return this;
        }
        
        public PageResponseBuilder<T> totalPages(int totalPages) {
            this.totalPages = totalPages;
            return this;
        }
        
        public PageResponseBuilder<T> last(boolean last) {
            this.last = last;
            return this;
        }
        
        /**
         * Constrói o objeto PageResponse
         */
        public PageResponse<T> build() {
            return new PageResponse<>(content, pageNumber, pageSize, totalElements, totalPages, last);
        }
    }
    
    /**
     * Método estático para criar um builder
     */
    public static <T> PageResponseBuilder<T> builder() {
        return new PageResponseBuilder<>();
    }
}