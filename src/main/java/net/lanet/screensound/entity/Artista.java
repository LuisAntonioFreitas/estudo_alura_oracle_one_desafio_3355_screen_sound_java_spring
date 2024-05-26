package net.lanet.screensound.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.lanet.screensound.enums.TipoArtista;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "artistas", indexes = {
        @Index(name = "idx_nome", columnList = "nome"),
        @Index(name = "idx_tipo", columnList = "tipo") })
@Data // @Getter @Setter @EqualsAndHashCode @ToString
@NoArgsConstructor @AllArgsConstructor
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable=false, unique=true)
    private Long id;
    @Column(name="nome", nullable=false, length=100)
    private String nome;
    @Enumerated(EnumType.STRING)
    @Column(name="tipo")
    private TipoArtista tipo;

    @OneToMany(mappedBy = "artista",
            cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Musica> musicas = new ArrayList<>();

    public Artista(String nome, TipoArtista tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    public void addMusica(Musica musica) {
        this.musicas.add(musica);
    }

    public void removeMusica(Musica musica) {
        this.musicas.remove(musica);
    }

    @Override
    public String toString() {
        return "[Artista]: " + this.nome
                + " (" + this.tipo + ")"
                + " # [Músicas]: " + this.musicas;
    }

    public String getNome() {
        return nome;
    }

    public TipoArtista getTipo() {
        return tipo;
    }

    public List<Musica> getMusicas() {
        return musicas;
    }
}
