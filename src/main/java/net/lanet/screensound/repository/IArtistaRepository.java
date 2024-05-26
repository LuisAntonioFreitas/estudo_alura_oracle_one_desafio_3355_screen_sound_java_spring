package net.lanet.screensound.repository;

import net.lanet.screensound.entity.Artista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IArtistaRepository extends JpaRepository<Artista, Long> {
    Optional<List<Artista>> findByNomeContainingIgnoreCase(String nome);
}
