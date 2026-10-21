package web.pulso.service.troca;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import web.pulso.repository.PlantaoRepository;
import web.pulso.repository.ProfissionalRepository;
import web.pulso.repository.TrocaRepository;
import web.pulso.repository.UsuarioRepository;
import web.pulso.service.TrocaService;
import web.pulso.service.mappers.TrocaMapper;

@ExtendWith(MockitoExtension.class)
public abstract class TrocaServiceBaseTest {

    @InjectMocks
    protected TrocaService trocaService;

    @Mock
    protected TrocaRepository trocaRepository;

    @Mock
    protected TrocaMapper trocaMapper;

    @Mock
    protected ProfissionalRepository profissionalRepository;

    @Mock
    protected PlantaoRepository plantaoRepository;

    @Mock
    protected UsuarioRepository usuarioRepository;
}