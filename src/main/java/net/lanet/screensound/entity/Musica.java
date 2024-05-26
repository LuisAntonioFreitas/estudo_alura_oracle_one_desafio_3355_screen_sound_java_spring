package net.lanet.screensound.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "musicas", indexes = {
        @Index(name = "idx_titulo", columnList = "titulo"),
        @Index(name = "idx_artista_id", columnList = "artista_id") })
@Data // @Getter @Setter @EqualsAndHashCode @ToString
@NoArgsConstructor @AllArgsConstructor
public class Musica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable=false, unique=true)
    private Long id;
    @Column(name="titulo", nullable=false, length=100)
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "artista_id")
    private Artista artista;

    public Musica(String titulo, Artista artista) {
        this.titulo = titulo;
        this.artista = artista;
    }

    @Override
    public String toString() {
        return "[Música]: " + this.titulo;
    }

    public Artista getArtista() {
        return artista;
    }

    public String getTitulo() {
        return titulo;
    }
}
