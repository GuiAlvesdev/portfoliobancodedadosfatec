package com.api.painelvendas.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table (name = "registro", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"fk_id_vendedor", "fk_id_produto", "fk_id_cliente"})
})
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Registro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDate diaRegistro;
    @ManyToOne
    @JoinColumn(name = "fk_id_vendedor", nullable = false)
    @JsonBackReference
    private Vendedor vendedor;
    @ManyToOne
    @JoinColumn(name = "fk_id_produto", nullable = false)
    private Produto produto;
    @ManyToOne
    @JoinColumn(name = "fk_id_cliente", nullable = false)
    private Cliente cliente;
    @OneToMany(mappedBy = "registro", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Planejamento> planejamentos;

    @OneToMany(mappedBy = "registro", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Historico> historicos;

    @OneToMany(mappedBy = "registro", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Predicao> predicaos;
}
